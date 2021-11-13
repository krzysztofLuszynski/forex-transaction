package forex.transaction.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DateFormatValidatorTest {
    private DateFormatValidator dateFormatValidatorYYYYMMDD;
    private DateFormatValidator dateFormatValidatorMMDDYYYY;

    @BeforeEach
    void setUpValidators() {
        dateFormatValidatorYYYYMMDD = new DateFormatValidator();
        DateFormatConstraint dateFormatYYYYMMDDConstraintMock = mock(DateFormatConstraint.class);
        when(dateFormatYYYYMMDDConstraintMock.value()).thenReturn("yyyy-MM-dd");
        dateFormatValidatorYYYYMMDD.initialize(dateFormatYYYYMMDDConstraintMock);

        dateFormatValidatorMMDDYYYY = new DateFormatValidator();
        DateFormatConstraint dateFormatMMDDYYYYConstraintMock = mock(DateFormatConstraint.class);
        when(dateFormatMMDDYYYYConstraintMock.value()).thenReturn("dd-MM-yyyy");
        dateFormatValidatorMMDDYYYY.initialize(dateFormatMMDDYYYYConstraintMock);
    }

    @Test
    void testYYYYMMDDValidatorNullDate() {
        boolean valid = dateFormatValidatorYYYYMMDD.isValid(null, null);

        assertThat(valid).isTrue();
    }

    @Test
    void testYYYYMMDDValidatorBlankDate() {
        boolean valid = dateFormatValidatorYYYYMMDD.isValid("", null);

        assertThat(valid).isFalse();
    }

    @Test
    void testYYYYMMDDValidatorInvalidDate() {
        boolean valid = dateFormatValidatorYYYYMMDD.isValid("2022-ee-12", null);

        assertThat(valid).isFalse();
    }

    @Test
    void testYYYYMMDDValidatorValidDate() {
        boolean valid = dateFormatValidatorYYYYMMDD.isValid("2022-12-12", null);

        assertThat(valid).isTrue();
    }

    @Test
    void testMMDDYYYYValidatorNullDate() {
        boolean valid = dateFormatValidatorMMDDYYYY.isValid(null, null);

        assertThat(valid).isTrue();
    }

    @Test
    void testMMDDYYYYValidatorBlankDate() {
        boolean valid = dateFormatValidatorMMDDYYYY.isValid("", null);

        assertThat(valid).isFalse();
    }

    @Test
    void testMMDDYYYYValidatorInvalidDate() {
        boolean valid = dateFormatValidatorMMDDYYYY.isValid("2022-12-12", null);

        assertThat(valid).isFalse();
    }

    @Test
    void testMMDDYYYYValidatorValidDate() {
        boolean valid = dateFormatValidatorMMDDYYYY.isValid("12-12-2022", null);

        assertThat(valid).isTrue();
    }
}
