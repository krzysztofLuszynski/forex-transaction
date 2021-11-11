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

public class PremiumDateBeforeDeliveryDateValidationRule implements ValidationRule {
    @Override
    public Optional<ValidationError> validate(ValidationContext<? extends Transaction> validationContext) {
        VanillaOptionTransaction vanillaOptionTransaction = (VanillaOptionTransaction) validationContext.getTransaction();

        LocalDate premiumDate = vanillaOptionTransaction.getPremiumDate();
        LocalDate deliveryDate = vanillaOptionTransaction.getDeliveryDate();

        // these null checks should be done before, see solution discussion in README.md
        if (premiumDate != null && deliveryDate != null && !premiumDate.isBefore(deliveryDate)) {
            ValidationError validationError = new ValidationError(
                    validationContext.getTransactionNumber(),
                    new LinkedHashSet<>(List.of("premiumDate", "deliveryDate")),
                    String.format("Premium date: %s, shall be before delivery date: %s",
                            premiumDate, deliveryDate)
            );

            return Optional.of(validationError);
        }

        return Optional.empty();
    }
}
