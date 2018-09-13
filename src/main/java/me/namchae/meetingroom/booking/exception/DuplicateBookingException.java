package me.namchae.meetingroom.booking.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class DuplicateBookingException extends RuntimeException {

    public DuplicateBookingException(String roomType, LocalDateTime useDateTime) {
        super(String.format("[%s]의 해당시간[%s]에는 이미 예약이 있습니다.", roomType, useDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

}
