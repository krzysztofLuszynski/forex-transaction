package forex.transaction.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import forex.transaction.dto.TransactionDTO;
import forex.transaction.dto.TransactionsValidationResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
    private final ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

    TransactionsValidationResultDTO validateInvalidTransaction(TransactionDTO transactionDTO) throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + getJSONString(transactionDTO) + "]"))
                .andExpect(status().isBadRequest())
                .andReturn();

        return getTransactionsValidationResult(result);
    }

    String getJSONString(TransactionDTO transactionDTO) throws JsonProcessingException {
        return objectWriter.writeValueAsString(transactionDTO);
    }

    TransactionsValidationResultDTO getTransactionsValidationResult(MvcResult result) throws Exception {
        String errorMessage = result.getResponse().getContentAsString();

        return objectMapper.readValue(errorMessage, TransactionsValidationResultDTO.class);
    }

    Set<String> getPropertiesSet(String... properties) {
        return new LinkedHashSet<>(List.of(properties));
    }
}
