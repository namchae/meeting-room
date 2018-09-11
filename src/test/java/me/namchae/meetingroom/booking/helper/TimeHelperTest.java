package me.namchae.meetingroom.reservation.helper;

import me.namchae.meetingroom.reservation.exception.NotValidThirtyMinutes;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeHelperTest {

    private List<LocalTime> times;

    @Before
    public void setUp() {
        times = new ArrayList<>();
        times.add(LocalTime.of(11,30));
        times.add(LocalTime.of(12,00));
    }

    @Test
    public void 시간테스트_30분단위_테스트_정상() {
        times.stream().forEach(TimeHelper::validThirtyMinute);
    }

    @Test(expected = NotValidThirtyMinutes.class)
    public void 시간테스트_30분단위_테스트_비정상() {
        times.add(LocalTime.of(11,31));
        times.stream().forEach(TimeHelper::validThirtyMinute);
    }

    @Test
    public void 타임리스트() {
        LocalTime startTime = LocalTime.of(11,30);
        LocalTime endTime = LocalTime.of(18,00);
        List<LocalTime> times = TimeHelper.getRangeTimes(startTime, endTime);
        System.out.println(times);

    }

}