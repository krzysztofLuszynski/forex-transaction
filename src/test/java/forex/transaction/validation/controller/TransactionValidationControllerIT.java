package forex.transaction.validation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionValidationControllerIT {
    private static final String VALID_SPOT_TRANSACTION = "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2020-08-11\"," +
            "\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2020-08-15\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

    private static final String VALID_FORWARD_TRANSACTION = "{\"customer\":\"YODA2\",\"ccyPair\":\"EURUSD\",\"type\":\"Forward\",\"direction\":\"SELL\",\"tradeDate\":\"2020-08-11\"," +
            "\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2020-08-22\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

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

    @Test
    void validateOneSpotValidTransaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + VALID_SPOT_TRANSACTION + "]"))
                .andExpect(status().isOk())
                .andReturn();

        String errorMessage = result.getResponse().getContentAsString();
        assertThat(errorMessage).isEqualTo("OK");
    }

    @Test
    void validateOneForwardValidTransaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + VALID_FORWARD_TRANSACTION + "]"))
                .andExpect(status().isOk())
                .andReturn();

        String errorMessage = result.getResponse().getContentAsString();
        assertThat(errorMessage).isEqualTo("OK");
    }
}
