package forex.transaction.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrencyValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyConstraint {
    String message() default "Invalid ISO-4217 currency";

    // needed for validation framework
    @SuppressWarnings("unused")
    Class<?>[] groups() default {};

    // needed for validation framework
    @SuppressWarnings("unused")
    Class<? extends Payload>[] payload() default {};
}
