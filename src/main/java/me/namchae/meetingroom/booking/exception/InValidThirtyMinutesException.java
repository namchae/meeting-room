package me.namchae.meetingroom.booking.exception;

import java.time.LocalTime;

public class InValidThirtyMinutes extends RuntimeException {
    public InValidThirtyMinutes(LocalTime time) {
        super(String.format("요청한 시간[%s]은 수행할 수 없습니다. 30분 단위로 요청해주세요.", time));
    }
}
