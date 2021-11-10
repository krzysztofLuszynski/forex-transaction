package forex.transaction.validation.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SpotTransaction.class, name = "Spot"),
        @JsonSubTypes.Type(value = ForwardTransaction.class, name = "Forward")
})
@Data
public class Transaction {
    String customer;
    String ccyPair;
    TransactionDirection direction;
    LocalDate tradeDate;
    BigDecimal amount1;
    BigDecimal amount2;
    BigDecimal rate;
    String legalEntity;
    String trader;
}
