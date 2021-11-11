package forex.transaction.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SpotTransactionDTO.class, name = "Spot"),
        @JsonSubTypes.Type(value = ForwardTransactionDTO.class, name = "Forward"),
        @JsonSubTypes.Type(value = VanillaOptionTransactionDTO.class, name = "VanillaOption")
})
@Data
public abstract class TransactionDTO {
    @Pattern(regexp = "YODA1|YODA2", message = "Customer can be only YODA1 or YODA2")
    String customer;

    @NotBlank(message = "CcyPair can not be blank")
    String ccyPair;

    @Pattern(regexp = "BUY|SELL", message="Direction can be only BUY or SELL")
    String direction;

    LocalDate tradeDate;

    @NotNull(message = "amount1 can not be null")
    BigDecimal amount1;

    @NotNull(message = "amount2 can not be null")
    BigDecimal amount2;

    @NotNull(message = "rate can not be null")
    BigDecimal rate;

    @Pattern(regexp = "UBS AG", message = "Legal entity can be only UBS AG")
    String legalEntity;

    @NotBlank(message = "Trader can not be blank")
    String trader;
}
