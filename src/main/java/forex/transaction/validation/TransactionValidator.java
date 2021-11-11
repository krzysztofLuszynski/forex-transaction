package forex.transaction.validation;

import forex.transaction.dto.TransactionDTO;
import forex.transaction.dto.ValidationErrorDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionValidator {
    private final List<ValidationRule> validationRules;

    public TransactionValidator(List<ValidationRule> validationRules) {
        this.validationRules = validationRules;
    }

    public List<ValidationErrorDTO> validate(ValidationContext<? extends TransactionDTO> validationContext) {
        List<ValidationErrorDTO> validationErrorDTOS = new ArrayList<>();
        for (ValidationRule validationRule : validationRules) {
            Optional<ValidationErrorDTO> validationError = validationRule.validate(validationContext);
            validationError.ifPresent(validationErrorDTOS::add);
        }

        return validationErrorDTOS;
    }
}
