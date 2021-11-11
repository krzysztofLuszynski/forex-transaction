package forex.transaction.controller;

import forex.transaction.dto.*;
import forex.transaction.dto.SpotTransactionDTODTO;
import forex.transaction.dto.TransactionDTO;
import forex.transaction.dto.vanillaoption.VanillaOptionTransactionDTO;
import forex.transaction.validation.TransactionValidator;
import forex.transaction.validation.ValidationContext;
import forex.transaction.dto.ValidationErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.*;
import java.util.*;

@Slf4j
@RestController
public class TransactionValidationController {
    @Autowired
    @Qualifier("spotTransactionValidator")
    TransactionValidator spotTransactionValidator;

    @Autowired
    @Qualifier("forwardTransactionValidator")
    TransactionValidator forwardTransactionValidator;

    @Autowired
    @Qualifier("vanillaOptionTransactionValidator")
    TransactionValidator vanillaOptionTransactionValidator;

    @PostMapping("/validate")
    TransactionsValidationResultDTO validate(@RequestBody List<TransactionDTO> transactionDTOs) {
        log.debug("Transactions validation for transactions: {}", transactionDTOs);

        List<ValidationErrorDTO> transactionValidationErrorDTOS = new ArrayList<>();
        long transactionNumber = 0;
        for (TransactionDTO transactionDTO : transactionDTOs) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<TransactionDTO>> violations = validator.validate(transactionDTO);
            for (ConstraintViolation<TransactionDTO> violation : violations) {
                transactionValidationErrorDTOS.add(
                        new ValidationErrorDTO(
                                transactionNumber+1,
                                new LinkedHashSet<>(List.of(violation.getPropertyPath().toString())),
                                violation.getMessageTemplate()
                        )
                );
            }

            TransactionValidator transactionValidator = getTransactionValidatorForTransaction(transactionDTO);
            ValidationContext<? extends TransactionDTO> validationContext = createValidationContext(transactionDTO, transactionNumber+1);

            transactionValidationErrorDTOS.addAll(transactionValidator.validate(validationContext));
            transactionNumber++;
        }

        return new TransactionsValidationResultDTO(transactionNumber, transactionValidationErrorDTOS);
    }

    private TransactionValidator getTransactionValidatorForTransaction(TransactionDTO transactionDTO) {
        if (transactionDTO instanceof SpotTransactionDTODTO) {
            return spotTransactionValidator;
        } else if(transactionDTO instanceof ForwardTransactionDTODTO) {
            return forwardTransactionValidator;
        } else if (transactionDTO instanceof VanillaOptionTransactionDTO) {
            return vanillaOptionTransactionValidator;
        }
        else {
            return new TransactionValidator(Collections.emptyList());
        }
    }

    private ValidationContext<? extends TransactionDTO> createValidationContext(TransactionDTO transactionDTO, Long transactionNumber) {
        ValidationContext<? extends TransactionDTO> validationContext;
        if (transactionDTO instanceof SpotTransactionDTODTO) {
            validationContext = new ValidationContext<>((SpotTransactionDTODTO) transactionDTO, transactionNumber);
        } else if (transactionDTO instanceof ForwardTransactionDTODTO) {
            validationContext = new ValidationContext<>((ForwardTransactionDTODTO) transactionDTO, transactionNumber);
        } else if (transactionDTO instanceof VanillaOptionTransactionDTO) {
            validationContext = new ValidationContext<>((VanillaOptionTransactionDTO) transactionDTO, transactionNumber);
        } else {
            validationContext = new ValidationContext<>(transactionDTO, transactionNumber);
        }

        return validationContext;
    }
}
