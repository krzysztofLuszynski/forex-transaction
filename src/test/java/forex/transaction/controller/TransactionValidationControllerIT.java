package forex.transaction.controller;

import forex.transaction.dto.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionValidationControllerIT extends AbstractTransactionValidationControllerIT {
    @Test
    void validateFourValidTransaction() throws Exception {
        TransactionsValidationResultDTO transactionsValidationResultDTO = validateValidTransactions(
                getValidSpotTransactionDTO(),
                getValidForwardTransactionDTO(),
                getValidVanillaOptionEuropeanTransactionDTO(),
                getValidVanillaOptionAmericanTransactionDTO());

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(4);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).isEmpty();
    }

    @Test
    void validateEightInvalidTransaction() throws Exception {
        SpotTransactionDTO invalidSpotTransactionDTO = getValidSpotTransactionDTO();
        invalidSpotTransactionDTO.setCustomer("yoda1");
        invalidSpotTransactionDTO.setLegalEntity("ubs ag");

        ForwardTransactionDTO invalidForwardTransactionDTO = getValidForwardTransactionDTO();
        invalidForwardTransactionDTO.setCustomer("yoda4");
        invalidForwardTransactionDTO.setLegalEntity("ubs aj");

        VanillaOptionTransactionDTO invalidVanillaOptionEuropeanTransactionDTO = getValidVanillaOptionEuropeanTransactionDTO();
        invalidVanillaOptionEuropeanTransactionDTO.setCustomer("yoda4");
        invalidVanillaOptionEuropeanTransactionDTO.setLegalEntity("ubs aj");

        VanillaOptionTransactionDTO invalidVanillaOptionAmericanTransactionDTO = getValidVanillaOptionAmericanTransactionDTO();
        invalidVanillaOptionAmericanTransactionDTO.setCustomer("yoda4");
        invalidVanillaOptionAmericanTransactionDTO.setLegalEntity("ubs aj");


        TransactionsValidationResultDTO transactionsValidationResultDTO = validateInvalidTransactions(
                getValidSpotTransactionDTO(),
                invalidVanillaOptionAmericanTransactionDTO,
                invalidSpotTransactionDTO,
                getValidForwardTransactionDTO(),
                getValidVanillaOptionEuropeanTransactionDTO(),
                invalidForwardTransactionDTO,
                getValidVanillaOptionAmericanTransactionDTO(),
                invalidVanillaOptionEuropeanTransactionDTO);

        assertThat(transactionsValidationResultDTO.getTransactionsNumber()).isEqualTo(8);
        assertThat(transactionsValidationResultDTO.getValidationErrorDTOS()).containsExactlyInAnyOrder(
                new ValidationErrorDTO(2L, Set.of("customer"), "Customer can be only YODA1 or YODA2"),
                new ValidationErrorDTO(2L, Set.of("legalEntity"), "Legal entity can be only UBS AG"),
                new ValidationErrorDTO(3L, Set.of("customer"), "Customer can be only YODA1 or YODA2"),
                new ValidationErrorDTO(3L, Set.of("legalEntity"), "Legal entity can be only UBS AG"),
                new ValidationErrorDTO(6L, Set.of("customer"), "Customer can be only YODA1 or YODA2"),
                new ValidationErrorDTO(6L, Set.of("legalEntity"), "Legal entity can be only UBS AG"),
                new ValidationErrorDTO(8L, Set.of("customer"), "Customer can be only YODA1 or YODA2"),
                new ValidationErrorDTO(8L, Set.of("legalEntity"), "Legal entity can be only UBS AG")
        );
    }
}
