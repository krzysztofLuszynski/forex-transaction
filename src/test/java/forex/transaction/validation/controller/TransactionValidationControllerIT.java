package forex.transaction.validation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionValidationControllerIT {
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

        String errorMessage = result.getResponse().getContentAsString();
        assertThat(errorMessage).isEqualTo("OK");
    }
}
