package me.namchae.meetingroom.reservation.helper;

import me.namchae.meetingroom.reservation.exception.NotValidThirtyMinutes;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeHelper {

    private static final long LIMIT_MINUTE = 30;

    public static boolean validThirtyMinute(LocalTime ... times) {

        for (LocalTime time : times) {
            if (time.getMinute() % LIMIT_MINUTE != 0) {
                throw new NotValidThirtyMinutes();
            }
        }
        return true;
    }



    public static List<LocalTime> getRangeTimes(LocalTime startTime, LocalTime endTime) {
        List<LocalTime> times = new ArrayList<>();
        while(!startTime.equals(endTime)) {
            times.add(startTime);
            startTime = startTime.plusMinutes(LIMIT_MINUTE);
        }
        times.add(endTime);
        return times;
    }

//    Predicate<LocalTime> filterThirtyMinutes(LocalTime time) {
//        return time -> time.getMinute() % LIMIT_MINUTE != 0;
//
//        if (time.getMinute() % LIMIT_MINUTE != 0) {
//            throw new NotValidThirtyMinutes();
//        }
//        return true;
//    }

}
