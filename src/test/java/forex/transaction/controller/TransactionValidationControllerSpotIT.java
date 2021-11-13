package forex.transaction.controller;

import forex.transaction.dto.SpotTransactionDTO;
import forex.transaction.dto.TransactionsValidationResultDTO;
import forex.transaction.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionValidationControllerSpotIT extends AbstractTransactionValidationControllerIT {
    @Test
    void validateOneValidTransaction() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();

        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + getJSONString(spotTransactionDTO) + "]"))
                .andExpect(status().isOk())
                .andReturn();

        TransactionsValidationResultDTO transactionsValidationResultDTO = getTransactionsValidationResult(result);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).isEmpty();
    }

    @Test
    void validateInvalidTransactionNullCustomer() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setCustomer(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringCustomer() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setCustomer("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedCustomer() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setCustomer("yoda1");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2")
        );
    }

    @Test
    void validateInvalidTransactionNullCcyPair() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setCcyPair(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullDirection() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setDirection(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringDirection() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setDirection("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can be only BUY or SELL")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedDirection() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setDirection("buy");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can be only BUY or SELL")
        );
    }

    @Test
    void validateInvalidTransactionNullTradeDate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setTradeDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullAmount1() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setAmount1(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("amount1"), "Amount1 can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullAmount2() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setAmount2(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("amount2"), "Amount2 can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullRate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setRate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("rate"), "Rate can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullLegalEntity() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setLegalEntity(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringLegalEntity() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setLegalEntity("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedLegalEntity() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setLegalEntity("ubs ag");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG")
        );
    }

    @Test
    void validateInvalidTransactionNullTrader() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setTrader(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("trader"), "Trader can not be blank")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringTrader() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setTrader("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("trader"), "Trader can not be blank")
        );
    }

    @Test
    void validateInvalidTransactionNullValueDate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setValueDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date can not be null")
        );
    }

    private SpotTransactionDTO getValidSpotTransactionDTO() {
        SpotTransactionDTO spotTransactionDTO = new SpotTransactionDTO();
        spotTransactionDTO.setCustomer("YODA1");
        spotTransactionDTO.setCcyPair("EURUSD");
        spotTransactionDTO.setDirection("BUY");
        spotTransactionDTO.setTradeDate("2020-08-11");
        spotTransactionDTO.setAmount1(BigDecimal.valueOf(100000000, 2));
        spotTransactionDTO.setAmount2(BigDecimal.valueOf(112000000, 2));
        spotTransactionDTO.setRate(BigDecimal.valueOf(112, 2));
        spotTransactionDTO.setLegalEntity("UBS AG");
        spotTransactionDTO.setTrader("Josef Schoenberger");

        spotTransactionDTO.setValueDate("2020-08-15");

        return spotTransactionDTO;
    }
}
