package me.namchae.meetingroom.booking.endpoint;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingDtoTest {

    private BookingDto.CreateReq createReq;




    @Before
    public void setUp() {
        createReq = BookingDto.CreateReq.builder()
                .roomType("회의실B")
                .booker("예약자")
                .useDate(LocalDate.now())
                .bookingTime(BookingTime.builder().startTime(LocalTime.of(11, 0))
                        .endTime(LocalTime.of(12, 0))
                        .build())
                .repetitionCount(3)
                .build();
    }

    @Test
    public void DTO_테스트() {
//        BookingDto.CreateReq tmpObj = BookingDto.CreateReq.builder()
//                .roomType(null)
//                .booker("예약자")
//                .useDate(LocalDate.now())
//                .startTime(LocalTime.of(11, 0))
//                .endTime(LocalTime.of(12, 0))
//                .repetitionCount(3)
//                .build();
//        validator.validateProperty(tmpObj, "roomType");
    }

    @Test
    public void 반복주기_테스트() {
        System.out.println(
        createReq.toString());

        System.out.println(createReq.repetitionDate());
    }

}