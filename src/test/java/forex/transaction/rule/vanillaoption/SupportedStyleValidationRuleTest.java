package forex.transaction.rule.vanillaoption;

import forex.transaction.domain.Transaction;
import forex.transaction.domain.vanillaoption.VanillaOptionTransaction;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.ValidationError;
import forex.transaction.validation.rule.vanillaoption.SupportedStyleValidationRule;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SupportedStyleValidationRuleTest {
    SupportedStyleValidationRule supportedLegalEntityValidationRule =
            new SupportedStyleValidationRule(List.of("EUROPEAN", "AMERICAN"));

    @Test
    void validateNullStyle() {
        VanillaOptionTransaction vanillaOptionTransaction = new VanillaOptionTransaction();
        ValidationContext<Transaction> validationContext = new ValidationContext<>(vanillaOptionTransaction, 1L);

        Optional<ValidationError> validationError = supportedLegalEntityValidationRule.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 1L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("style");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Unsupported style: null, supported values are: [EUROPEAN, AMERICAN]");
    }

    @Test
    void validateSupportedEUROPEANStyle() {
        VanillaOptionTransaction vanillaOptionTransaction = new VanillaOptionTransaction();
        vanillaOptionTransaction.setStyle("EUROPEAN");
        ValidationContext<Transaction> validationContext = new ValidationContext<>(vanillaOptionTransaction, 2L);

        Optional<ValidationError> validationError = supportedLegalEntityValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    @Test
    void validateSupportedAMERICANStyle() {
        VanillaOptionTransaction vanillaOptionTransaction = new VanillaOptionTransaction();
        vanillaOptionTransaction.setStyle("AMERICAN");
        ValidationContext<Transaction> validationContext = new ValidationContext<>(vanillaOptionTransaction, 3L);

        Optional<ValidationError> validationError = supportedLegalEntityValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    @Test
    void validateUnsupportedeuropeanStyle() {
        VanillaOptionTransaction vanillaOptionTransaction = new VanillaOptionTransaction();
        vanillaOptionTransaction.setStyle("european");
        ValidationContext<Transaction> validationContext = new ValidationContext<>(vanillaOptionTransaction, 4L);

        Optional<ValidationError> validationError = supportedLegalEntityValidationRule.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 4L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("style");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Unsupported style: european, supported values are: [EUROPEAN, AMERICAN]");
    }

    @Test
    void validateCustomerEmptySupportedStyleList() {
        SupportedStyleValidationRule supportedStyleValidationRuleEmptyList
                = new SupportedStyleValidationRule(Collections.emptyList());

        VanillaOptionTransaction vanillaOptionTransaction = new VanillaOptionTransaction();
        vanillaOptionTransaction.setStyle("EUROPEAN");
        ValidationContext<Transaction> validationContext = new ValidationContext<>(vanillaOptionTransaction, 5L);

        Optional<ValidationError> validationError = supportedStyleValidationRuleEmptyList.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 5L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("style");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Unsupported style: EUROPEAN, supported values are: []");
    }
}
