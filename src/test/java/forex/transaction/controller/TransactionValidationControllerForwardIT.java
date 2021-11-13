package forex.transaction.controller;

import forex.transaction.dto.ForwardTransactionDTO;
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

class TransactionValidationControllerForwardIT extends AbstractTransactionValidationControllerIT {
    @Test
    void validateOneValidTransaction() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();

        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + getJSONString(forwardTransactionDTO) + "]"))
                .andExpect(status().isOk())
                .andReturn();

        TransactionsValidationResultDTO transactionsValidationResultDTO = getTransactionsValidationResult(result);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).isEmpty();
    }

    @Test
    void validateInvalidTransactionNullCustomer() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setCustomer(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringCustomer() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setCustomer("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedCustomer() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setCustomer("yoda1");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2")
        );
    }

    @Test
    void validateInvalidTransactionNullCcyPair() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setCcyPair(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullDirection() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setDirection(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringDirection() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setDirection("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can be only BUY or SELL")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedDirection() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setDirection("buy");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can be only BUY or SELL")
        );
    }

    @Test
    void validateInvalidTransactionNullTradeDate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setTradeDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullAmount1() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setAmount1(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("amount1"), "Amount1 can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullAmount2() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setAmount2(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("amount2"), "Amount2 can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullRate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setRate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("rate"), "Rate can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullLegalEntity() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setLegalEntity(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringLegalEntity() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setLegalEntity("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedLegalEntity() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setLegalEntity("ubs ag");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG")
        );
    }

    @Test
    void validateInvalidTransactionNullTrader() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setTrader(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("trader"), "Trader can not be blank")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringTrader() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setTrader("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("trader"), "Trader can not be blank")
        );
    }

    @Test
    void validateInvalidTransactionNullValueDate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setValueDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date can not be null")
        );
    }

    private ForwardTransactionDTO getValidForwardTransactionDTO() {
        ForwardTransactionDTO forwardTransactionDTO = new ForwardTransactionDTO();
        forwardTransactionDTO.setCustomer("YODA1");
        forwardTransactionDTO.setCcyPair("EURUSD");
        forwardTransactionDTO.setDirection("BUY");
        forwardTransactionDTO.setTradeDate("2020-08-11");
        forwardTransactionDTO.setAmount1(BigDecimal.valueOf(100000000, 2));
        forwardTransactionDTO.setAmount2(BigDecimal.valueOf(112000000, 2));
        forwardTransactionDTO.setRate(BigDecimal.valueOf(112, 2));
        forwardTransactionDTO.setValueDate("2020-08-15");
        forwardTransactionDTO.setLegalEntity("UBS AG");
        forwardTransactionDTO.setTrader("Josef Schoenberger");

        return forwardTransactionDTO;
    }
}
