package forex.transaction.config;

import forex.transaction.validation.TransactionValidator;
import forex.transaction.validation.ValidationRule;
import forex.transaction.validation.rule.general.SupportedCustomerValidationRule;
import forex.transaction.validation.rule.general.MandatoryValueDateValidationRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class ValidationConfiguration {
    private static final String SPOT_TRANSACTION_TYPE_LABEL = "spot";
    private static final String FORWARD_TRANSACTION_TYPE_LABEL = "forward";

    private static final ValidationRule SUPPORTED_CUSTOMER_VALIDATION_RULE =
            new SupportedCustomerValidationRule(List.of("YODA1", "YODA2"));

    private static final List<ValidationRule> GENERAL_VALIDATION_RULES =
            List.of(SUPPORTED_CUSTOMER_VALIDATION_RULE);


    @Bean
    @Qualifier("spotTransactionValidator")
    TransactionValidator getSpotTransactionValidator() {
        MandatoryValueDateValidationRule mandatoryValueDateValidationRule = new MandatoryValueDateValidationRule(
                SPOT_TRANSACTION_TYPE_LABEL);

        List<ValidationRule> validationRules = new ArrayList<>(GENERAL_VALIDATION_RULES);
        validationRules.addAll(
                List.of(mandatoryValueDateValidationRule)
        );

        return new TransactionValidator(Collections.unmodifiableList(validationRules));
    }

    @Bean
    @Qualifier("forwardTransactionValidator")
    TransactionValidator getForwardTransactionValidator() {
        MandatoryValueDateValidationRule mandatoryValueDateValidationRule = new MandatoryValueDateValidationRule(
                FORWARD_TRANSACTION_TYPE_LABEL);

        List<ValidationRule> validationRules = new ArrayList<>(GENERAL_VALIDATION_RULES);
        validationRules.addAll(
                List.of(mandatoryValueDateValidationRule)
        );

        return new TransactionValidator(Collections.unmodifiableList(validationRules));
    }
}
