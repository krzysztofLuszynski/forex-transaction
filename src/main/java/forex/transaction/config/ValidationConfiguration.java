package forex.transaction.config;

import forex.transaction.validation.TransactionValidator;
import forex.transaction.validation.rule.general.SupportedCustomerValidationRule;
import forex.transaction.validation.rule.spot.MandatoryValueDateValidationRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ValidationConfiguration {
    @Bean
    @Qualifier("spotTransactionValidator")
    TransactionValidator getSpotTransactionValidator() {
        SupportedCustomerValidationRule supportedCustomerValidationRule = new SupportedCustomerValidationRule(
                List.of("YODA1", "YODA2"));
        MandatoryValueDateValidationRule mandatoryValueDateValidationRule = new MandatoryValueDateValidationRule();

        return new TransactionValidator(List.of(
                supportedCustomerValidationRule,
                mandatoryValueDateValidationRule
        ));
    }
}
