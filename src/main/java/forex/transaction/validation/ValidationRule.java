package forex.transaction.validation;

import forex.transaction.domain.Transaction;

import java.util.Optional;

public interface ValidationRule {
    Optional<ValidationError> validate(ValidationContext<? extends Transaction> validationContext);
}
