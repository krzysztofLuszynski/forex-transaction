package forex.transaction.controller;

import forex.transaction.dto.ForwardTransactionDTO;
import forex.transaction.dto.TransactionsValidationResultDTO;
import forex.transaction.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionValidationControllerForwardIT extends AbstractTransactionValidationControllerIT {
    @Test
    void validateOneValidTransaction() throws Exception {
        TransactionsValidationResultDTO transactionsValidationResultDTO = validateValidTransactions(
                getValidForwardTransactionDTO());

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).isEmpty();
    }

    @Test
    void validateInvalidTransactionNullCustomer() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setCustomer(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringCustomer() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setCustomer("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedCustomer() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setCustomer("yoda1");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2")
        );
    }

    @Test
    void validateInvalidTransactionNullCcyPair() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setCcyPair(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair can not be null")
        );
    }

    @Test
    void validateInvalidTransactionBlankCcyPair() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setCcyPair("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionLessThan6CharactersCcyPair() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setCcyPair("AAAAA");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionMoreThan6CharactersCcyPair() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setCcyPair("AAAAAAA");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionAAAEURCcyPair() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setCcyPair("AAAEUR");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionEURAAACcyPair() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setCcyPair("EURAAA");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionNullDirection() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setDirection(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringDirection() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setDirection("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can be only BUY or SELL")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedDirection() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setDirection("buy");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can be only BUY or SELL")
        );
    }

    @Test
    void validateInvalidTransactionNullTradeDate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setTradeDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date can not be null")
        );
    }

    @Test
    void validateInvalidTransactionBlankTradeDate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setTradeDate("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionInvalidTradeDate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setTradeDate("12-12-2021");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date format can be only yyyy-MM-dd")
        );
    }



    @Test
    void validateInvalidTransactionNullAmount1() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setAmount1(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("amount1"), "Amount1 can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullAmount2() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setAmount2(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("amount2"), "Amount2 can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullRate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setRate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("rate"), "Rate can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullLegalEntity() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setLegalEntity(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringLegalEntity() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setLegalEntity("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedLegalEntity() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setLegalEntity("ubs ag");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG")
        );
    }

    @Test
    void validateInvalidTransactionNullTrader() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setTrader(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("trader"), "Trader can not be blank")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringTrader() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setTrader("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("trader"), "Trader can not be blank")
        );
    }

    @Test
    void validateInvalidTransactionNullValueDate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setValueDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date can not be null")
        );
    }

    @Test
    void validateInvalidTransactionSaturdayValueDate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setValueDate("2020-08-08");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date can not be on Saturday or Sunday")
        );
    }

    @Test
    void validateInvalidTransactionSundayValueDate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setValueDate("2020-08-09");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date can not be on Saturday or Sunday")
        );
    }

    @Test
    void validateInvalidTransactionValueDateEqualsTradeDate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setTradeDate("2021-12-13");
        forwardTransactionDTO.setValueDate("2021-12-13");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, getPropertiesSet("valueDate", "tradeDate"), "Value date can not be after or equal trade date")
        );
    }

    @Test
    void validateInvalidTransactionValueDateAfterTradeDate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setTradeDate("2021-12-12");
        forwardTransactionDTO.setValueDate("2021-12-13");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, getPropertiesSet("valueDate", "tradeDate"), "Value date can not be after or equal trade date")
        );
    }

    @Test
    void validateInvalidTransactionBlankValueDate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setValueDate("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionInvalidValueDate() throws Exception {
        ForwardTransactionDTO forwardTransactionDTO = getValidForwardTransactionDTO();
        forwardTransactionDTO.setValueDate("12-12-2021");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(forwardTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date format can be only yyyy-MM-dd")
        );
    }
}
