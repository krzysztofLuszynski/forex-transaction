package forex.transaction.dto;

import forex.transaction.validation.currency.CurrencyConstraint;
import forex.transaction.validation.DateFormatConstraint;
import forex.transaction.validation.FirstDateBeforeSecondDateConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
@FirstDateBeforeSecondDateConstraint.List({
        @FirstDateBeforeSecondDateConstraint(
            firstDateStringProperty = "expiryDate",
            secondDateStringProperty = "deliveryDate",
            message = "Expiry date can not be after or equal delivery date"),
        @FirstDateBeforeSecondDateConstraint(
            firstDateStringProperty = "premiumDate",
            secondDateStringProperty = "deliveryDate",
            message = "Premium date can not be after or equal delivery date"),
        @FirstDateBeforeSecondDateConstraint(
            firstDateStringProperty = "tradeDate",
            secondDateStringProperty = "exerciseStartDate",
            isApplicableBooleanProperty = "applicableForAmericanStyle",
            message = "Trade date can not be after or equal exercise start date"),
        @FirstDateBeforeSecondDateConstraint(
            firstDateStringProperty = "exerciseStartDate",
            secondDateStringProperty = "expiryDate",
            isApplicableBooleanProperty = "applicableForAmericanStyle",
            message = "Exercise start date can not be after or equal expiry date")
})
public class VanillaOptionTransactionDTO extends TransactionDTO {
    @NotNull(message = "Style can not be null")
    @Pattern(regexp = "EUROPEAN|AMERICAN", message="Style can be only EUROPEAN or AMERICAN")
    String style;

    @NotNull(message = "Strategy can not be null")
    @Pattern(regexp = "CALL", message="Strategy can be only CALL")
    String strategy;

    @NotNull(message = "Delivery date can not be null")
    @DateFormatConstraint(message = "Delivery date format can be only yyyy-MM-dd")
    String deliveryDate;

    @NotNull(message = "Expiry date can not be null")
    @DateFormatConstraint(message = "Expiry date format can be only yyyy-MM-dd")
    String expiryDate;

    @DateFormatConstraint(message = "Exercise start date format can be only yyyy-MM-dd")
    String exerciseStartDate;

    @NotNull(message = "PayCcy can not be null")
    @CurrencyConstraint(message = "PayCcy must be ISO-4217 currency")
    String payCcy;

    BigDecimal premium;

    @CurrencyConstraint(message = "PremiumCcy must be ISO-4217 currency")
    String premiumCcy;

    String premiumType;

    @DateFormatConstraint(message = "Premium date format can be only yyyy-MM-dd")
    String premiumDate;

    @SuppressWarnings("unused")
    public boolean getApplicableForAmericanStyle() {
        return StringUtils.equals(style, "AMERICAN");
    }
}
