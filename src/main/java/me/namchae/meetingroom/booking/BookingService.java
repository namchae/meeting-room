package me.namchae.meetingroom.booking;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.namchae.meetingroom.booking.domain.Booking;
import me.namchae.meetingroom.booking.endpoint.BookingDto;
import me.namchae.meetingroom.booking.domain.BookingTimeLine;
import me.namchae.meetingroom.booking.repository.BookingRepository;
import me.namchae.meetingroom.booking.repository.BookingTimeLineRepository;
import me.namchae.meetingroom.booking.exception.DuplicateBookingException;
import org.h2.jdbc.JdbcSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
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

//        try {
//            return bookingRepository.save(booking);
//        } catch (DataIntegrityViolationException ex) {
//            log.error("error in : {}", ex);
//            if (ex.getRootCause() instanceof JdbcSQLException) {
//                JdbcSQLException exception = (JdbcSQLException) ex.getRootCause();
//                if (exception.getErrorCode() == 23505) {
////                    throw new DuplicateBookingException(ex.getMessage());
//
//                    List<Map.Entry<String, LocalDateTime>> checkList = Lists.newArrayList();
//                    for (BookingTimeLine timeLine : booking.getBookingTimeLines()) {
//                        checkList.add(new AbstractMap.SimpleEntry<>(timeLine.getRoomType(), timeLine.getUseDateTime()));
//                    }
//                    test(checkList);
//                    List<BookingTimeLine> bookingTimeLines = booking.getBookingTimeLines();


//                    log.error("error Code : {}", exception.getErrorCode());
//                    log.error("message : {}", exception.getOriginalMessage());

//                }
//            }
//            log.error("internal error : {}", ex.getRootCause().getClass());
//            throw ex;
//        }
    }

    public void test(List<Map.Entry<String, LocalDateTime>> checkList) {
        for (Map.Entry<String, LocalDateTime> timeLine : checkList) {
            Optional<BookingTimeLine> timeLineOptional = bookingTimeLineRepository.findByRoomTypeAndUseDateTime(timeLine.getKey(), timeLine.getValue());
            if (timeLineOptional.isPresent()) {
                throw new DuplicateBookingException(timeLine.getKey(), timeLine.getValue());
            }
        }
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
