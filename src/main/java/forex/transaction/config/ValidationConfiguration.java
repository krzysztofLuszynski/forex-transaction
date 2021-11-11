package forex.transaction.config;

import forex.transaction.validation.TransactionValidator;
import forex.transaction.validation.rule.general.MandatoryValueDateValidationRule;
import forex.transaction.validation.rule.vanillaoption.ExpiryDateBeforeDeliveryDateValidationRule;
import forex.transaction.validation.rule.vanillaoption.PremiumDateBeforeDeliveryDateValidationRule;
import forex.transaction.validation.rule.vanillaoption.SupportedStyleValidationRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ValidationConfiguration {
    @Bean
    @Qualifier("spotTransactionValidator")
    TransactionValidator getSpotTransactionValidator() {
        return new TransactionValidator(List.of(new MandatoryValueDateValidationRule()));
    }

    @Bean
    @Qualifier("forwardTransactionValidator")
    TransactionValidator getForwardTransactionValidator() {
        return new TransactionValidator(List.of(new MandatoryValueDateValidationRule()));
    }

    @Bean
    @Qualifier("vanillaOptionTransactionValidator")
    TransactionValidator getVanillaOptionTransactionValidator() {
        return new TransactionValidator(List.of(new ExpiryDateBeforeDeliveryDateValidationRule(),
                new PremiumDateBeforeDeliveryDateValidationRule(),
                new SupportedStyleValidationRule(List.of("EUROPEAN", "AMERICAN"))));
    }
}
