package me.namchae.meetingroom.booking.exception;

import java.time.LocalTime;

public class NotValidRangeException extends RuntimeException {

    public NotValidRangeException(LocalTime startTime, LocalTime endTime) {
        super(String.format("시작시간[%s]이 종료시간[%s]보다 앞서 있습니다.", startTime, endTime));
    }
}
