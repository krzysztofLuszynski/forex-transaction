package forex.transaction.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class ForwardTransactionDTODTO extends AbstractSpotForwardTransactionDTO {
    LocalDate valueDate;
}
