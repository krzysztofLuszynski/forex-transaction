package forex.transaction.rule.vanillaoption;

import forex.transaction.domain.Transaction;
import forex.transaction.domain.vanillaoption.VanillaOptionTransaction;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.ValidationError;
import forex.transaction.validation.rule.vanillaoption.ExpiryDateBeforeDeliveryDateValidationRule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpiryDateBeforeDeliveryDateValidationRuleTest {
    ExpiryDateBeforeDeliveryDateValidationRule expiryDateBeforeDeliveryDateValidationRule
            = new ExpiryDateBeforeDeliveryDateValidationRule();

    @Test
    void validateExpiryDateNullDeliveryDateNull() {
        VanillaOptionTransaction vanillaOptionTransaction = new VanillaOptionTransaction();
        ValidationContext<Transaction> validationContext = new ValidationContext<>(vanillaOptionTransaction, 1L);

        Optional<ValidationError> validationError = expiryDateBeforeDeliveryDateValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    @Test
    void validateExpiryDateNotNullDeliveryDateNull() {
        VanillaOptionTransaction vanillaOptionTransaction = new VanillaOptionTransaction();
        vanillaOptionTransaction.setExpiryDate(LocalDate.parse("2021-11-11"));
        ValidationContext<Transaction> validationContext = new ValidationContext<>(vanillaOptionTransaction, 2L);

        Optional<ValidationError> validationError = expiryDateBeforeDeliveryDateValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    @Test
    void validateExpiryDateNullDeliveryDateNotNull() {
        VanillaOptionTransaction vanillaOptionTransaction = new VanillaOptionTransaction();
        vanillaOptionTransaction.setDeliveryDate(LocalDate.parse("2021-11-11"));
        ValidationContext<Transaction> validationContext = new ValidationContext<>(vanillaOptionTransaction, 3L);

        Optional<ValidationError> validationError = expiryDateBeforeDeliveryDateValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    @Test
    void validateExpiryDateBeforeDeliveryDate() {
        VanillaOptionTransaction vanillaOptionTransaction = new VanillaOptionTransaction();
        vanillaOptionTransaction.setExpiryDate(LocalDate.parse("2021-11-10"));
        vanillaOptionTransaction.setDeliveryDate(LocalDate.parse("2021-11-11"));
        ValidationContext<Transaction> validationContext = new ValidationContext<>(vanillaOptionTransaction, 4L);

        Optional<ValidationError> validationError = expiryDateBeforeDeliveryDateValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    @Test
    void validateExpiryDateEqualDeliveryDate() {
        VanillaOptionTransaction vanillaOptionTransaction = new VanillaOptionTransaction();
        vanillaOptionTransaction.setExpiryDate(LocalDate.parse("2021-11-11"));
        vanillaOptionTransaction.setDeliveryDate(LocalDate.parse("2021-11-11"));
        ValidationContext<Transaction> validationContext = new ValidationContext<>(vanillaOptionTransaction, 5L);

        Optional<ValidationError> validationError = expiryDateBeforeDeliveryDateValidationRule.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 5L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("expiryDate", "deliveryDate");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Expiry date: 2021-11-11, shall be before delivery date: 2021-11-11 for vanilla option transaction");
    }

    @Test
    void validateExpiryDateAfterDeliveryDate() {
        VanillaOptionTransaction vanillaOptionTransaction = new VanillaOptionTransaction();
        vanillaOptionTransaction.setExpiryDate(LocalDate.parse("2021-11-12"));
        vanillaOptionTransaction.setDeliveryDate(LocalDate.parse("2021-11-11"));
        ValidationContext<Transaction> validationContext = new ValidationContext<>(vanillaOptionTransaction, 6L);

        Optional<ValidationError> validationError = expiryDateBeforeDeliveryDateValidationRule.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 6L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("expiryDate", "deliveryDate");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Expiry date: 2021-11-12, shall be before delivery date: 2021-11-11 for vanilla option transaction");
    }
}
