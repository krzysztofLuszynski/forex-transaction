package forex.transaction.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import forex.transaction.validation.DateFormatConstraint;
import forex.transaction.validation.currency.CurrencyPairConstraint;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SpotTransactionDTO.class, name = "Spot"),
        @JsonSubTypes.Type(value = ForwardTransactionDTO.class, name = "Forward"),
        @JsonSubTypes.Type(value = VanillaOptionTransactionDTO.class, name = "VanillaOption")
})
@Data
public abstract class TransactionDTO {
    @NotNull(message = "Customer can not be null")
    @Pattern(regexp = "YODA1|YODA2", message = "Customer can be only YODA1 or YODA2")
    String customer;

    @NotNull(message = "CcyPair can not be null")
    @CurrencyPairConstraint(message = "CcyPair must contain two ISO-4217 currencies")
    String ccyPair;

    @NotNull(message = "Direction can not be null")
    @Pattern(regexp = "BUY|SELL", message="Direction can be only BUY or SELL")
    String direction;

    @NotNull(message = "Trade date can not be null")
    @DateFormatConstraint(message = "Trade date format can be only yyyy-MM-dd")
    String tradeDate;

    @NotNull(message = "Amount1 can not be null")
    BigDecimal amount1;

    @NotNull(message = "Amount2 can not be null")
    BigDecimal amount2;

    @NotNull(message = "Rate can not be null")
    BigDecimal rate;

    @NotNull(message = "Legal entity can not be null")
    @Pattern(regexp = "UBS AG", message = "Legal entity can be only UBS AG")
    String legalEntity;

    @NotBlank(message = "Trader can not be blank")
    String trader;
}
