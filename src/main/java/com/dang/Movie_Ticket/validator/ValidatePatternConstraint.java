package com.dang.Movie_Ticket.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidatePatternConstraint implements ConstraintValidator<ValidatePattern, String> {
    private String regexp;
    @Override
    public void initialize(ValidatePattern constraintAnnotation) {
        regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null){
            return true;
        }
        return Pattern.matches(regexp, value);
    }
}
