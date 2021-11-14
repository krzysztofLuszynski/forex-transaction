package forex.transaction.validation.date;

import org.apache.commons.lang3.StringUtils;
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
    private String firstDateStringProperty;
    private String firstDatePropertyFormat;
    private String secondDateStringProperty;
    private String secondDatePropertyFormat;
    private String isApplicableBooleanProperty;

    public void initialize(FirstDateBeforeSecondDateConstraint constraintAnnotation) {
        firstDateStringProperty = constraintAnnotation.firstDateStringProperty();
        firstDatePropertyFormat = constraintAnnotation.firstDatePropertyFormat();
        secondDateStringProperty = constraintAnnotation.secondDateStringProperty();
        secondDatePropertyFormat = constraintAnnotation.secondDatePropertyFormat();
        isApplicableBooleanProperty = constraintAnnotation.isApplicableBooleanProperty();
    }

    public boolean isValid(Object object, ConstraintValidatorContext context) {
        if (StringUtils.isNotBlank(isApplicableBooleanProperty)) {
            Boolean isApplicable = (Boolean) new BeanWrapperImpl(object).getPropertyValue(isApplicableBooleanProperty);
            if (Boolean.FALSE.equals(isApplicable)) {
                return true;
            }
        }

        Optional<LocalDate> firstLocalDate = getLocalDateValue(object, firstDateStringProperty, firstDatePropertyFormat);
        Optional<LocalDate> secondLocalDate = getLocalDateValue(object, secondDateStringProperty, secondDatePropertyFormat);

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
