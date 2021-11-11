package forex.transaction.validation;

import lombok.Value;

import java.util.Set;

@Value
public class ValidationError {
    Long transactionNumber;
    Set<String> affectedFields;
    String message;
}
