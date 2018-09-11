package me.namchae.meetingroom.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.namchae.meetingroom.booking.domain.Booking;
import me.namchae.meetingroom.booking.endpoint.BookingDto;
import me.namchae.meetingroom.booking.domain.BookingTimeLine;
import me.namchae.meetingroom.booking.repository.BookingRepository;
import me.namchae.meetingroom.booking.repository.BookingTimeLineRepository;
import me.namchae.meetingroom.booking.exception.DuplicateBookingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static me.namchae.meetingroom.booking.helper.TimeHelper.getBetweenTimes;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final BookingTimeLineRepository bookingTimeLineRepository;

    @Transactional
    public Booking execute(BookingDto.CreateReq createReq) {
        final Booking booking = createReq.toEntity();
        booking.addTimeLine(createReq);

        Booking returnBooking;
        try {
            returnBooking = bookingRepository.save(booking);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateBookingException(ex.getMessage());
        }
        return returnBooking;
    }

    public List<Booking> list(LocalDate useDate) {
        Pair<LocalDateTime, LocalDateTime> betweenTimes = getBetweenTimes(useDate);
        List<BookingTimeLine> timeLineList = bookingTimeLineRepository.findBookingTimeLineByUseDateTimeBetween(betweenTimes.getFirst(), betweenTimes.getSecond());

        return timeLineList.stream()
                .map(BookingTimeLine::getBooking)
                .distinct()
                .collect(Collectors.toList());
    }
}
