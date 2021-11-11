package forex.transaction.validation.rule.general;

import forex.transaction.dto.AbstractSpotForwardTransactionDTO;
import forex.transaction.dto.TransactionDTO;
import forex.transaction.validation.ValidationContext;
import forex.transaction.dto.ValidationErrorDTO;
import forex.transaction.validation.ValidationRule;

import java.util.Optional;
import java.util.Set;

public class MandatoryValueDateValidationRule implements ValidationRule {
    @Override
    public Optional<ValidationErrorDTO> validate(ValidationContext<? extends TransactionDTO> validationContext) {
        AbstractSpotForwardTransactionDTO abstractSpotForwardTransaction = (AbstractSpotForwardTransactionDTO) validationContext.getTransaction();

        if (abstractSpotForwardTransaction.getValueDate() == null) {
            ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO(
                    validationContext.getTransactionNumber(),
                    Set.of("valueDate"),
                    "Value date is mandatory"
            );

            return Optional.of(validationErrorDTO);
        }

        return Optional.empty();
    }
}
