package forex.transaction.validation.rule.vanillaoption;

import forex.transaction.dto.TransactionDTO;
import forex.transaction.dto.VanillaOptionTransactionDTO;
import forex.transaction.validation.ValidationContext;
import forex.transaction.dto.ValidationErrorDTO;
import forex.transaction.validation.ValidationRule;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SupportedStyleValidationRule implements ValidationRule {
    private final Set<String> supportedStyles = new LinkedHashSet<>();

    public SupportedStyleValidationRule(List<String> supportedStyles) {
        this.supportedStyles.addAll(supportedStyles);
    }

    @Override
    public Optional<ValidationErrorDTO> validate(ValidationContext<? extends TransactionDTO> validationContext) {
        VanillaOptionTransactionDTO vanillaOptionTransaction = (VanillaOptionTransactionDTO) validationContext.getTransaction();

        if (!supportedStyles.contains(vanillaOptionTransaction.getStyle())) {
            ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO(
                    validationContext.getTransactionNumber(),
                    Set.of("style"),
                    String.format("Unsupported style: %s, supported values are: %s",
                            vanillaOptionTransaction.getStyle(), supportedStyles)
            );

            return Optional.of(validationErrorDTO);
        }

        return Optional.empty();
    }
}
