package forex.transaction.validation.date;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeekendDayValidator implements ConstraintValidator<WeekendDayConstraint, String>  {
    private String format;

    @Override
    public void initialize(WeekendDayConstraint constraintAnnotation) {
        format = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }

        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            Date date = dateFormat.parse(value);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int dayField = calendar.get(Calendar.DAY_OF_WEEK);
            return dayField != Calendar.SATURDAY && dayField != Calendar.SUNDAY;
        } catch (ParseException exception) {
            return true;
        }
    }
}
