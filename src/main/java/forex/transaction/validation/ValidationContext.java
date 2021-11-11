package forex.transaction.validation;

import forex.transaction.domain.Transaction;
import lombok.Value;

@Value
public class ValidationContext<T extends Transaction> {
    T transaction;
    Long transactionNumber;
}
