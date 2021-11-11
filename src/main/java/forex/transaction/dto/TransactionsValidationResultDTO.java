package forex.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TransactionsValidationResultDTO {
    long transactionsNumber;
    List<ValidationErrorDTO> validationErrorDTOS;

    // needed for jackson only
    TransactionsValidationResultDTO() {}
}
