package forex.transaction.validation.rule.general;

import forex.transaction.domain.AbstractSpotForwardTransaction;
import forex.transaction.domain.Transaction;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.ValidationError;
import forex.transaction.validation.ValidationRule;

import java.util.Optional;
import java.util.Set;

public class MandatoryValueDateValidationRule implements ValidationRule {
    @Override
    public Optional<ValidationError> validate(ValidationContext<? extends Transaction> validationContext) {
        AbstractSpotForwardTransaction abstractSpotForwardTransaction = (AbstractSpotForwardTransaction) validationContext.getTransaction();

        if (abstractSpotForwardTransaction.getValueDate() == null) {
            ValidationError validationError = new ValidationError(
                    validationContext.getTransactionNumber(),
                    Set.of("valueDate"),
                    "Value date is mandatory"
            );

            return Optional.of(validationError);
        }

        return Optional.empty();
    }
}
