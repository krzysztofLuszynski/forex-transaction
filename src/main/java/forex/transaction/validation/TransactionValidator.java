package forex.transaction.validation;

import forex.transaction.dto.TransactionDTO;
import forex.transaction.dto.ValidationErrorDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.annotation.Annotation;
import java.util.*;

public class TransactionValidator {
    public List<ValidationErrorDTO> validate(ValidationContext<TransactionDTO> validationContext) {
        List<ValidationErrorDTO> validationErrorDTOs = new ArrayList<>();
        TransactionDTO transactionDTO = validationContext.getTransactionDTO();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<TransactionDTO>> violations = validator.validate(transactionDTO);
        for (ConstraintViolation<TransactionDTO> violation : violations) {
            Set<String> affectedFields = getAffectedFields(violation);
            validationErrorDTOs.add(
                    new ValidationErrorDTO(
                            validationContext.getTransactionNumber(),
                            affectedFields,
                            violation.getMessageTemplate()
                    )
            );
        }

        return validationErrorDTOs;
    }

    private Set<String> getAffectedFields(ConstraintViolation<TransactionDTO> violation) {
        Annotation annotation = violation.getConstraintDescriptor().getAnnotation();
        if (annotation instanceof FirstDateBeforeSecondDateConstraint) {
            FirstDateBeforeSecondDateConstraint firstDateBeforeSecondDateConstraint = (FirstDateBeforeSecondDateConstraint) annotation;
            return new LinkedHashSet<>(List.of(firstDateBeforeSecondDateConstraint.firstDateStringProperty(),
                    firstDateBeforeSecondDateConstraint.secondDateStringProperty()));
        } else {
            return Set.of(violation.getPropertyPath().toString());
        }

    }
}
