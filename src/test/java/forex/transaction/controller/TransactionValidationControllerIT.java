package forex.transaction.controller;

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
    private static final String VALID_SPOT_TRANSACTION =
            "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\"," +
                    "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"valueDate\":\"2020-08-15\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

    private static final String INVALID_SPOT_TRANSACTION =
            "{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\"," +
                    "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

    private static final String VALID_FORWARD_TRANSACTION =
            "{\"customer\":\"YODA2\",\"ccyPair\":\"EURUSD\",\"type\":\"Forward\",\"direction\":\"SELL\"," +
                    "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"valueDate\":\"2020-08-22\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

    private static final String INVALID_FORWARD_TRANSACTION =
            "{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"Forward\",\"direction\":\"SELL\"," +
                    "\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

    private static final String VALID_VANILLA_OPTION_TRANSACTION_1 =
            "{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN\"," +
                    "\"direction\":\"BUY\",\"strategy\":\"CALL\",\"tradeDate\":\"2020-08-11\"," +
                    "\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-19\",\"payCcy\":\"USD\"," +
                    "\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                    "\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

    private static final String VALID_VANILLA_OPTION_TRANSACTION_2 =
            "{\"customer\":\"YODA2\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"AMERICAN\"," +
                    "\"direction\":\"SELL\",\"strategy\":\"CALL\",\"tradeDate\":\"2020-08-11\"," +
                    "\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-21\"," +
                    "\"excerciseStartDate\":\"2020-08-12\",\"payCcy\":\"USD\"," +
                    "\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                    "\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

    private static final String INVALID_VANILLA_OPTION_TRANSACTION =
            "{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN\"," +
                    "\"direction\":\"BUY\",\"strategy\":\"CALL\",\"tradeDate\":\"2020-08-11\"," +
                    "\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12," +
                    "\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-23\",\"payCcy\":\"USD\"," +
                    "\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\"," +
                    "\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}";

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
        assertThat(errorMessage).isEqualTo("{\"transactionsNumber\":0,\"validationErrors\":[]}");
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
        assertThat(errorMessage).isEqualTo("{\"transactionsNumber\":1,\"validationErrors\":[]}");
    }

    @Test
    void validateOneSpotInvalidTransaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + INVALID_SPOT_TRANSACTION + "]"))
                .andExpect(status().isOk())
                .andReturn();

        String errorMessage = result.getResponse().getContentAsString();
        assertThat(errorMessage).isEqualTo("{\"transactionsNumber\":1,\"validationErrors\":[" +
                "{\"transactionNumber\":1,\"affectedFields\":[\"customer\"]," +
                "\"message\":\"Unsupported customer: YODA4, supported values are: [YODA1, YODA2]\"}," +
                "{\"transactionNumber\":1,\"affectedFields\":[\"valueDate\"]," +
                "\"message\":\"Value date is mandatory for spot transaction\"}]}"
        );
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
        assertThat(errorMessage).isEqualTo("{\"transactionsNumber\":1,\"validationErrors\":[]}");
    }

    @Test
    void validateOneForwardInvalidTransaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + INVALID_FORWARD_TRANSACTION + "]"))
                .andExpect(status().isOk())
                .andReturn();

        String errorMessage = result.getResponse().getContentAsString();
        assertThat(errorMessage).isEqualTo("{\"transactionsNumber\":1,\"validationErrors\":[" +
                "{\"transactionNumber\":1,\"affectedFields\":[\"customer\"]," +
                "\"message\":\"Unsupported customer: YODA4, supported values are: [YODA1, YODA2]\"}," +
                "{\"transactionNumber\":1,\"affectedFields\":[\"valueDate\"]," +
                "\"message\":\"Value date is mandatory for forward transaction\"}]}"
        );
    }

    @Test
    void validateOneVanillaOptionValid1Transaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + VALID_VANILLA_OPTION_TRANSACTION_1 + "]"))
                .andExpect(status().isOk())
                .andReturn();

        String errorMessage = result.getResponse().getContentAsString();
        assertThat(errorMessage).isEqualTo("{\"transactionsNumber\":1,\"validationErrors\":[]}");
    }

    @Test
    void validateOneVanillaOptionValid2Transaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + VALID_VANILLA_OPTION_TRANSACTION_2 + "]"))
                .andExpect(status().isOk())
                .andReturn();

        String errorMessage = result.getResponse().getContentAsString();
        assertThat(errorMessage).isEqualTo("{\"transactionsNumber\":1,\"validationErrors\":[]}");
    }

    @Test
    void validateOneVanillaOptionInvalidTransaction() throws Exception {
        MvcResult result = mvc.perform(
                        post("/validate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("[" + INVALID_VANILLA_OPTION_TRANSACTION + "]"))
                .andExpect(status().isOk())
                .andReturn();

        String errorMessage = result.getResponse().getContentAsString();
        assertThat(errorMessage).isEqualTo("{\"transactionsNumber\":1,\"validationErrors\":[" +
                "{\"transactionNumber\":1,\"affectedFields\":[\"customer\"]," +
                "\"message\":\"Unsupported customer: YODA4, supported values are: [YODA1, YODA2]\"}," +
                "{\"transactionNumber\":1,\"affectedFields\":[\"expiryDate\",\"deliveryDate\"]," +
                "\"message\":\"Expiry date: 2020-08-23, shall be before delivery date: 2020-08-22\"}]}"
        );
    }
}
