package forex.transaction.validation.currency;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrencyPairValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyPairConstraint {
    String message() default "Invalid ISO-4217 currency in currency pair";

    // needed for validation framework
    @SuppressWarnings("unused")
    Class<?>[] groups() default {};

    // needed for validation framework
    @SuppressWarnings("unused")
    Class<? extends Payload>[] payload() default {};
}
