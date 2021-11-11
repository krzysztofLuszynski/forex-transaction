package forex.transaction.validation.rule.general;

import forex.transaction.domain.Transaction;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.ValidationError;
import forex.transaction.validation.ValidationRule;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SupportedLegalEntityValidationRule implements ValidationRule {
    private final Set<String> supportedLegalEntities = new LinkedHashSet<>();

    public SupportedLegalEntityValidationRule(List<String> supportedLegalEntities) {
        this.supportedLegalEntities.addAll(supportedLegalEntities);
    }

    @Override
    public Optional<ValidationError> validate(ValidationContext<? extends Transaction> validationContext) {
        Transaction transaction = validationContext.getTransaction();

        if (!supportedLegalEntities.contains(transaction.getLegalEntity())) {
            ValidationError validationError = new ValidationError(
                validationContext.getTransactionNumber(),
                    Set.of("legalEntity"),
                    String.format("Unsupported legal entity: %s, supported values are: %s",
                            transaction.getLegalEntity(), supportedLegalEntities)
            );

            return Optional.of(validationError);
        }

        return Optional.empty();
    }
}
