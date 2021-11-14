package forex.transaction.validation.currency;

import java.util.Currency;

class CurrencyValidatorUtil {
    boolean isValidISO4217Currency(String currency) {
        try {
            Currency.getInstance(currency);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
