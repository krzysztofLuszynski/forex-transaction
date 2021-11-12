package forex.transaction.validation;

import forex.transaction.dto.TransactionDTO;
import forex.transaction.dto.ValidationErrorDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class TransactionValidator {
    public List<ValidationErrorDTO> validate(ValidationContext<TransactionDTO> validationContext) {
        List<ValidationErrorDTO> validationErrorDTOs = new ArrayList<>();
        TransactionDTO transactionDTO = validationContext.getTransactionDTO();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<TransactionDTO>> violations = validator.validate(transactionDTO);
        for (ConstraintViolation<TransactionDTO> violation : violations) {
            validationErrorDTOs.add(
                    new ValidationErrorDTO(
                            validationContext.getTransactionNumber(),
                            new LinkedHashSet<>(List.of(violation.getPropertyPath().toString())),
                            violation.getMessageTemplate()
                    )
            );
        }

        return validationErrorDTOs;
    }
}
