package forex.transaction.rule.general;

import forex.transaction.dto.SpotTransactionDTO;
import forex.transaction.dto.TransactionDTO;
import forex.transaction.validation.ValidationContext;
import forex.transaction.dto.ValidationErrorDTO;
import forex.transaction.validation.rule.general.MandatoryValueDateValidationRule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MandatoryValueDateValidationRuleTest {
    MandatoryValueDateValidationRule mandatoryValueDateValidationRule = new MandatoryValueDateValidationRule();

    @Test
    void validateValueDatePresent() {
        SpotTransactionDTO spotTransactionDTO = new SpotTransactionDTO();
        spotTransactionDTO.setValueDate(LocalDate.parse("2021-11-11"));
        ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(spotTransactionDTO, 1L);

        Optional<ValidationErrorDTO> validationError = mandatoryValueDateValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    @Test
    void validateValueDateMissing() {
        SpotTransactionDTO spotTransactionDTO = new SpotTransactionDTO();
        ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(spotTransactionDTO, 2L);

        Optional<ValidationErrorDTO> validationError = mandatoryValueDateValidationRule.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 2L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("valueDate");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Value date is mandatory");
    }
}
