package me.namchae.meetingroom.booking;

import me.namchae.meetingroom.booking.domain.Booking;
import me.namchae.meetingroom.booking.endpoint.BookingDto;
import me.namchae.meetingroom.booking.domain.BookingTimeLine;
import me.namchae.meetingroom.booking.exception.DuplicateBookingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.is;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    public BookingDto.CreateReq createReq;

    @Before
    public void setUp() {
        createReq = BookingDto.CreateReq.builder()
                .roomType("회의실D")
                .booker("카카오")
                .useDate(LocalDate.now())
                .startTime(LocalTime.of(11,00))
                .endTime(LocalTime.of(12,00))
                .repetitionCount(2)
                .build();
    }

    @Test(expected = DuplicateBookingException.class)
    public void 예약_중첩요청_실패() {
        bookingService.execute(createReq);
        bookingService.execute(createReq);
    }

    @Test
    public void 예약_성공() {
        Booking booking = bookingService.execute(createReq);
        System.out.println(booking);
        System.out.println(booking.getBookingTimeLines());
        List<BookingTimeLine> timeLines = booking.getBookingTimeLines();
        BookingTimeLine firstTimeLine = timeLines.get(0);
        BookingTimeLine finalTimeLine = timeLines.get(timeLines.size() - 1);

//        LocalTime.of(11,00);

//        assertThat(booking.getBooker(), is("카카오"));
//        assertThat(firstTimeLine.getUseDateTime(), is(LocalDate.now()));
//        assertThat(firstTimeLine.getRoomType(), is("회의실D"));
//        assertThat(finalTimeLine.getUseDateTime(), is(LocalDate.now().plusDays(7)));
//        assertThat(booking.getBookingTimeLines().size(), is(4));
    }




    @Test
    public void list() {
//        LocalDate startTime = LocalDate.of(2018, 9, 3);
//        LocalDate endTime = LocalDate.of(2018, 9, 14);
//
//        BookingDto.ListReq listReq = BookingDto.ListReq.builder()
//                .startTime(startTime)
//                .endTime(endTime)
//                .build();
//
//        bookingService.list(listReq);
    }



}