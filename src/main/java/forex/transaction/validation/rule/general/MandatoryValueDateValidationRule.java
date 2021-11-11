package forex.transaction.validation.rule.general;

import forex.transaction.domain.AbstractSpotForwardTransaction;
import forex.transaction.domain.Transaction;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.ValidationError;
import forex.transaction.validation.ValidationRule;

import java.util.Optional;
import java.util.Set;

public class MandatoryValueDateValidationRule implements ValidationRule {
    private final String transactionType;

    public MandatoryValueDateValidationRule(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public Optional<ValidationError> validate(ValidationContext<? extends Transaction> validationContext) {
        AbstractSpotForwardTransaction abstractSpotForwardTransaction = (AbstractSpotForwardTransaction) validationContext.getTransaction();

        if (abstractSpotForwardTransaction.getValueDate() == null) {
            ValidationError validationError = new ValidationError(
                    validationContext.getTransactionNumber(),
                    Set.of("valueDate"),
                    String.format("Value date is mandatory for %s transaction", transactionType)
            );

            return Optional.of(validationError);
        }

        return Optional.empty();
    }
}
