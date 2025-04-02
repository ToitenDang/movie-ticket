package com.dang.Movie_Ticket.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DobValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DobConstraint {
    String message() default "Invalid date of birth";
    int min(); // Truyền giá trị nhỏ nhất cho tuổi
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
