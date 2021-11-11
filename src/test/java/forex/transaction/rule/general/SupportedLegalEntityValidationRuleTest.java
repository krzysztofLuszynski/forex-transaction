package forex.transaction.rule.general;

import forex.transaction.domain.Transaction;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.ValidationError;
import forex.transaction.validation.rule.general.SupportedLegalEntityValidationRule;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SupportedLegalEntityValidationRuleTest {
    SupportedLegalEntityValidationRule supportedLegalEntityValidationRule =
            new SupportedLegalEntityValidationRule(List.of("UBS AG"));

    @Test
    void validateSupportedUBSAGLegalEntity() {
        Transaction transaction = new Transaction(){};
        transaction.setLegalEntity("UBS AG");
        ValidationContext<Transaction> validationContext = new ValidationContext<>(transaction, 1L);

        Optional<ValidationError> validationError = supportedLegalEntityValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    @Test
    void validateUnsupportedubsAGCustomer() {
        Transaction transaction = new Transaction(){};
        transaction.setLegalEntity("ubs AG");
        ValidationContext<Transaction> validationContext = new ValidationContext<>(transaction, 2L);

        Optional<ValidationError> validationError = supportedLegalEntityValidationRule.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 2L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("legalEntity");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Unsupported legal entity: ubs AG, supported values are: [UBS AG]");
    }

    @Test
    void validateCustomerEmptySupportedCustomerList() {
        SupportedLegalEntityValidationRule supportedCustomerValidationRuleEmptyList
                = new SupportedLegalEntityValidationRule(Collections.emptyList());

        Transaction transaction = new Transaction(){};
        transaction.setLegalEntity("UBS AG");
        ValidationContext<Transaction> validationContext = new ValidationContext<>(transaction, 3L);

        Optional<ValidationError> validationError = supportedCustomerValidationRuleEmptyList.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 3L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("legalEntity");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Unsupported legal entity: UBS AG, supported values are: []");
    }
}
