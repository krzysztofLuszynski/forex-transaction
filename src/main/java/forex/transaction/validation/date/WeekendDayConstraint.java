package forex.transaction.validation.date;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Should be used along with DateFormatConstraint - does not checks valid format of date
 */
@Documented
@Constraint(validatedBy = WeekendDayValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface WeekendDayConstraint {
    String value() default "yyyy-MM-dd";

    String message() default "Invalid date format";

    // needed for validation framework
    @SuppressWarnings("unused")
    Class<?>[] groups() default {};

    // needed for validation framework
    @SuppressWarnings("unused")
    Class<? extends Payload>[] payload() default {};
}
