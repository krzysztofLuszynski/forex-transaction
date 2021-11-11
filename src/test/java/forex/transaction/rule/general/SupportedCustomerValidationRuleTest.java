package forex.transaction.rule.general;

import forex.transaction.domain.Transaction;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.ValidationError;
import forex.transaction.validation.rule.general.SupportedCustomerValidationRule;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SupportedCustomerValidationRuleTest {
    SupportedCustomerValidationRule supportedCustomerValidationRule
            = new SupportedCustomerValidationRule(List.of("YODA1", "YODA2"));

    @Test
    void validateNullCustomer() {
        Transaction transaction = new Transaction(){};
        ValidationContext<Transaction> validationContext = new ValidationContext<>(transaction, 1L);

        Optional<ValidationError> validationError = supportedCustomerValidationRule.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 1L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("customer");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Unsupported customer: null, supported values are: [YODA1, YODA2]");
    }

    @Test
    void validateSupportedYODA1Customer() {
        Transaction transaction = new Transaction(){};
        transaction.setCustomer("YODA1");
        ValidationContext<Transaction> validationContext = new ValidationContext<>(transaction, 2L);

        Optional<ValidationError> validationError = supportedCustomerValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    @Test
    void validateSupportedYODA2Customer() {
        Transaction transaction = new Transaction(){};
        transaction.setCustomer("YODA2");
        ValidationContext<Transaction> validationContext = new ValidationContext<>(transaction, 3L);

        Optional<ValidationError> validationError = supportedCustomerValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    @Test
    void validateUnsupportedyoda1Customer() {
        Transaction transaction = new Transaction(){};
        transaction.setCustomer("yoda1");
        ValidationContext<Transaction> validationContext = new ValidationContext<>(transaction, 4L);

        Optional<ValidationError> validationError = supportedCustomerValidationRule.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 4L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("customer");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Unsupported customer: yoda1, supported values are: [YODA1, YODA2]");
    }

    @Test
    void validateCustomerEmptySupportedCustomerList() {
        SupportedCustomerValidationRule supportedCustomerValidationRuleEmptyList
                = new SupportedCustomerValidationRule(Collections.emptyList());

        Transaction transaction = new Transaction(){};
        transaction.setCustomer("YODA1");
        ValidationContext<Transaction> validationContext = new ValidationContext<>(transaction, 5L);

        Optional<ValidationError> validationError = supportedCustomerValidationRuleEmptyList.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 5L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("customer");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Unsupported customer: YODA1, supported values are: []");
    }
}
