package forex.transaction.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class ForwardTransaction extends Transaction {
    LocalDate valueDate;
}