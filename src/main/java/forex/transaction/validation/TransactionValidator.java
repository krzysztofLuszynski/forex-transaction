package forex.transaction.validation;

import forex.transaction.domain.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionValidator {
    private final List<ValidationRule> validationRules;

    public TransactionValidator(List<ValidationRule> validationRules) {
        this.validationRules = validationRules;
    }

    public List<ValidationError> validate(ValidationContext<? extends Transaction> validationContext) {
        List<ValidationError> validationErrors = new ArrayList<>();
        for (ValidationRule validationRule : validationRules) {
            Optional<ValidationError> validationError = validationRule.validate(validationContext);
            validationError.ifPresent(validationErrors::add);
        }

        return validationErrors;
    }
}
