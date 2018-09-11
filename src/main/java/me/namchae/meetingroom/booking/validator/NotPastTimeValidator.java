package me.namchae.meetingroom.booking.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class NotPastTimeValidator implements ConstraintValidator<NotPastTime, LocalDateTime> {


    @Override
    public void initialize(NotPastTime notReverseTime) {
    }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext cxt) {
        return localDateTime.isAfter(LocalDateTime.now());
    }
}