package forex.transaction.controller;

import forex.transaction.dto.SpotTransactionDTO;
import forex.transaction.dto.TransactionsValidationResultDTO;
import forex.transaction.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionValidationControllerSpotIT extends AbstractTransactionValidationControllerIT {
    @Test
    void validateOneValidTransaction() throws Exception {
        TransactionsValidationResultDTO transactionsValidationResultDTO = validateValidTransactions(
                getValidSpotTransactionDTO());

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).isEmpty();
    }

    @Test
    void validateInvalidTransactionNullCustomer() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setCustomer(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringCustomer() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setCustomer("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedCustomer() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setCustomer("yoda1");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2")
        );
    }

    @Test
    void validateInvalidTransactionNullCcyPair() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setCcyPair(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair can not be null")
        );
    }

    @Test
    void validateInvalidTransactionBlankCcyPair() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setCcyPair("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionLessThan6CharactersCcyPair() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setCcyPair("AAAAA");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionMoreThan6CharactersCcyPair() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setCcyPair("AAAAAAA");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionAAAEURCcyPair() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setCcyPair("AAAEUR");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionEURAAACcyPair() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setCcyPair("EURAAA");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }



    @Test
    void validateInvalidTransactionNullDirection() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setDirection(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringDirection() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setDirection("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can be only BUY or SELL")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedDirection() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setDirection("buy");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can be only BUY or SELL")
        );
    }

    @Test
    void validateInvalidTransactionNullTradeDate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setTradeDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date can not be null")
        );
    }

    @Test
    void validateInvalidTransactionBlankTradeDate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setTradeDate("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionInvalidTradeDate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setTradeDate("12-12-2021");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionNullAmount1() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setAmount1(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("amount1"), "Amount1 can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullAmount2() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setAmount2(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("amount2"), "Amount2 can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullRate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setRate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("rate"), "Rate can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullLegalEntity() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setLegalEntity(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringLegalEntity() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setLegalEntity("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedLegalEntity() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setLegalEntity("ubs ag");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG")
        );
    }

    @Test
    void validateInvalidTransactionNullTrader() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setTrader(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("trader"), "Trader can not be blank")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringTrader() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setTrader("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("trader"), "Trader can not be blank")
        );
    }

    @Test
    void validateInvalidTransactionNullValueDate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setValueDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date can not be null")
        );
    }

    @Test
    void validateInvalidTransactionSaturdayValueDate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setValueDate("2020-08-08");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date can not be on Saturday or Sunday")
        );
    }

    @Test
    void validateInvalidTransactionSundayValueDate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setValueDate("2020-08-09");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date can not be on Saturday or Sunday")
        );
    }

    @Test
    void validateInvalidTransactionValueDateEqualsTradeDate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setTradeDate("2021-12-13");
        spotTransactionDTO.setValueDate("2021-12-13");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, getPropertiesSet("valueDate", "tradeDate"), "Value date can not be after or equal trade date")
        );
    }

    @Test
    void validateInvalidTransactionValueDateAfterTradeDate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setTradeDate("2021-12-12");
        spotTransactionDTO.setValueDate("2021-12-13");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, getPropertiesSet("valueDate", "tradeDate"), "Value date can not be after or equal trade date")
        );
    }

    @Test
    void validateInvalidTransactionBlankValueDate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setValueDate("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionInvalidValueDate() throws Exception {
        SpotTransactionDTO spotTransactionDTO = getValidSpotTransactionDTO();
        spotTransactionDTO.setValueDate("12-12-2021");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionAllNullFields() throws Exception {
        SpotTransactionDTO spotTransactionDTO = new SpotTransactionDTO();
        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(spotTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactly(
                new ValidationErrorDTO(1L, Set.of("amount1"), "Amount1 can not be null"),
                new ValidationErrorDTO(1L, Set.of("amount2"), "Amount2 can not be null"),
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair can not be null"),
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can not be null"),
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can not be null"),
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can not be null"),
                new ValidationErrorDTO(1L, Set.of("rate"), "Rate can not be null"),
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date can not be null"),
                new ValidationErrorDTO(1L, Set.of("trader"), "Trader can not be blank"),
                new ValidationErrorDTO(1L, Set.of("valueDate"), "Value date can not be null")
        );
    }
}
