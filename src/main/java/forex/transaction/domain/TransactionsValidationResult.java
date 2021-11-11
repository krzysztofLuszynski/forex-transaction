package forex.transaction.domain;

import forex.transaction.validation.ValidationError;
import lombok.Value;

import java.util.List;

@Value
public class TransactionsValidationResult {
    long transactionsNumber;
    List<ValidationError> validationErrors;
}
