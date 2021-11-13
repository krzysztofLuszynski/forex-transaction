package forex.transaction.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FirstDateBeforeSecondDateValidatorTest {
    private FirstDateBeforeSecondDateValidator firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator;
    private FirstDateBeforeSecondDateValidator firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator;

    @BeforeEach
    void setUpValidators() {
        firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator = new FirstDateBeforeSecondDateValidator();
        FirstDateBeforeSecondDateConstraint firstDateYYYYMMDDBeforeSecondDateYYYYMMDDConstraint = mock(FirstDateBeforeSecondDateConstraint.class);
        when(firstDateYYYYMMDDBeforeSecondDateYYYYMMDDConstraint.firstDateStringProperty()).thenReturn("firstDateString");
        when(firstDateYYYYMMDDBeforeSecondDateYYYYMMDDConstraint.firstDatePropertyFormat()).thenReturn("yyyy-MM-dd");
        when(firstDateYYYYMMDDBeforeSecondDateYYYYMMDDConstraint.secondDateStringProperty()).thenReturn("secondDateString");
        when(firstDateYYYYMMDDBeforeSecondDateYYYYMMDDConstraint.secondDatePropertyFormat()).thenReturn("yyyy-MM-dd");
        firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.initialize(firstDateYYYYMMDDBeforeSecondDateYYYYMMDDConstraint);

        firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator = new FirstDateBeforeSecondDateValidator();
        FirstDateBeforeSecondDateConstraint firstDateDDMMYYYYBeforeSecondDateYYYYMMDDConstraint = mock(FirstDateBeforeSecondDateConstraint.class);
        when(firstDateDDMMYYYYBeforeSecondDateYYYYMMDDConstraint.firstDateStringProperty()).thenReturn("firstDateString");
        when(firstDateDDMMYYYYBeforeSecondDateYYYYMMDDConstraint.firstDatePropertyFormat()).thenReturn("dd-MM-yyyy");
        when(firstDateDDMMYYYYBeforeSecondDateYYYYMMDDConstraint.secondDateStringProperty()).thenReturn("secondDateString");
        when(firstDateDDMMYYYYBeforeSecondDateYYYYMMDDConstraint.secondDatePropertyFormat()).thenReturn("yyyy-MM-dd");
        firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.initialize(firstDateDDMMYYYYBeforeSecondDateYYYYMMDDConstraint);
    }

    @Test
    void firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateNullSecondDateNull() {
        TwoDatesClass twoDatesClass = new TwoDatesClass(null, null);
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateNonNullSecondDateNull() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("2012-12-12", null);
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateNullSecondDateNonNull() {
        TwoDatesClass twoDatesClass = new TwoDatesClass(null, "2021-12-12");
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateInvalidSecondDateValid() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("12-12-2021", "2021-12-12");
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateValidNullSecondDateInvalid() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("2012-12-12", "12-12-2021");
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateBeforeSecondDate() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("2021-12-11", "2021-12-12");
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateEqualSecondDate() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("2021-12-12", "2021-12-12");
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isFalse();
    }

    @Test
    void firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateAfterSecondDate() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("2021-12-13", "2021-12-12");
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isFalse();
    }

    @Test
    void firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateNullSecondDateNull() {
        TwoDatesClass twoDatesClass = new TwoDatesClass(null, null);
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateNonNullSecondDateNull() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("12-12-2021", null);
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateNullSecondDateNonNull() {
        TwoDatesClass twoDatesClass = new TwoDatesClass(null, "2021-12-12");
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateInvalidlSecondDateValid() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("2021-12-12", "12-12-2021");
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateValidlSecondDateInvalid() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("12-12-2021", "12-12-2021");
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateBeforeSecondDate() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("11-12-2021", "2021-12-12");
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateEqualSecondDate() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("12-12-2021", "2021-12-12");
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isFalse();
    }

    @Test
    void firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateAfterSecondDate() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("13-12-2021", "2021-12-12");
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isFalse();
    }

    private static class TwoDatesClass {
        private final String firstDateString;
        private final String secondDateString;

        public TwoDatesClass(String firstDateString, String secondDateString) {
            this.firstDateString = firstDateString;
            this.secondDateString = secondDateString;
        }


        // used by bean wrapper for getting property
        @SuppressWarnings("unused")
        public String getFirstDateString() {
            return firstDateString;
        }

        // used by bean wrapper for getting property
        @SuppressWarnings("unused")
        public String getSecondDateString() {
            return secondDateString;
        }
    }
}

