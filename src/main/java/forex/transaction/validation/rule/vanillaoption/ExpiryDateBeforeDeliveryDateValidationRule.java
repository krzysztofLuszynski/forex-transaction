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

public class ExpiryDateBeforeDeliveryDateValidationRule implements ValidationRule {
    @Override
    public Optional<ValidationErrorDTO> validate(ValidationContext<? extends TransactionDTO> validationContext) {
        VanillaOptionTransactionDTO vanillaOptionTransaction = (VanillaOptionTransactionDTO) validationContext.getTransactionDTO();

        LocalDate expiryDate = LocalDate.parse(vanillaOptionTransaction.getExpiryDate());
        LocalDate deliveryDate = LocalDate.parse(vanillaOptionTransaction.getDeliveryDate());

        // these null checks should be done before, see solution discussion in README.md
        if (expiryDate != null && deliveryDate != null && !expiryDate.isBefore(deliveryDate)) {
            ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO(
                    validationContext.getTransactionNumber(),
                    new LinkedHashSet<>(List.of("expiryDate", "deliveryDate")),
                    String.format("Expiry date: %s, shall be before delivery date: %s",
                            expiryDate, deliveryDate)
            );

            return Optional.of(validationErrorDTO);
        }

        return Optional.empty();
    }
}
