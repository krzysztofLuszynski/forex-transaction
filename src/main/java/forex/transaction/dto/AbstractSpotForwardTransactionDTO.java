package forex.transaction.dto;

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
    String valueDate;
}
