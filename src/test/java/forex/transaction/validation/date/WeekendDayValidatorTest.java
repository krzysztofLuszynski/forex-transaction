package forex.transaction.validation.date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeekendDayValidatorTest {
    private WeekendDayValidator weekendDayValidatorYYYYMMDD;
    private WeekendDayValidator weekendDayValidatorDDMMYYYY;

    @BeforeEach
    void setUpValidators() {
        weekendDayValidatorYYYYMMDD = new WeekendDayValidator();
        WeekendDayConstraint weekendDayYYYYMMDDConstraintMock = mock(WeekendDayConstraint.class);
        when(weekendDayYYYYMMDDConstraintMock.value()).thenReturn("yyyy-MM-dd");
        weekendDayValidatorYYYYMMDD.initialize(weekendDayYYYYMMDDConstraintMock);

        weekendDayValidatorDDMMYYYY = new WeekendDayValidator();
        WeekendDayConstraint weekendDayDDMMYYYYConstraintMock = mock(WeekendDayConstraint.class);
        when(weekendDayDDMMYYYYConstraintMock.value()).thenReturn("dd-MM-yyyy");
        weekendDayValidatorDDMMYYYY.initialize(weekendDayDDMMYYYYConstraintMock);
    }

    @Test
    void isValidYYYYMMDDValidatorNullDate() {
        boolean valid = weekendDayValidatorYYYYMMDD.isValid(null, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidYYYYMMDDValidatorBlankDate() {
        boolean valid = weekendDayValidatorYYYYMMDD.isValid("", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidYYYYMMDDValidatorInvalidDate() {
        boolean valid = weekendDayValidatorYYYYMMDD.isValid("2022-ee-12", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidYYYYMMDDValidatorMondayDate() {
        boolean valid = weekendDayValidatorYYYYMMDD.isValid("2021-11-15", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidYYYYMMDDValidatorTuesdayDate() {
        boolean valid = weekendDayValidatorYYYYMMDD.isValid("2021-11-16", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidYYYYMMDDValidatorWednesdayDate() {
        boolean valid = weekendDayValidatorYYYYMMDD.isValid("2021-11-17", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidYYYYMMDDValidatorThursdayDate() {
        boolean valid = weekendDayValidatorYYYYMMDD.isValid("2021-11-18", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidYYYYMMDDValidatorFridayDate() {
        boolean valid = weekendDayValidatorYYYYMMDD.isValid("2021-11-19", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidYYYYMMDDValidatorSaturdayDate() {
        boolean valid = weekendDayValidatorYYYYMMDD.isValid("2021-11-20", null);

        assertThat(valid).isFalse();
    }

    @Test
    void isValidYYYYMMDDValidatorSundayDate() {
        boolean valid = weekendDayValidatorYYYYMMDD.isValid("2021-11-21", null);

        assertThat(valid).isFalse();
    }

    @Test
    void isValidDDMMYYYYValidatorNullDate() {
        boolean valid = weekendDayValidatorDDMMYYYY.isValid(null, null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidDDMMYYYYValidatorBlankDate() {
        boolean valid = weekendDayValidatorDDMMYYYY.isValid("", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidDDMMYYYYValidatorInvalidDate() {
        boolean valid = weekendDayValidatorDDMMYYYY.isValid("12-ee-2022", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidDDMMYYYYValidatorMondayDate() {
        boolean valid = weekendDayValidatorDDMMYYYY.isValid("15-11-2021", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidDDMMYYYYValidatorTuesdayDate() {
        boolean valid = weekendDayValidatorDDMMYYYY.isValid("16-11-2021", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidDDMMYYYYValidatorWednesdayDate() {
        boolean valid = weekendDayValidatorDDMMYYYY.isValid("17-11-2021", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidDDMMYYYYValidatorThursdayDate() {
        boolean valid = weekendDayValidatorDDMMYYYY.isValid("18-11-2021", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidDDMMYYYYValidatorFridayDate() {
        boolean valid = weekendDayValidatorDDMMYYYY.isValid("19-11-2021", null);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidDDMMYYYYValidatorSaturdayDate() {
        boolean valid = weekendDayValidatorDDMMYYYY.isValid("20-11-2021", null);

        assertThat(valid).isFalse();
    }

    @Test
    void isValidDDMMYYYYValidatorSundayDate() {
        boolean valid = weekendDayValidatorDDMMYYYY.isValid("21-11-2021", null);

        assertThat(valid).isFalse();
    }
}
