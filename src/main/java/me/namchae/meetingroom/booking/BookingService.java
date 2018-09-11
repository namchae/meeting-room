package me.namchae.meetingroom.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.namchae.meetingroom.booking.domain.Booking;
import me.namchae.meetingroom.booking.endpoint.BookingDto;
import me.namchae.meetingroom.booking.domain.BookingTimeLine;
import me.namchae.meetingroom.booking.repository.BookingRepository;
import me.namchae.meetingroom.booking.repository.BookingTimeLineRepository;
import me.namchae.meetingroom.booking.exception.DuplicateBookingException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

        for (BookingTimeLine timeLine : booking.getBookingTimeLines()) {
            Optional<BookingTimeLine> timeLineOptional = bookingTimeLineRepository.findByRoomTypeAndUseDateTime(timeLine.getRoomType(), timeLine.getUseDateTime());
            if (timeLineOptional.isPresent()) {
                throw new DuplicateBookingException(timeLine.getRoomType(), timeLine.getUseDateTime());
            }
        }
        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public List<Booking> list(LocalDate useDate) {
        Pair<LocalDateTime, LocalDateTime> betweenTimes = getBetweenTimes(useDate);
        List<BookingTimeLine> timeLineList = bookingTimeLineRepository.findBookingTimeLineByUseDateTimeBetween(betweenTimes.getFirst(), betweenTimes.getSecond());

        return timeLineList.stream()
                .map(BookingTimeLine::getBooking)
                .distinct()
                .collect(Collectors.toList());
    }
}
