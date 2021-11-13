package forex.transaction.rule.vanillaoption;

import forex.transaction.dto.TransactionDTO;
import forex.transaction.dto.ValidationErrorDTO;
import forex.transaction.dto.VanillaOptionTransactionDTO;
import forex.transaction.validation.ValidationContext;
import forex.transaction.validation.rule.vanillaoption.PremiumDateBeforeDeliveryDateValidationRule;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PremiumDateBeforeDeliveryDateValidationRuleTest {
    PremiumDateBeforeDeliveryDateValidationRule premiumDateBeforeDeliveryDateValidationRule
            = new PremiumDateBeforeDeliveryDateValidationRule();

    //@Test
    void validatePremiumDateNullDeliveryDateNull() {
        VanillaOptionTransactionDTO vanillaOptionTransaction = new VanillaOptionTransactionDTO();
        ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(vanillaOptionTransaction, 1L);

        Optional<ValidationErrorDTO> validationError = premiumDateBeforeDeliveryDateValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    //@Test
    void validatePremiumDateNotNullDeliveryDateNull() {
        VanillaOptionTransactionDTO vanillaOptionTransaction = new VanillaOptionTransactionDTO();
        vanillaOptionTransaction.setPremiumDate("2021-11-11");
        ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(vanillaOptionTransaction, 2L);

        Optional<ValidationErrorDTO> validationError = premiumDateBeforeDeliveryDateValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    //@Test
    void validatePremiumDateNullDeliveryDateNotNull() {
        VanillaOptionTransactionDTO vanillaOptionTransaction = new VanillaOptionTransactionDTO();
        vanillaOptionTransaction.setDeliveryDate("2021-11-11");
        ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(vanillaOptionTransaction, 3L);

        Optional<ValidationErrorDTO> validationError = premiumDateBeforeDeliveryDateValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    //@Test
    void validatePremiumDateBeforeDeliveryDate() {
        VanillaOptionTransactionDTO vanillaOptionTransaction = new VanillaOptionTransactionDTO();
        vanillaOptionTransaction.setPremiumDate("2021-11-10");
        vanillaOptionTransaction.setDeliveryDate("2021-11-11");
        ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(vanillaOptionTransaction, 4L);

        Optional<ValidationErrorDTO> validationError = premiumDateBeforeDeliveryDateValidationRule.validate(validationContext);

        assertThat(validationError).isEmpty();
    }

    //@Test
    void validatePremiumDateEqualDeliveryDate() {
        VanillaOptionTransactionDTO vanillaOptionTransaction = new VanillaOptionTransactionDTO();
        vanillaOptionTransaction.setPremiumDate("2021-11-11");
        vanillaOptionTransaction.setDeliveryDate("2021-11-11");
        ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(vanillaOptionTransaction, 5L);

        Optional<ValidationErrorDTO> validationError = premiumDateBeforeDeliveryDateValidationRule.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 5L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("premiumDate", "deliveryDate");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Premium date: 2021-11-11, shall be before delivery date: 2021-11-11");
    }

    //@Test
    void validatePremiumDateAfterDeliveryDate() {
        VanillaOptionTransactionDTO vanillaOptionTransaction = new VanillaOptionTransactionDTO();
        vanillaOptionTransaction.setPremiumDate("2021-11-12");
        vanillaOptionTransaction.setDeliveryDate("2021-11-11");
        ValidationContext<TransactionDTO> validationContext = new ValidationContext<>(vanillaOptionTransaction, 6L);

        Optional<ValidationErrorDTO> validationError = premiumDateBeforeDeliveryDateValidationRule.validate(validationContext);

        assertThat(validationError).isNotEmpty();
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("transactionNumber", 6L);
        assertThat(validationError.get().getAffectedFields()).containsExactly("premiumDate", "deliveryDate");
        assertThat(validationError.get()).hasFieldOrPropertyWithValue("message",
                "Premium date: 2021-11-12, shall be before delivery date: 2021-11-11");
    }
}
