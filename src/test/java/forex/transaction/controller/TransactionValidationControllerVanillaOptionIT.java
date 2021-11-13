package forex.transaction.controller;

import forex.transaction.dto.TransactionsValidationResultDTO;
import forex.transaction.dto.ValidationErrorDTO;
import forex.transaction.dto.VanillaOptionTransactionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionValidationControllerVanillaOptionIT extends AbstractTransactionValidationControllerIT {
    @Test
    void validateOneValidEuropeanTransaction() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();

        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + getJSONString(vanillaOptionTransactionDTO) + "]"))
                .andExpect(status().isOk())
                .andReturn();

        TransactionsValidationResultDTO transactionsValidationResultDTO = getTransactionsValidationResult(result);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).isEmpty();
    }

    @Test
    void validateOneValidAmericanTransaction() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionAmericanTransactionDTO();

        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + getJSONString(vanillaOptionTransactionDTO) + "]"))
                .andExpect(status().isOk())
                .andReturn();

        TransactionsValidationResultDTO transactionsValidationResultDTO = getTransactionsValidationResult(result);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).isEmpty();
    }

    @Test
    void validateInvalidTransactionNullCustomer() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setCustomer(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringCustomer() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setCustomer("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedCustomer() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setCustomer("yoda1");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("customer"), "Customer can be only YODA1 or YODA2")
        );
    }

    @Test
    void validateInvalidTransactionNullCcyPair() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setCcyPair(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("ccyPair"), "CcyPair can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullDirection() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setDirection(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringDirection() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setDirection("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can be only BUY or SELL")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedDirection() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setDirection("buy");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("direction"), "Direction can be only BUY or SELL")
        );
    }

    @Test
    void validateInvalidTransactionNullTradeDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setTradeDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date can not be null")
        );
    }

    @Test
    void validateInvalidTransactionBlankTradeDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setTradeDate("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date format can be only YYYY-mm-dd")
        );
    }

    @Test
    void validateInvalidTransactionInvalidTradeDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setTradeDate("12-12-2021");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("tradeDate"), "Trade date format can be only YYYY-mm-dd")
        );
    }

    @Test
    void validateInvalidTransactionNullAmount1() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setAmount1(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("amount1"), "Amount1 can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullAmount2() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setAmount2(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("amount2"), "Amount2 can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullRate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setRate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("rate"), "Rate can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullLegalEntity() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setLegalEntity(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringLegalEntity() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setLegalEntity("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedLegalEntity() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setLegalEntity("ubs ag");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("legalEntity"), "Legal entity can be only UBS AG")
        );
    }

    @Test
    void validateInvalidTransactionNullTrader() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setTrader(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("trader"), "Trader can not be blank")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringTrader() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setTrader("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("trader"), "Trader can not be blank")
        );
    }

    @Test
    void validateInvalidTransactionNullStyle() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setStyle(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("style"), "Style can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringStyle() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setStyle("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("style"), "Style can be only EUROPEAN or AMERICAN")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedStyle() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setStyle("european");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("style"), "Style can be only EUROPEAN or AMERICAN")
        );
    }

    @Test
    void validateInvalidTransactionNullStrategy() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setStrategy(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("strategy"), "Strategy can not be null")
        );
    }

    @Test
    void validateInvalidTransactionEmptyStringStrategy() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setStrategy("");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("strategy"), "Strategy can be only CALL")
        );
    }

    @Test
    void validateInvalidTransactionNotSupportedStrategy() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setStrategy("call");

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("strategy"), "Strategy can be only CALL")
        );
    }

    @Test
    void validateInvalidTransactionNullDeliveryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setDeliveryDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("deliveryDate"), "Delivery date can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullExpiryDate() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setExpiryDate(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("expiryDate"), "Expiry date can not be null")
        );
    }

    @Test
    void validateInvalidTransactionNullPayCcy() throws Exception {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        vanillaOptionTransactionDTO.setPayCcy(null);

        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransaction(vanillaOptionTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(1);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(1L, Set.of("payCcy"), "PayCcy can not be null")
        );
    }

    private VanillaOptionTransactionDTO getValidVanillaOptionEuropeanTransactionDTO() {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = new VanillaOptionTransactionDTO();
        vanillaOptionTransactionDTO.setCustomer("YODA1");
        vanillaOptionTransactionDTO.setCcyPair("EURUSD");
        vanillaOptionTransactionDTO.setDirection("BUY");
        vanillaOptionTransactionDTO.setTradeDate("2020-08-11");
        vanillaOptionTransactionDTO.setAmount1(BigDecimal.valueOf(100000000, 2));
        vanillaOptionTransactionDTO.setAmount2(BigDecimal.valueOf(112000000, 2));
        vanillaOptionTransactionDTO.setRate(BigDecimal.valueOf(112, 2));
        vanillaOptionTransactionDTO.setLegalEntity("UBS AG");
        vanillaOptionTransactionDTO.setTrader("Josef Schoenberger");

        vanillaOptionTransactionDTO.setStyle("EUROPEAN");
        vanillaOptionTransactionDTO.setStrategy("CALL");

        vanillaOptionTransactionDTO.setDeliveryDate("2020-08-22");
        vanillaOptionTransactionDTO.setExpiryDate("2020-08-19");
        vanillaOptionTransactionDTO.setPayCcy("USD");

        vanillaOptionTransactionDTO.setPremium(BigDecimal.valueOf(20,2));
        vanillaOptionTransactionDTO.setPremiumCcy("USD");
        vanillaOptionTransactionDTO.setPremiumType("%USD");
        vanillaOptionTransactionDTO.setPremiumDate("2020-08-12");

        return vanillaOptionTransactionDTO;
    }

    private VanillaOptionTransactionDTO getValidVanillaOptionAmericanTransactionDTO() {
        VanillaOptionTransactionDTO vanillaOptionTransactionDTO = new VanillaOptionTransactionDTO();
        vanillaOptionTransactionDTO.setCustomer("YODA1");
        vanillaOptionTransactionDTO.setCcyPair("EURUSD");
        vanillaOptionTransactionDTO.setDirection("BUY");
        vanillaOptionTransactionDTO.setTradeDate("2020-08-11");
        vanillaOptionTransactionDTO.setAmount1(BigDecimal.valueOf(100000000, 2));
        vanillaOptionTransactionDTO.setAmount2(BigDecimal.valueOf(112000000, 2));
        vanillaOptionTransactionDTO.setRate(BigDecimal.valueOf(112, 2));
        vanillaOptionTransactionDTO.setLegalEntity("UBS AG");
        vanillaOptionTransactionDTO.setTrader("Josef Schoenberger");

        vanillaOptionTransactionDTO.setStyle("AMERICAN");
        vanillaOptionTransactionDTO.setStrategy("CALL");

        vanillaOptionTransactionDTO.setDeliveryDate("2020-08-22");
        vanillaOptionTransactionDTO.setExpiryDate("2020-08-19");
        vanillaOptionTransactionDTO.setExcerciseStartDate("2020-08-12");
        vanillaOptionTransactionDTO.setPayCcy("USD");

        vanillaOptionTransactionDTO.setPremium(BigDecimal.valueOf(20,2));
        vanillaOptionTransactionDTO.setPremiumCcy("USD");
        vanillaOptionTransactionDTO.setPremiumType("%USD");
        vanillaOptionTransactionDTO.setPremiumDate("2020-08-12");

        return vanillaOptionTransactionDTO;
    }
}
