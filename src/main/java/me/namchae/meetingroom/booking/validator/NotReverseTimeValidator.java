package me.namchae.meetingroom.booking.validator;

import me.namchae.meetingroom.booking.endpoint.BookingTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NotReverseTimeValidator implements ConstraintValidator<NotReverseTime, BookingTime> {


    @Override
    public void initialize(NotReverseTime notReverseTime) {
    }

    @Override
    public boolean isValid(BookingTime bookingTime, ConstraintValidatorContext cxt) {
        return bookingTime.getEndTime().isAfter(bookingTime.getStartTime());
    }
}