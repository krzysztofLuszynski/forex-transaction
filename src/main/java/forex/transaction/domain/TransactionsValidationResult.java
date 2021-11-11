package forex.transaction.domain;

import forex.transaction.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class TransactionsValidationResult {
    long transactionsNumber;
    List<ValidationError> validationErrors;

    // needed for jackson only
    TransactionsValidationResult() {}
}
