package forex.transaction.validation.currency;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyPairValidator implements ConstraintValidator<CurrencyPairConstraint, String> {
    private final CurrencyValidatorUtil currencyValidatorUtil = new CurrencyValidatorUtil();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (StringUtils.isBlank(value) || value.length() != 6) {
            return false;
        }

        String currency1 = value.substring(0,3);
        String currency2 = value.substring(3, 6);

        return currencyValidatorUtil.isValidISO4217Currency(currency1) &&
                currencyValidatorUtil.isValidISO4217Currency(currency2);
    }


}
