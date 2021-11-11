package forex.transaction.validation.rule.spot;

import forex.transaction.domain.SpotTransaction;
import forex.transaction.domain.Transaction;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.ValidationError;
import forex.transaction.validation.ValidationRule;

import java.util.Optional;
import java.util.Set;

public class MandatoryValueDateValidationRule implements ValidationRule {
    @Override
    public Optional<ValidationError> validate(ValidationContext<? extends Transaction> validationContext) {
        SpotTransaction spotTransaction = (SpotTransaction) validationContext.getTransaction();

        if (spotTransaction.getValueDate() == null) {
            ValidationError validationError = new ValidationError(
                    validationContext.getTransactionNumber(),
                    Set.of("valueDate"),
                    "Value date is mandatory for spot transaction"
            );

            return Optional.of(validationError);
        }

        return Optional.empty();
    }
}
