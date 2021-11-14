package forex.transaction.validation.date;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Should be used along with DateFormatConstraint - does not checks valid format of date
 */
@Documented
@Constraint(validatedBy = FirstDateBeforeSecondDateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FirstDateBeforeSecondDateConstraint {
    String firstDateStringProperty();

    String firstDatePropertyFormat() default "yyyy-MM-dd";

    String secondDateStringProperty();

    String secondDatePropertyFormat() default "yyyy-MM-dd";

    String isApplicableBooleanProperty() default "";

    String message() default "First date must be before second date";

    // needed for validation framework
    @SuppressWarnings("unused")
    Class<?>[] groups() default {};

    // needed for validation framework
    @SuppressWarnings("unused")
    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FirstDateBeforeSecondDateConstraint[] value();
    }
}
