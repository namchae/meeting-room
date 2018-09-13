package me.namchae.meetingroom.booking.repository;

import me.namchae.meetingroom.booking.domain.BookingTimeLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingTimeLineRepository extends JpaRepository<BookingTimeLine, Long> {

   List<BookingTimeLine> findBookingTimeLineByUseDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

   @Lock(LockModeType.PESSIMISTIC_READ)
   Optional<BookingTimeLine> findByRoomTypeAndUseDateTime(String roomType, LocalDateTime useDateTime);
}
