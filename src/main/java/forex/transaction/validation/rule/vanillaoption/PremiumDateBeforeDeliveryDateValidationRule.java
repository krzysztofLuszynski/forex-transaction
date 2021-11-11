package forex.transaction.validation.rule.vanillaoption;

import forex.transaction.dto.TransactionDTO;
import forex.transaction.dto.VanillaOptionTransactionDTO;
import forex.transaction.validation.ValidationContext;
import forex.transaction.dto.ValidationErrorDTO;
import forex.transaction.validation.ValidationRule;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

public class PremiumDateBeforeDeliveryDateValidationRule implements ValidationRule {
    @Override
    public Optional<ValidationErrorDTO> validate(ValidationContext<? extends TransactionDTO> validationContext) {
        VanillaOptionTransactionDTO vanillaOptionTransaction = (VanillaOptionTransactionDTO) validationContext.getTransaction();

        LocalDate premiumDate = vanillaOptionTransaction.getPremiumDate();
        LocalDate deliveryDate = vanillaOptionTransaction.getDeliveryDate();

        // these null checks should be done before, see solution discussion in README.md
        if (premiumDate != null && deliveryDate != null && !premiumDate.isBefore(deliveryDate)) {
            ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO(
                    validationContext.getTransactionNumber(),
                    new LinkedHashSet<>(List.of("premiumDate", "deliveryDate")),
                    String.format("Premium date: %s, shall be before delivery date: %s",
                            premiumDate, deliveryDate)
            );

            return Optional.of(validationErrorDTO);
        }

        return Optional.empty();
    }
}
