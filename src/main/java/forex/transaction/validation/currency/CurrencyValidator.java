package forex.transaction.validation.currency;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyValidator implements ConstraintValidator<CurrencyConstraint, String> {
    private final CurrencyValidatorUtil currencyValidatorUtil = new CurrencyValidatorUtil();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }

        return currencyValidatorUtil.isValidISO4217Currency(value);
    }
}
