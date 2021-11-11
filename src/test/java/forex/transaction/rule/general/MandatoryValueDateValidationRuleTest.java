package forex.transaction.rule.general;

import forex.transaction.domain.SpotTransaction;
import forex.transaction.domain.Transaction;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.ValidationError;
import forex.transaction.validation.rule.general.MandatoryValueDateValidationRule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MandatoryValueDateValidationRuleTest {
    MandatoryValueDateValidationRule mandatoryValueDateValidationRule = new MandatoryValueDateValidationRule("spot");

    @Test
    void validateValueDatePresent() {
        SpotTransaction spotTransaction = new SpotTransaction();
        spotTransaction.setValueDate(LocalDate.parse("2021-11-11"));
        ValidationContext<Transaction> validationContext = new ValidationContext<>(spotTransaction, 1L);

        Optional<ValidationError> validationError = mandatoryValueDateValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    @Test
    void validateValueDateMissing() {
        SpotTransaction spotTransaction = new SpotTransaction();
        ValidationContext<Transaction> validationContext = new ValidationContext<>(spotTransaction, 2L);

        Optional<ValidationError> validationError = mandatoryValueDateValidationRule.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 2L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("valueDate");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Value date is mandatory for spot transaction");
    }
}
