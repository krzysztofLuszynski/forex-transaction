package forex.transaction.controller;

import forex.transaction.dto.TransactionsValidationResultDTO;
import forex.transaction.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionValidationControllerIT extends AbstractTransactionValidationControllerIT {
    private static final String INVALID_FORWARD_TRANSACTION =
            "{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"Forward\",\"direction\":\"SELL\"," +
                    "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"legalEntity\":\"UBS AG1\",\"trader\":\"Josef Schoenberger\"}";

    private static final String INVALID_VANILLA_OPTION_TRANSACTION =
            "{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN1\"," +
                    "\"direction\":\"BUY\",\"strategy\":\"CALL\",\"tradeDate\":\"2020-08-11\"," +
                    "\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-23\",\"payCcy\":\"USD\"," +
                    "\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                    "\"premiumDate\":\"2020-08-24\",\"legalEntity\":\"UBS AG1\",\"trader\":\"Josef Schoenberger\"}";

    @Test
    void validateEmptyTransactionList() throws Exception {
        MvcResult result = mvc.perform(
                post("/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                    .andExpect(status().isOk())
                    .andReturn();

        TransactionsValidationResultDTO transactionsValidationResultDTO = getTransactionsValidationResult(result);
        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(0);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).isEmpty();
    }

    @Test
    void validateOneForwardInvalidTransaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + INVALID_FORWARD_TRANSACTION + "]"))
                .andExpect(status().isBadRequest())
                .andReturn();

        TransactionsValidationResultDTO transactionsValidationResultDTO = getTransactionsValidationResult(result);
        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2"),
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG"),
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date can not be null")
        );
    }

    @Test
    void validateOneVanillaOptionInvalidTransaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + INVALID_VANILLA_OPTION_TRANSACTION + "]"))
                .andExpect(status().isBadRequest())
                .andReturn();

        TransactionsValidationResultDTO transactionsValidationResultDTO = getTransactionsValidationResult(result);
        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2"),
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG"),
                /*new ValidationErrorDTO(1L, new LinkedHashSet<>(List.of("expiryDate", "deliveryDate")),
                        "Expiry date: 2020-08-23, shall be before delivery date: 2020-08-22"),
                new ValidationErrorDTO(1L, new LinkedHashSet<>(List.of("premiumDate", "deliveryDate")),
                        "Premium date: 2020-08-24, shall be before delivery date: 2020-08-22"),*/
                new ValidationErrorDTO(1L, Set.of("style"),
                        "Style can be only EUROPEAN or AMERICAN")
        );
    }
}
