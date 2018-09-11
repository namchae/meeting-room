package me.namchae.meetingroom.booking.repository;

import me.namchae.meetingroom.booking.domain.BookingTimeLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingTimeLineRepository extends JpaRepository<BookingTimeLine, Long> {

   List<BookingTimeLine> findBookingTimeLineByUseDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
