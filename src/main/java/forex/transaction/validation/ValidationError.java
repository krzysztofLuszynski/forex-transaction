package forex.transaction.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ValidationError {
    Long transactionNumber;
    Set<String> affectedFields;
    String message;

    // needed for jackson only
    ValidationError() {}
}
