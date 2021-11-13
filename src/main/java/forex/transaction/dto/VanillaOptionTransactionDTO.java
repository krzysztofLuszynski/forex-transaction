package forex.transaction.dto;

import forex.transaction.dto.TransactionDTO;
import forex.transaction.validation.DateFormatConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class VanillaOptionTransactionDTO extends TransactionDTO {
    @NotNull(message = "Style can not be null")
    @Pattern(regexp = "EUROPEAN|AMERICAN", message="Style can be only EUROPEAN or AMERICAN")
    String style;

    @NotNull(message = "Strategy can not be null")
    @Pattern(regexp = "CALL", message="Strategy can be only CALL")
    String strategy;

    @NotNull(message = "Delivery date can not be null")
    @DateFormatConstraint(message = "Delivery date format can be only YYYY-mm-dd")
    String deliveryDate;

    @NotNull(message = "Expiry date can not be null")
    @DateFormatConstraint(message = "Expiry date format can be only YYYY-mm-dd")
    String expiryDate;

    @DateFormatConstraint(message = "Excercise start date format can be only YYYY-mm-dd")
    String excerciseStartDate;

    @NotNull(message = "PayCcy can not be null")
    String payCcy;

    BigDecimal premium;
    String premiumCcy;
    String premiumType;

    @DateFormatConstraint(message = "Premium date format can be only YYYY-mm-dd")
    String premiumDate;
}
