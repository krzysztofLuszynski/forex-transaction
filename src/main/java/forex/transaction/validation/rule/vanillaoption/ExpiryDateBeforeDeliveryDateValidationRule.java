package forex.transaction.validation.rule.vanillaoption;

import forex.transaction.domain.Transaction;
import forex.transaction.domain.vanillaoption.VanillaOptionTransaction;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.ValidationError;
import forex.transaction.validation.ValidationRule;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

public class ExpiryDateBeforeDeliveryDateValidationRule implements ValidationRule {
    @Override
    public Optional<ValidationError> validate(ValidationContext<? extends Transaction> validationContext) {
        VanillaOptionTransaction vanillaOptionTransaction = (VanillaOptionTransaction) validationContext.getTransaction();

        LocalDate expiryDate = vanillaOptionTransaction.getExpiryDate();
        LocalDate deliveryDate = vanillaOptionTransaction.getDeliveryDate();

        // these null checks should be done before, see solution discussion in README.md
        if (expiryDate != null && deliveryDate != null && !expiryDate.isBefore(deliveryDate)) {
            ValidationError validationError = new ValidationError(
                    validationContext.getTransactionNumber(),
                    new LinkedHashSet<>(List.of("expiryDate", "deliveryDate")),
                    String.format("Expiry date: %s, shall be before delivery date: %s for vanilla option transaction",
                            expiryDate, deliveryDate)
            );

            return Optional.of(validationError);
        }

        return Optional.empty();
    }
}
