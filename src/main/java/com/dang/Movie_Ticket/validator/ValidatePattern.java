package com.dang.Movie_Ticket.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidatePatternConstraint.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatePattern {
    String message() default "{name} invalid format";
    String regexp(); // Truyền regex
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
