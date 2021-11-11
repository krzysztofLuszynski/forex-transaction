package forex.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ValidationErrorDTO {
    Long transactionNumber;
    Set<String> affectedFields;
    String message;

    // needed for jackson only
    ValidationErrorDTO() {}
}
