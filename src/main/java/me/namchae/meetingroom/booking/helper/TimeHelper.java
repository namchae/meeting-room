package me.namchae.meetingroom.booking.helper;

import com.google.common.collect.Lists;

import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class TimeHelper {

    private static final long LIMIT_MINUTE = 30;

    // 종료시간이 시작시간보다 이전일 경우 확인
//    public static boolean validTimeRange(LocalTime startTime, LocalTime endTime) {
//        validThirtyMinute(startTime, endTime);
//
//        if (startTime.isAfter(endTime)) {
//            throw new InValidRangeException(startTime, endTime);
//        }
//        return true;
//    }

    // 30분단위 요청 확인
//    public static boolean validThirtyMinute(LocalTime... times) {
//
//        for (LocalTime time : times) {
//            if (time.getMinute() % LIMIT_MINUTE != 0) {
//                throw new InValidThirtyMinutesException(time);
//            }
//        }
//        return true;
//    }

    //시작 - 종료시간 30분 단위 List 생성
    public static List<LocalTime> getRangeTimeList(LocalTime startTime, LocalTime endTime) {
        List<LocalTime> times = Lists.newArrayList();
        while (startTime.isBefore(endTime)) {
            times.add(startTime);
            startTime = startTime.plusMinutes(LIMIT_MINUTE);
        }
        return times;
    }

    // 리스트 요청 데이터
    public static Pair<LocalDateTime, LocalDateTime> getBetweenTimes(LocalDate useDate) {
        LocalTime localTime = LocalTime.of(0,0);
        return Pair.of(LocalDateTime.of(useDate, localTime), LocalDateTime.of(useDate.plusDays(1), localTime));
    }
}
