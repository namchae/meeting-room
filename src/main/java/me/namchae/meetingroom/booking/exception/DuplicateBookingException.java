package me.namchae.meetingroom.booking.exception;

import lombok.Getter;

@Getter
public class DuplicateBookingException extends RuntimeException {
    public DuplicateBookingException(String message) {
        super(message);
    }
}
