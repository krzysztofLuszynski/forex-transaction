package forex.transaction.validation.currency;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyValidatorTest {
    private final CurrencyValidator currencyValidator = new CurrencyValidator();

    @Test
    void isValidNullValue() {
        boolean valid = currencyValidator.isValid(null, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidBlankValue() {
        boolean valid = currencyValidator.isValid("", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidNonIsoCurrencyValue() {
        boolean valid = currencyValidator.isValid("AAA", null);

        assertThat(valid).isFalse();
    }

    @Test
    void isValidEURCurrencyValue() {
        boolean valid = currencyValidator.isValid("EUR", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidAEDCurrencyValue() {
        boolean valid = currencyValidator.isValid("AED", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidCDFCurrencyValue() {
        boolean valid = currencyValidator.isValid("CDF", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidCHFCurrencyValue() {
        boolean valid = currencyValidator.isValid("CHF", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidUSDCurrencyValue() {
        boolean valid = currencyValidator.isValid("CHF", null);

        assertThat(valid).isTrue();
    }
}
