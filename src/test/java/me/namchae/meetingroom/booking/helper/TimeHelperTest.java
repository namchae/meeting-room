package me.namchae.meetingroom.booking.helper;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import java.time.LocalTime;
import java.util.List;

public class TimeHelperTest {


    @Test
    public void 시간범위_30분단위_리스트_생성_테스트() {
        // given
        LocalTime startTime = LocalTime.of(12,0);
        LocalTime endTime = LocalTime.of(13,0);

        // when
        List<LocalTime> times = TimeHelper.getRangeTimeList(startTime, endTime);

        // then
        assertThat(times.size(), is(2));
        assertThat(times.get(0), is(LocalTime.of(12,0)));
        assertThat(times.get(1), is(LocalTime.of(12,30)));
    }

}