package forex.transaction.validation.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class SpotTransaction extends Transaction {
    LocalDate valueDate;
}
