package me.namchae.meetingroom.booking.validator;

import javax.validation.ConstraintValidatorContext;

public class ThirtyMinuteValidator implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone phone) {
    
    }
 
    @Override
    public boolean isValid(String field, ConstraintValidatorContext cxt) {
        return field != null && field.matches("[0-9]+")
                && (field.length() > 8) && (field.length() < 14);
    }
}