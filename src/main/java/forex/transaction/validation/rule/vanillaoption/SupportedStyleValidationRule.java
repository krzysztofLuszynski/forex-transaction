package forex.transaction.validation.rule.vanillaoption;

import forex.transaction.domain.Transaction;
import forex.transaction.domain.vanillaoption.VanillaOptionTransaction;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.ValidationError;
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
    public Optional<ValidationError> validate(ValidationContext<? extends Transaction> validationContext) {
        VanillaOptionTransaction vanillaOptionTransaction = (VanillaOptionTransaction) validationContext.getTransaction();

        if (!supportedStyles.contains(vanillaOptionTransaction.getStyle())) {
            ValidationError validationError = new ValidationError(
                    validationContext.getTransactionNumber(),
                    Set.of("style"),
                    String.format("Unsupported style: %s, supported values are: %s",
                            vanillaOptionTransaction.getStyle(), supportedStyles)
            );

            return Optional.of(validationError);
        }

        return Optional.empty();
    }
}
