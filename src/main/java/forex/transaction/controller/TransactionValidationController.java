package forex.transaction.controller;

import forex.transaction.domain.ForwardTransaction;
import forex.transaction.domain.SpotTransaction;
import forex.transaction.domain.Transaction;
import forex.transaction.domain.TransactionsValidationResult;
import forex.transaction.domain.vanillaoption.VanillaOptionTransaction;
import forex.transaction.validation.TransactionValidator;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    TransactionsValidationResult validate(@RequestBody List<Transaction> transactions) {
        log.debug("Transactions validation for transactions: {}", transactions);

        List<ValidationError> transactionValidationErrors = new ArrayList<>();
        long transactionNumber = 0;
        for (Transaction transaction: transactions) {
            TransactionValidator transactionValidator = getTransactionValidatorForTransaction(transaction);
            ValidationContext<? extends Transaction> validationContext = createValidationContext(transaction, transactionNumber+1);

            transactionValidationErrors.addAll(transactionValidator.validate(validationContext));
            transactionNumber++;
        }

        return new TransactionsValidationResult(transactionNumber, transactionValidationErrors);
    }

    private TransactionValidator getTransactionValidatorForTransaction(Transaction transaction) {
        if (transaction instanceof SpotTransaction) {
            return spotTransactionValidator;
        } else if(transaction instanceof ForwardTransaction) {
            return forwardTransactionValidator;
        } else if (transaction instanceof VanillaOptionTransaction) {
            return vanillaOptionTransactionValidator;
        }
        else {
            return new TransactionValidator(Collections.emptyList());
        }
    }

    private ValidationContext<? extends Transaction> createValidationContext(Transaction transaction, Long transactionNumber) {
        ValidationContext<? extends Transaction> validationContext;
        if (transaction instanceof SpotTransaction) {
            validationContext = new ValidationContext<>((SpotTransaction) transaction, transactionNumber);
        } else if (transaction instanceof ForwardTransaction) {
            validationContext = new ValidationContext<>((ForwardTransaction) transaction, transactionNumber);
        } else if (transaction instanceof VanillaOptionTransaction) {
            validationContext = new ValidationContext<>((VanillaOptionTransaction) transaction, transactionNumber);
        } else {
            validationContext = new ValidationContext<>(transaction, transactionNumber);
        }

        return validationContext;
    }
}
