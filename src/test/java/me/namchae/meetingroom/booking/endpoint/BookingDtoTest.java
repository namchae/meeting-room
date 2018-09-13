package me.namchae.meetingroom.booking.endpoint;





import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BookingDtoTest {

    private BookingDto.CreateReq createReq;

    @BeforeEach
    public void setUp() {
        createReq = BookingDto.CreateReq.builder()
                .roomType("회의실B")
                .booker("예약자")
                .useDate(LocalDate.now())
                .bookingTime(BookingTime.builder().startTime(LocalTime.of(11, 0))
                        .endTime(LocalTime.of(12, 0))
                        .build())
                .repetitionCount(2)
                .build();
    }

    @Test
    public void DTO_테스트() {
        createReq.toEntity();
    }

    @Test
    public void RequestDto_createReq_예약_반복주기_리스트_테스트() {
        // given (before)

        // when
        List<LocalDate> repetitionDates = createReq.repetitionDate();

        // then
        assertThat(repetitionDates.size(), is(2));
        assertThat(repetitionDates.get(0), is(LocalDate.now()));
        assertThat(repetitionDates.get(1), is(LocalDate.now().plusDays(7)));
    }

}