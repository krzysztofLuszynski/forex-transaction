package forex.transaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import forex.transaction.domain.TransactionsValidationResult;
import forex.transaction.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionValidationControllerIT {
    private static final String VALID_SPOT_TRANSACTION =
            "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\"," +
                    "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"valueDate\":\"2020-08-15\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

    private static final String INVALID_SPOT_TRANSACTION =
            "{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\"," +
                    "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"legalEntity\":\"UBS AG1\",\"trader\":\"Josef Schoenberger\"}";

    private static final String VALID_FORWARD_TRANSACTION =
            "{\"customer\":\"YODA2\",\"ccyPair\":\"EURUSD\",\"type\":\"Forward\",\"direction\":\"SELL\"," +
                    "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"valueDate\":\"2020-08-22\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

    private static final String INVALID_FORWARD_TRANSACTION =
            "{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"Forward\",\"direction\":\"SELL\"," +
                    "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"legalEntity\":\"UBS AG1\",\"trader\":\"Josef Schoenberger\"}";

    private static final String VALID_VANILLA_OPTION_TRANSACTION_1 =
            "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN\"," +
                    "\"direction\":\"BUY\",\"strategy\":\"CALL\",\"tradeDate\":\"2020-08-11\"," +
                    "\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-19\",\"payCcy\":\"USD\"," +
                    "\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                    "\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

    private static final String VALID_VANILLA_OPTION_TRANSACTION_2 =
            "{\"customer\":\"YODA2\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"AMERICAN\"," +
                    "\"direction\":\"SELL\",\"strategy\":\"CALL\",\"tradeDate\":\"2020-08-11\"," +
                    "\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-21\"," +
                    "\"excerciseStartDate\":\"2020-08-12\",\"payCcy\":\"USD\"," +
                    "\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                    "\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

    private static final String INVALID_VANILLA_OPTION_TRANSACTION =
            "{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN1\"," +
                    "\"direction\":\"BUY\",\"strategy\":\"CALL\",\"tradeDate\":\"2020-08-11\"," +
                    "\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-23\",\"payCcy\":\"USD\"," +
                    "\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                    "\"premiumDate\":\"2020-08-24\",\"legalEntity\":\"UBS AG1\",\"trader\":\"Josef Schoenberger\"}";

    @Autowired
    private MockMvc mvc;

    @Test
    void validateEmptyTransactionList() throws Exception {
        MvcResult result = mvc.perform(
                post("/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                    .andExpect(status().isOk())
                    .andReturn();

        TransactionsValidationResult transactionsValidationResult = getTransactionsValidationResult(result);
        assertThat(transactionsValidationResult.getTransactionsNumber()).isEqualTo(0);
        assertThat(transactionsValidationResult.getValidationErrors()).isEmpty();
    }

    @Test
    void validateOneSpotValidTransaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + VALID_SPOT_TRANSACTION + "]"))
                .andExpect(status().isOk())
                .andReturn();

        TransactionsValidationResult transactionsValidationResult = getTransactionsValidationResult(result);
        assertThat(transactionsValidationResult.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResult.getValidationErrors()).isEmpty();
    }

    @Test
    void validateOneSpotInvalidTransaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + INVALID_SPOT_TRANSACTION + "]"))
                .andExpect(status().isOk())
                .andReturn();

        TransactionsValidationResult transactionsValidationResult = getTransactionsValidationResult(result);
        assertThat(transactionsValidationResult.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResult.getValidationErrors()).containsExactlyInAnyOrder(
                new ValidationError(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2"),
                new ValidationError(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG"),
                new ValidationError(1L, Set.of("valueDate"), "Value date is mandatory")
                );
    }

    @Test
    void validateOneForwardValidTransaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + VALID_FORWARD_TRANSACTION + "]"))
                .andExpect(status().isOk())
                .andReturn();

        TransactionsValidationResult transactionsValidationResult = getTransactionsValidationResult(result);
        assertThat(transactionsValidationResult.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResult.getValidationErrors()).isEmpty();
    }

    @Test
    void validateOneForwardInvalidTransaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + INVALID_FORWARD_TRANSACTION + "]"))
                .andExpect(status().isOk())
                .andReturn();

        TransactionsValidationResult transactionsValidationResult = getTransactionsValidationResult(result);
        assertThat(transactionsValidationResult.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResult.getValidationErrors()).containsExactlyInAnyOrder(
                new ValidationError(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2"),
                new ValidationError(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG"),
                new ValidationError(1L, Set.of("valueDate"), "Value date is mandatory")
        );
    }

    @Test
    void validateOneVanillaOptionValid1Transaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + VALID_VANILLA_OPTION_TRANSACTION_1 + "]"))
                .andExpect(status().isOk())
                .andReturn();

        TransactionsValidationResult transactionsValidationResult = getTransactionsValidationResult(result);
        assertThat(transactionsValidationResult.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResult.getValidationErrors()).isEmpty();
    }

    @Test
    void validateOneVanillaOptionValid2Transaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + VALID_VANILLA_OPTION_TRANSACTION_2 + "]"))
                .andExpect(status().isOk())
                .andReturn();

        TransactionsValidationResult transactionsValidationResult = getTransactionsValidationResult(result);
        assertThat(transactionsValidationResult.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResult.getValidationErrors()).isEmpty();
    }

    @Test
    void validateOneVanillaOptionInvalidTransaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + INVALID_VANILLA_OPTION_TRANSACTION + "]"))
                .andExpect(status().isOk())
                .andReturn();

        TransactionsValidationResult transactionsValidationResult = getTransactionsValidationResult(result);
        assertThat(transactionsValidationResult.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResult.getValidationErrors()).containsExactlyInAnyOrder(
                new ValidationError(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2"),
                new ValidationError(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG"),
                new ValidationError(1L, new LinkedHashSet<>(List.of("expiryDate", "deliveryDate")),
                        "Expiry date: 2020-08-23, shall be before delivery date: 2020-08-22"),
                new ValidationError(1L, new LinkedHashSet<>(List.of("premiumDate", "deliveryDate")),
                        "Premium date: 2020-08-24, shall be before delivery date: 2020-08-22"),
                new ValidationError(1L, Set.of("style"),
                        "Unsupported style: EUROPEAN1, supported values are: [EUROPEAN, AMERICAN]")
        );
    }

    private TransactionsValidationResult getTransactionsValidationResult(MvcResult result) throws Exception {
        String errorMessage = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(errorMessage, TransactionsValidationResult.class);
    }
}
