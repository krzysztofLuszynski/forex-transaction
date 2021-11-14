package forex.transaction.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import forex.transaction.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AbstractTransactionValidationControllerIT {
    @Autowired
    MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    TransactionsValidationResultDTO validateValidTransactions(TransactionDTO... transactionDTOs) throws Exception {
        String JSONTransactionsString = getJSONTransactionsString(transactionDTOs);

        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JSONTransactionsString))
                .andExpect(status().isOk())
                .andReturn();

        return getTransactionsValidationResult(result);
    }

    TransactionsValidationResultDTO validateInvalidTransactions(TransactionDTO... transactionDTOs) throws Exception {
        String JSONTransactionsString = getJSONTransactionsString(transactionDTOs);

        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JSONTransactionsString))
                .andExpect(status().isBadRequest())
                .andReturn();

        return getTransactionsValidationResult(result);
    }

    TransactionsValidationResultDTO getTransactionsValidationResult(MvcResult result) throws Exception {
        String errorMessage = result.getResponse().getContentAsString();

        return objectMapper.readValue(errorMessage, TransactionsValidationResultDTO.class);
    }

    Set<String> getPropertiesSet(String... properties) {
        return new LinkedHashSet<>(List.of(properties));
    }

    SpotTransactionDTO getValidSpotTransactionDTO() {
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

        spotTransactionDTO.setValueDate("2020-08-10");

        return spotTransactionDTO;
    }

    ForwardTransactionDTO getValidForwardTransactionDTO() {
        ForwardTransactionDTO forwardTransactionDTO = new ForwardTransactionDTO();
        forwardTransactionDTO.setCustomer("YODA1");
        forwardTransactionDTO.setCcyPair("EURUSD");
        forwardTransactionDTO.setDirection("BUY");
        forwardTransactionDTO.setTradeDate("2020-08-11");
        forwardTransactionDTO.setAmount1(BigDecimal.valueOf(100000000, 2));
        forwardTransactionDTO.setAmount2(BigDecimal.valueOf(112000000, 2));
        forwardTransactionDTO.setRate(BigDecimal.valueOf(112, 2));
        forwardTransactionDTO.setLegalEntity("UBS AG");
        forwardTransactionDTO.setTrader("Josef Schoenberger");

        forwardTransactionDTO.setValueDate("2020-08-10");

        return forwardTransactionDTO;
    }

    VanillaOptionTransactionDTO getValidVanillaOptionEuropeanTransactionDTO() {
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

    VanillaOptionTransactionDTO getValidVanillaOptionAmericanTransactionDTO() {
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
        vanillaOptionTransactionDTO.setExerciseStartDate("2020-08-12");
        vanillaOptionTransactionDTO.setPayCcy("USD");

        vanillaOptionTransactionDTO.setPremium(BigDecimal.valueOf(20,2));
        vanillaOptionTransactionDTO.setPremiumCcy("USD");
        vanillaOptionTransactionDTO.setPremiumType("%USD");
        vanillaOptionTransactionDTO.setPremiumDate("2020-08-12");

        return vanillaOptionTransactionDTO;
    }

    private String getJSONTransactionsString(TransactionDTO... transactionDTOs) throws JsonProcessingException {
        return objectMapper.writeValueAsString(transactionDTOs);
    }
}
