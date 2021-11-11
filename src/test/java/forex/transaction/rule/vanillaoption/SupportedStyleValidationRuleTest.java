package forex.transaction.rule.vanillaoption;

import forex.transaction.dto.TransactionDTO;
import forex.transaction.dto.VanillaOptionTransactionDTO;
import forex.transaction.validation.ValidationContext;
import forex.transaction.dto.ValidationErrorDTO;
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
        VanillaOptionTransactionDTO vanillaOptionTransaction = new VanillaOptionTransactionDTO();
        ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(vanillaOptionTransaction, 1L);

        Optional<ValidationErrorDTO> validationError = supportedLegalEntityValidationRule.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 1L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("style");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Unsupported style: null, supported values are: [EUROPEAN, AMERICAN]");
    }

    @Test
    void validateSupportedEUROPEANStyle() {
        VanillaOptionTransactionDTO vanillaOptionTransaction = new VanillaOptionTransactionDTO();
        vanillaOptionTransaction.setStyle("EUROPEAN");
        ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(vanillaOptionTransaction, 2L);

        Optional<ValidationErrorDTO> validationError = supportedLegalEntityValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    @Test
    void validateSupportedAMERICANStyle() {
        VanillaOptionTransactionDTO vanillaOptionTransaction = new VanillaOptionTransactionDTO();
        vanillaOptionTransaction.setStyle("AMERICAN");
        ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(vanillaOptionTransaction, 3L);

        Optional<ValidationErrorDTO> validationError = supportedLegalEntityValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    @Test
    void validateUnsupportedeuropeanStyle() {
        VanillaOptionTransactionDTO vanillaOptionTransaction = new VanillaOptionTransactionDTO();
        vanillaOptionTransaction.setStyle("european");
        ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(vanillaOptionTransaction, 4L);

        Optional<ValidationErrorDTO> validationError = supportedLegalEntityValidationRule.validate(validationContext);

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

        VanillaOptionTransactionDTO vanillaOptionTransaction = new VanillaOptionTransactionDTO();
        vanillaOptionTransaction.setStyle("EUROPEAN");
        ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(vanillaOptionTransaction, 5L);

        Optional<ValidationErrorDTO> validationError = supportedStyleValidationRuleEmptyList.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 5L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("style");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Unsupported style: EUROPEAN, supported values are: []");
    }
}
