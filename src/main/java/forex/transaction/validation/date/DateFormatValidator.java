package forex.transaction.validation.date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatValidator implements ConstraintValidator<DateFormatConstraint, String> {
    private String format;

    @Override
    public void initialize(DateFormatConstraint constraintAnnotation) {
        format = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(value);
            return true;
        } catch (ParseException exception) {
            return false;
        }
    }
}
