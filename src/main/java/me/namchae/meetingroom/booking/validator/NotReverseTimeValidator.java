package me.namchae.meetingroom.booking.validator;

import me.namchae.meetingroom.booking.endpoint.RequestTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class TimeReverseValidator implements ConstraintValidator<TimeReverse, RequestTime> {


    @Override
    public void initialize(TimeReverse timeReverse) {
    }

    @Override
    public boolean isValid(RequestTime requestTime, ConstraintValidatorContext cxt) {
        return requestTime.getEndTime().isAfter(requestTime.getStartTime());
    }
}