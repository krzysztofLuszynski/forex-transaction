package forex.transaction.dto;

import forex.transaction.dto.TransactionDTO;
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
    @Pattern(regexp = "EUROPEAN|AMERICAN", message="Style can be only EUROPEAN or AMERICAN")
    String style;

    @Pattern(regexp = "CALL", message="Strategy can be only CALL")
    String strategy;

    LocalDate deliveryDate;

    LocalDate expiryDate;

    LocalDate excerciseStartDate;

    @NotNull(message = "PayCcy can not be null")
    String payCcy;

    BigDecimal premium;
    String premiumCcy;
    String premiumType;
    LocalDate premiumDate;
}
