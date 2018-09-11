package me.namchae.meetingroom.booking.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class ThirtyMinuteValidator implements ConstraintValidator<ThirtyMinute, LocalTime> {

    private static final long LIMIT_MINUTE = 30;

    @Override
    public void initialize(ThirtyMinute thirtyMinute) {
    }
 
    @Override
    public boolean isValid(LocalTime localTime, ConstraintValidatorContext cxt) {
        return (localTime.getMinute() % LIMIT_MINUTE) == 0;
    }
}