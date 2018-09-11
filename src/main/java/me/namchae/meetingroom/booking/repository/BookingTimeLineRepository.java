package me.namchae.meetingroom;

import me.namchae.meetingroom.booking.domain.BookingTimeLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingTimeLineRepository extends JpaRepository<BookingTimeLine, Long> {

   List<BookingTimeLine> findBookingTimeLineByUseDateBetween(LocalDate startDate, LocalDate endDate);
}
