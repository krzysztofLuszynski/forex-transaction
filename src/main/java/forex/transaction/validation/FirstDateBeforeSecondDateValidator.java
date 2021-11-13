package forex.transaction.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class FirstDateBeforeSecondDateValidator implements ConstraintValidator<FirstDateBeforeSecondDateConstraint, Object> {
    private String firstDateString;
    private String firstDateFormat;
    private String secondDateString;
    private String secondDateFormat;

    public void initialize(FirstDateBeforeSecondDateConstraint constraintAnnotation) {
        firstDateString = constraintAnnotation.firstDateStringProperty();
        firstDateFormat = constraintAnnotation.firstDatePropertyFormat();
        secondDateString = constraintAnnotation.secondDateStringProperty();
        secondDateFormat = constraintAnnotation.secondDatePropertyFormat();
    }

    public boolean isValid(Object object, ConstraintValidatorContext context) {

        Optional<LocalDate> firstLocalDate = getLocalDateValue(object, firstDateString, firstDateFormat);
        Optional<LocalDate> secondLocalDate = getLocalDateValue(object, secondDateString, secondDateFormat);

        if (firstLocalDate.isEmpty() || secondLocalDate.isEmpty()) {
            // invalid formats should be handled by different validators
            return true;
        } else {
            return firstLocalDate.get().isBefore(secondLocalDate.get());
        }
    }

    private Optional<LocalDate> getLocalDateValue(Object object, String datePropertyName, String dateFormatString) {
        String firstDateStringValue = (String) new BeanWrapperImpl(object).getPropertyValue(datePropertyName);

        if (firstDateStringValue == null) {
            // null property value should be handled by different validators
            return Optional.empty();
        }

        DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
        dateFormat.setLenient(false);
        try {
            Date date = dateFormat.parse(firstDateStringValue);
            LocalDate localDate = getLocalDateFromDate(date);
            return Optional.of(localDate);
        } catch (ParseException exception) {
            return Optional.empty();
        }
    }

    private LocalDate getLocalDateFromDate(Date date) {
        // system default zone does not have impact, cause we are comparing these dates only
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
