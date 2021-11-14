package forex.transaction.validation.currency;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyPairValidatorTest {
    private final CurrencyPairValidator currencyPairValidator = new CurrencyPairValidator();

    @Test
    void isValidNullValue() {
        boolean valid = currencyPairValidator.isValid(null, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidBlankValue() {
        boolean valid = currencyPairValidator.isValid("", null);

        assertThat(valid).isFalse();
    }

    @Test
    void isValidLessThan6CharactersIsoCurrencyValue() {
        boolean valid = currencyPairValidator.isValid("AAAAA", null);

        assertThat(valid).isFalse();
    }

    @Test
    void isValidMoreThan6CharactersIsoCurrencyValue() {
        boolean valid = currencyPairValidator.isValid("AAAAAAA", null);

        assertThat(valid).isFalse();
    }

    @Test
    void isValidAAAEURCurrencyValue() {
        boolean valid = currencyPairValidator.isValid("AAAEUR", null);

        assertThat(valid).isFalse();
    }

    @Test
    void isValidEURAAACurrencyValue() {
        boolean valid = currencyPairValidator.isValid("EURAAA", null);

        assertThat(valid).isFalse();
    }

    @Test
    void isValidUSDUSDCurrencyValue() {
        boolean valid = currencyPairValidator.isValid("USDUSD", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidUSDEURCurrencyValue() {
        boolean valid = currencyPairValidator.isValid("USDEUR", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidCHFJPYCurrencyValue() {
        boolean valid = currencyPairValidator.isValid("CHFJPY", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidAEDCDFCurrencyValue() {
        boolean valid = currencyPairValidator.isValid("AEDCDF", null);

        assertThat(valid).isTrue();
    }

}
