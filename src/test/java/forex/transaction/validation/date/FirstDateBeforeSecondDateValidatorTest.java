package forex.transaction.validation.date;

import forex.transaction.validation.date.FirstDateBeforeSecondDateConstraint;
import forex.transaction.validation.date.FirstDateBeforeSecondDateValidator;
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
        when(firstDateYYYYMMDDBeforeSecondDateYYYYMMDDConstraint.isApplicableBooleanProperty()).thenReturn("applicable");
        firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.initialize(firstDateYYYYMMDDBeforeSecondDateYYYYMMDDConstraint);

        firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator = new FirstDateBeforeSecondDateValidator();
        FirstDateBeforeSecondDateConstraint firstDateDDMMYYYYBeforeSecondDateYYYYMMDDConstraint = mock(FirstDateBeforeSecondDateConstraint.class);
        when(firstDateDDMMYYYYBeforeSecondDateYYYYMMDDConstraint.firstDateStringProperty()).thenReturn("firstDateString");
        when(firstDateDDMMYYYYBeforeSecondDateYYYYMMDDConstraint.firstDatePropertyFormat()).thenReturn("dd-MM-yyyy");
        when(firstDateDDMMYYYYBeforeSecondDateYYYYMMDDConstraint.secondDateStringProperty()).thenReturn("secondDateString");
        when(firstDateDDMMYYYYBeforeSecondDateYYYYMMDDConstraint.secondDatePropertyFormat()).thenReturn("yyyy-MM-dd");
        when(firstDateDDMMYYYYBeforeSecondDateYYYYMMDDConstraint.isApplicableBooleanProperty()).thenReturn("applicable");
        firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.initialize(firstDateDDMMYYYYBeforeSecondDateYYYYMMDDConstraint);
    }

    @Test
    void isValidFirstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateNullSecondDateNull() {
        TwoDatesClass twoDatesClass = new TwoDatesClass(null, null, true);
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateNonNullSecondDateNull() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("2012-12-12", null, true);
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateNullSecondDateNonNull() {
        TwoDatesClass twoDatesClass = new TwoDatesClass(null, "2021-12-12", true);
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateInvalidSecondDateValid() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("12-12-2021", "2021-12-12", true);
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateValidNullSecondDateInvalid() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("2012-12-12", "12-12-2021", true);
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateBeforeSecondDate() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("2021-12-11", "2021-12-12", true);
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateEqualSecondDate() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("2021-12-12", "2021-12-12", true);
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isFalse();
    }

    @Test
    void isValidFirstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateAfterSecondDate() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("2021-12-13", "2021-12-12", true);
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isFalse();
    }

    @Test
    void isValidFirstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidatorFirstDateAfterSecondDateApplicableFalse() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("2021-12-13", "2021-12-12", false);
        boolean valid = firstDateYYYYMMDDBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateNullSecondDateNull() {
        TwoDatesClass twoDatesClass = new TwoDatesClass(null, null, true);
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateNonNullSecondDateNull() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("12-12-2021", null, true);
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateNullSecondDateNonNull() {
        TwoDatesClass twoDatesClass = new TwoDatesClass(null, "2021-12-12", true);
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateInvalidlSecondDateValid() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("2021-12-12", "12-12-2021", true);
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateValidlSecondDateInvalid() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("12-12-2021", "12-12-2021", true);
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateBeforeSecondDate() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("11-12-2021", "2021-12-12", true);
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateEqualSecondDate() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("12-12-2021", "2021-12-12", true);
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isFalse();
    }

    @Test
    void isValidFirstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateEqualSecondDateApplicableFalse() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("12-12-2021", "2021-12-12", false);
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidFirstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidatorFirstDateAfterSecondDate() {
        TwoDatesClass twoDatesClass = new TwoDatesClass("13-12-2021", "2021-12-12", true);
        boolean valid = firstDateDDMMYYYYBeforeSecondDateYYYYMMDDValidator.isValid(twoDatesClass, null);

        assertThat(valid).isFalse();
    }

    private static class TwoDatesClass {
        private final String firstDateString;
        private final String secondDateString;
        private final boolean applicable;

        public TwoDatesClass(String firstDateString, String secondDateString, boolean applicable) {
            this.firstDateString = firstDateString;
            this.secondDateString = secondDateString;
            this.applicable = applicable;
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

        // used by bean wrapper for getting property
        // must be get because is does not work with BeanWrapperImpl
        @SuppressWarnings("unused")
        public Boolean getApplicable() {
            return applicable;
        }
    }
}

