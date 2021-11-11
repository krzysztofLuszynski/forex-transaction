package forex.transaction.validation;

import forex.transaction.dto.TransactionDTO;
import lombok.Value;

@Value
public class ValidationContext<T extends TransactionDTO> {
    T transaction;
    Long transactionNumber;
}
