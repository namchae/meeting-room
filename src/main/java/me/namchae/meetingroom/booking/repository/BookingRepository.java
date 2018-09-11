package me.namchae.meetingroom.booking.repository;

import me.namchae.meetingroom.booking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
