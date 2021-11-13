package forex.transaction.dto;

import forex.transaction.validation.DateFormatConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class AbstractSpotForwardTransactionDTO extends TransactionDTO {
    @NotNull(message = "Value date can not be null")
    @DateFormatConstraint(message = "Value date format can be only yyyy-MM-dd")
    String valueDate;
}
