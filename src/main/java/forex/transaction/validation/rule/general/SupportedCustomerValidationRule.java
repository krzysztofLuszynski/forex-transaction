package forex.transaction.validation.rule.general;

import forex.transaction.domain.Transaction;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.ValidationError;
import forex.transaction.validation.ValidationRule;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SupportedCustomerValidationRule implements ValidationRule {
    private final Set<String> supportedCustomers = new LinkedHashSet<>();

    public SupportedCustomerValidationRule(List<String> supportedCustomers) {
        this.supportedCustomers.addAll(supportedCustomers);
    }

    @Override
    public Optional<ValidationError> validate(ValidationContext<? extends Transaction> validationContext) {
        Transaction transaction = validationContext.getTransaction();

        if (!supportedCustomers.contains(transaction.getCustomer())) {
            ValidationError validationError = new ValidationError(
                validationContext.getTransactionNumber(),
                    Set.of("customer"),
                    String.format("Unsupported customer: %s, supported values are: %s",
                            transaction.getCustomer(), supportedCustomers)
            );

            return Optional.of(validationError);
        }

        return Optional.empty();
    }
}
