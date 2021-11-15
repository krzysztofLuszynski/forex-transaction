package forex.transaction.validation;

import forex.transaction.dto.TransactionDTO;
import forex.transaction.dto.ValidationErrorDTO;
import forex.transaction.validation.date.FirstDateBeforeSecondDateConstraint;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionValidator {
    public List<ValidationErrorDTO> validate(ValidationContext<TransactionDTO> validationContext) {
        TransactionDTO transactionDTO = validationContext.getTransactionDTO();

        return getConstraintViolations(transactionDTO).stream()
                .map(violation -> getValidationErrorForViolation(violation, validationContext))
                .sorted(Comparator.comparing(ValidationErrorDTO::getMessage, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    private Set<ConstraintViolation<TransactionDTO>> getConstraintViolations(TransactionDTO transactionDTO) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(transactionDTO);
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

    ValidationErrorDTO getValidationErrorForViolation(ConstraintViolation<TransactionDTO> violation,
                                                      ValidationContext<TransactionDTO> validationContext) {
        Set<String> affectedFields = getAffectedFields(violation);
        return new ValidationErrorDTO(
                validationContext.getTransactionNumber(),
                affectedFields,
                violation.getMessageTemplate()
        );
    }
}
