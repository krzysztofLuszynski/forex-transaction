package forex.transaction.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class SpotTransactionDTO extends AbstractSpotForwardTransactionDTO {
}
