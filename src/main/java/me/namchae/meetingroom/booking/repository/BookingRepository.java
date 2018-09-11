package me.namchae.meetingroom.booking;

import me.namchae.meetingroom.booking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {


}
