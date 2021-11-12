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

@Slf4j
@RestController
public class TransactionValidationController {
    @PostMapping(value = "/validate", produces = "application/json")
    ResponseEntity<TransactionsValidationResultDTO> validate(@RequestBody List<TransactionDTO> transactionDTOs) {
        log.debug("Transactions validation for transactions: {}", transactionDTOs);

        TransactionValidator transactionValidator = new TransactionValidator();
        List<ValidationErrorDTO> transactionValidationErrorDTOs = new ArrayList<>();
        long transactionNumber = 0;
        for (TransactionDTO transactionDTO : transactionDTOs) {
            ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(transactionDTO, transactionNumber+1);
            transactionValidationErrorDTOs.addAll(transactionValidator.validate(validationContext));
            transactionNumber++;
        }

        TransactionsValidationResultDTO transactionsValidationResultDTO
                = new TransactionsValidationResultDTO(transactionNumber, transactionValidationErrorDTOs);

        if (transactionValidationErrorDTOs.isEmpty()) {
            return new ResponseEntity<>(transactionsValidationResultDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(transactionsValidationResultDTO, HttpStatus.BAD_REQUEST);
        }
    }
}
