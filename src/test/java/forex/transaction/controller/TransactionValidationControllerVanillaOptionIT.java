package forex.transaction.controller;

import forex.transaction.dto.TransactionsValidationResultDTO;
import forex.transaction.dto.ValidationErrorDTO;
import forex.transaction.dto.VanillaOptionTransactionDTO;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionValidationControllerVanillaOptionIT extends AbstractTransactionValidationControllerIT {
    @Test
    void validateOneValidEuropeanTransaction() throws Exception {
        TransactionsValidationResultDTO transactionsValidationResultDTO = validateValidTransactions(
                getValidVanillaOptionEuropeanTransactionDTO());

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).isEmpty();
    }

    @Test
    void validateOneValidAmericanTransaction() throws Exception {
        TransactionsValidationResultDTO transactionsValidationResultDTO = validateValidTransactions(
                getValidVanillaOptionAmericanTransactionDTO());

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).isEmpty();
    }

    @Test
    void validateInvalidTransactionNullCustomer() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setCustomer(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringCustomer() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setCustomer("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedCustomer() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setCustomer("yoda1");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2")
        );
    }

    @Test
    void validateInvalidTransactionNullCcyPair() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setCcyPair(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair can not be null")
        );
    }

    @Test
    void validateInvalidTransactionBlankCcyPair() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setCcyPair("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionLessThan6CharactersCcyPair() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setCcyPair("AAAAA");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionMoreThan6CharactersCcyPair() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setCcyPair("AAAAAAA");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionAAAEURCcyPair() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setCcyPair("AAAEUR");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionEURAAACcyPair() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setCcyPair("AAAEUR");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair must contain two ISO-4217 currencies")
        );
    }

    @Test
    void validateInvalidTransactionNullDirection() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setDirection(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringDirection() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setDirection("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can be only BUY or SELL")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedDirection() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setDirection("buy");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can be only BUY or SELL")
        );
    }

    @Test
    void validateInvalidTransactionNullTradeDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setTradeDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date can not be null")
        );
    }

    @Test
    void validateInvalidTransactionBlankTradeDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setTradeDate("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionInvalidTradeDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setTradeDate("12-12-2021");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionInvalidTradeDateEqualsExerciseStartDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionAmericanTransactionDTO();
        vanillaOptionTransactionDTO.setTradeDate("2020-08-12");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, getPropertiesSet("tradeDate", "exerciseStartDate"),
                        "Trade date can not be after or equal exercise start date")
        );
    }

    @Test
    void validateInvalidTransactionInvalidTradeDateAfterExerciseStartDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionAmericanTransactionDTO();
        vanillaOptionTransactionDTO.setTradeDate("2020-08-13");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, getPropertiesSet("tradeDate", "exerciseStartDate"),
                        "Trade date can not be after or equal exercise start date")
        );
    }

    @Test
    void validateInvalidTransactionNullAmount1() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setAmount1(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("amount1"), "Amount1 can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullAmount2() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setAmount2(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("amount2"), "Amount2 can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullRate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setRate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("rate"), "Rate can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullLegalEntity() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setLegalEntity(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringLegalEntity() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setLegalEntity("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedLegalEntity() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setLegalEntity("ubs ag");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG")
        );
    }

    @Test
    void validateInvalidTransactionNullTrader() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setTrader(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("trader"), "Trader can not be blank")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringTrader() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setTrader("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("trader"), "Trader can not be blank")
        );
    }

    @Test
    void validateInvalidTransactionNullStyle() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setStyle(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("style"), "Style can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringStyle() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setStyle("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("style"), "Style can be only EUROPEAN or AMERICAN")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedStyle() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setStyle("european");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("style"), "Style can be only EUROPEAN or AMERICAN")
        );
    }

    @Test
    void validateInvalidTransactionNullStrategy() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setStrategy(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("strategy"), "Strategy can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringStrategy() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setStrategy("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("strategy"), "Strategy can be only CALL")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedStrategy() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setStrategy("call");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("strategy"), "Strategy can be only CALL")
        );
    }

    @Test
    void validateInvalidTransactionNullDeliveryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setDeliveryDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("deliveryDate"), "Delivery date can not be null")
        );
    }

    @Test
    void validateInvalidTransactionBlankDeliveryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setDeliveryDate("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("deliveryDate"), "Delivery date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionInvalidDeliveryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setDeliveryDate("12-12-2021");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("deliveryDate"), "Delivery date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionNullExpiryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setExpiryDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("expiryDate"), "Expiry date can not be null")
        );
    }

    @Test
    void validateInvalidTransactionBlankExpiryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setExpiryDate("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("expiryDate"), "Expiry date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionInvalidExpiryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setExpiryDate("12-12-2021");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("expiryDate"), "Expiry date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionInvalidExpiryDateEqualsDeliveryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setDeliveryDate("2021-12-12");
        vanillaOptionTransactionDTO.setExpiryDate("2021-12-12");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, getPropertiesSet("expiryDate", "deliveryDate"), "Expiry date can not be after or equal delivery date")
        );
    }

    @Test
    void validateInvalidTransactionInvalidExpiryDateAfterDeliveryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setDeliveryDate("2021-12-12");
        vanillaOptionTransactionDTO.setExpiryDate("2021-12-13");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, getPropertiesSet("expiryDate", "deliveryDate"),
                        "Expiry date can not be after or equal delivery date")
        );
    }

    @Test
    void validateInvalidTransactionBlankExerciseStartDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionAmericanTransactionDTO();
        vanillaOptionTransactionDTO.setExerciseStartDate("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, getPropertiesSet("exerciseStartDate"),
                        "Exercise start date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionInvalidExerciseStartDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionAmericanTransactionDTO();
        vanillaOptionTransactionDTO.setExerciseStartDate("12-12-2021");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("exerciseStartDate"), "Exercise start date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionExerciseStartDateEqualsExpiryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionAmericanTransactionDTO();
        vanillaOptionTransactionDTO.setExerciseStartDate("2020-08-19");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, getPropertiesSet("exerciseStartDate", "expiryDate"),
                        "Exercise start date can not be after or equal expiry date")
        );
    }

    @Test
    void validateInvalidTransactionExerciseStartDateAfterExpiryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionAmericanTransactionDTO();
        vanillaOptionTransactionDTO.setExerciseStartDate("2020-08-20");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, getPropertiesSet("exerciseStartDate", "expiryDate"),
                        "Exercise start date can not be after or equal expiry date")
        );
    }

    @Test
    void validateInvalidTransactionNullPayCcy() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setPayCcy(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("payCcy"), "PayCcy can not be null")
        );
    }

    @Test
    void validateInvalidTransactionInvalidPayCcy() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setPayCcy("AAA");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("payCcy"), "PayCcy must be ISO-4217 currency")
        );
    }

    @Test
    void validateInvalidTransactionInvalidPremiumCcy() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setPremiumCcy("AAA");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("premiumCcy"), "PremiumCcy must be ISO-4217 currency")
        );
    }

    @Test
    void validateInvalidTransactionBlankPremiumDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionAmericanTransactionDTO();
        vanillaOptionTransactionDTO.setPremiumDate("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("premiumDate"), "Premium date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionInvalidPremiumDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionAmericanTransactionDTO();
        vanillaOptionTransactionDTO.setPremiumDate("12-12-2021");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("premiumDate"), "Premium date format can be only yyyy-MM-dd")
        );
    }

    @Test
    void validateInvalidTransactionPremiumDateEqualsDeliveryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionAmericanTransactionDTO();
        vanillaOptionTransactionDTO.setDeliveryDate("2021-12-12");
        vanillaOptionTransactionDTO.setPremiumDate("2021-12-12");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, getPropertiesSet("premiumDate", "deliveryDate"),
                        "Premium date can not be after or equal delivery date")
        );
    }

    @Test
    void validateInvalidTransactionPremiumDateAfterDeliveryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionAmericanTransactionDTO();
        vanillaOptionTransactionDTO.setDeliveryDate("2021-12-12");
        vanillaOptionTransactionDTO.setPremiumDate("2021-12-13");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, getPropertiesSet("premiumDate", "deliveryDate"),
                        "Premium date can not be after or equal delivery date")
        );
    }
}
