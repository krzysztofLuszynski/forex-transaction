package forex.transaction.config;

import forex.transaction.validation.TransactionValidator;
import forex.transaction.validation.ValidationRule;
import forex.transaction.validation.rule.general.SupportedCustomerValidationRule;
import forex.transaction.validation.rule.general.MandatoryValueDateValidationRule;
import forex.transaction.validation.rule.general.SupportedLegalEntityValidationRule;
import forex.transaction.validation.rule.vanillaoption.ExpiryDateBeforeDeliveryDateValidationRule;
import forex.transaction.validation.rule.vanillaoption.PremiumDateBeforeDeliveryDateValidationRule;
import forex.transaction.validation.rule.vanillaoption.SupportedStyleValidationRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class ValidationConfiguration {
    private static final ValidationRule SUPPORTED_CUSTOMER_VALIDATION_RULE
            = new SupportedCustomerValidationRule(List.of("YODA1", "YODA2"));
    private static final ValidationRule SUPPORTED_LEGAL_ENTITY_VALIDATION_RULE
            = new SupportedLegalEntityValidationRule(List.of("UBS AG"));

    private static final List<ValidationRule> GENERAL_VALIDATION_RULES =
            List.of(
                    SUPPORTED_CUSTOMER_VALIDATION_RULE,
                    SUPPORTED_LEGAL_ENTITY_VALIDATION_RULE);


    @Bean
    @Qualifier("spotTransactionValidator")
    TransactionValidator getSpotTransactionValidator() {
        List<ValidationRule> validationRules = new ArrayList<>(GENERAL_VALIDATION_RULES);
        validationRules.addAll(
                List.of(new MandatoryValueDateValidationRule())
        );

        return new TransactionValidator(Collections.unmodifiableList(validationRules));
    }

    @Bean
    @Qualifier("forwardTransactionValidator")
    TransactionValidator getForwardTransactionValidator() {
        List<ValidationRule> validationRules = new ArrayList<>(GENERAL_VALIDATION_RULES);
        validationRules.addAll(
                List.of(new MandatoryValueDateValidationRule())
        );

        return new TransactionValidator(Collections.unmodifiableList(validationRules));
    }

    @Bean
    @Qualifier("vanillaOptionTransactionValidator")
    TransactionValidator getVanillaOptionTransactionValidator() {
        List<ValidationRule> validationRules = new ArrayList<>(GENERAL_VALIDATION_RULES);

        validationRules.addAll(
                List.of(new ExpiryDateBeforeDeliveryDateValidationRule(),
                        new PremiumDateBeforeDeliveryDateValidationRule(),
                        new SupportedStyleValidationRule(List.of("EUROPEAN", "AMERICAN")))
        );

        return new TransactionValidator(Collections.unmodifiableList(validationRules));
    }
}
