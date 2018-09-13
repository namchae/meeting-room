package me.namchae.meetingroom.booking;

import me.namchae.meetingroom.booking.domain.Booking;
import me.namchae.meetingroom.booking.endpoint.BookingDto;
import me.namchae.meetingroom.booking.domain.BookingTimeLine;
import me.namchae.meetingroom.booking.endpoint.BookingTime;
import me.namchae.meetingroom.booking.exception.DuplicateBookingException;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    private BookingDto.CreateReq createReq1;


    private BookingDto.CreateReq createReq2;

    @BeforeEach
    public void setUp() {
        // given
        createReq1 = BookingDto.CreateReq.builder()
                .roomType("회의실F")
                .booker("예약자")
                .useDate(LocalDate.now().plusDays(3))
                .bookingTime(
                        BookingTime.builder()
                                .startTime(LocalTime.of(11, 0))
                                .endTime(LocalTime.of(12, 0))
                                .build()
                )
                .repetitionCount(2)
                .build();

        createReq2 = BookingDto.CreateReq.builder()
                .roomType("회의실F")
                .booker("테스터")
                .useDate(LocalDate.now().plusDays(10))
                .bookingTime(
                        BookingTime.builder()
                                .startTime(LocalTime.of(11, 0))
                                .endTime(LocalTime.of(12, 0))
                                .build()
                )
                .repetitionCount(1)
                .build();
    }

    @Test
    public void 예약_중첩요청_실패() {
        // given (before)
        // 3일뒤 반복주기 2회.
        bookingService.execute(createReq1);

        // when then
        // 10일뒤 반복주기 1회.
        assertThrows(DuplicateBookingException.class, () ->{
            bookingService.execute(createReq2);
        });

    }

    @Test
    public void 예약완료_테스트() {
        // given (before)

        // when
        Booking executeBooking = bookingService.execute(createReq1);
        List<BookingTimeLine> timeLines = executeBooking.getBookingTimeLines();
        List<LocalDate> foundLocalDates = timeLines.stream()
                .map(BookingTimeLine::getUseDateTime)
                .map(LocalDateTime::toLocalDate)
                .distinct()
                .collect(Collectors.toList());

        List<LocalTime> foundLocalTimes = timeLines.stream()
                .map(BookingTimeLine::getUseDateTime)
                .map(LocalDateTime::toLocalTime)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        BookingTimeLine firstTimeLine = timeLines.get(0);
        BookingTimeLine finalTimeLine = timeLines.get(timeLines.size() - 1);
        // then
        assertThat(executeBooking.getBooker(), IsEqual.equalTo(createReq1.getBooker()));
        assertThat(foundLocalDates, IsEqual.equalTo(createReq1.repetitionDate()));
        assertThat(foundLocalTimes.get(0), IsEqual.equalTo(createReq1.getBookingTime().getStartTime()));
        assertThat(foundLocalTimes.get(1), IsEqual.equalTo(createReq1.getBookingTime().getEndTime().minusMinutes(30)));
        assertThat(executeBooking.getRepetitionCount(), is(2));
        assertThat(firstTimeLine.getUseDateTime(), is(LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(11,0))));
        assertThat(firstTimeLine.getRoomType(), is(createReq1.getRoomType()));
        assertThat(finalTimeLine.getUseDateTime(), is(LocalDateTime.of(LocalDate.now().plusDays(10), LocalTime.of(11,30))));
        assertThat(executeBooking.getBookingTimeLines().size(), is(4));
    }

    @Test
    public void 당일예약건_리스트_호출() {
        // given
        Booking executeBooking = bookingService.execute(createReq1);
        LocalDate requestDate = LocalDate.now().plusDays(3);

        // when
        List<Booking> bookings = bookingService.list(requestDate);

        // then
        List<BookingDto.Response> responses = bookings.stream().map(BookingDto.Response::new).collect(Collectors.toList());
        BookingDto.Response expected = BookingDto.Response.builder().booking(executeBooking).build();

        assertThat(expected.getBooker(), IsEqual.equalTo(responses.get(0).getBooker()));
        assertThat(expected.getRoomType(), IsEqual.equalTo(responses.get(0).getRoomType()));
        assertThat(expected.getBookingTime().getStartTime(), IsEqual.equalTo(responses.get(0).getBookingTime().getStartTime()));
        assertThat(expected.getBookingTime().getEndTime(), IsEqual.equalTo(responses.get(0).getBookingTime().getEndTime()));
        assertThat(expected.getRepetitionCount(), IsEqual.equalTo(responses.get(0).getRepetitionCount()));
    }

}