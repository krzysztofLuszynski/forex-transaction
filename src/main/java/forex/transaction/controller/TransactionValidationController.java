package forex.transaction.controller;

import forex.transaction.dto.TransactionDTO;
import forex.transaction.dto.TransactionsValidationResultDTO;
import forex.transaction.dto.ValidationErrorDTO;
import forex.transaction.validation.TransactionValidator;
import forex.transaction.validation.ValidationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class TransactionValidationController {
    @PostMapping(value = "/validate", produces = "application/json")
    ResponseEntity<TransactionsValidationResultDTO> validate(@RequestBody List<TransactionDTO> transactionDTOs) {
        log.debug("Transactions validation for transactions: {}", transactionDTOs);

        TransactionValidator transactionValidator = new TransactionValidator();

        List<ValidationErrorDTO> transactionValidationErrorDTOs = createValidationContexts(transactionDTOs)
                .stream()
                .map(transactionValidator::validate)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        TransactionsValidationResultDTO transactionsValidationResultDTO
                = new TransactionsValidationResultDTO(transactionDTOs.size(), transactionValidationErrorDTOs);

        if (transactionValidationErrorDTOs.isEmpty()) {
            log.debug("Validation did not report any errors");
            return new ResponseEntity<>(transactionsValidationResultDTO, HttpStatus.OK);
        } else {
            log.debug("Validation reported errors: {}", transactionValidationErrorDTOs);
            return new ResponseEntity<>(transactionsValidationResultDTO, HttpStatus.BAD_REQUEST);
        }
    }

    private List<ValidationContext<TransactionDTO>> createValidationContexts(List<TransactionDTO> transactionDTOs) {
        List<ValidationContext<TransactionDTO>> validationContexts = new ArrayList<>();
        
        long transactionNumber = 0;
        for (TransactionDTO transactionDTO : transactionDTOs) {
            ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(transactionDTO, transactionNumber+1);
            validationContexts.add(validationContext);
            transactionNumber++;
        }

        return validationContexts;
    }
}
