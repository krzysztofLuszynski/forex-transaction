package forex.transaction.validation;

import forex.transaction.dto.TransactionDTO;
import forex.transaction.dto.ValidationErrorDTO;

import java.util.Optional;

public interface ValidationRule {
    Optional<ValidationErrorDTO> validate(ValidationContext<? extends TransactionDTO> validationContext);
}
