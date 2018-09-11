package me.namchae.meetingroom.booking.helper;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
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
    public void 시간_30분단위_범위_리스트() {
        LocalTime startTime = LocalTime.of(12,00);
        LocalTime endTime = LocalTime.of(13,00);
        List<LocalTime> times = TimeHelper.getRangeTimeList(startTime, endTime);
        assertThat(times.size(), is(2));
    }

}