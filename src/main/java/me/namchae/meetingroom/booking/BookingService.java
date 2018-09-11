package me.namchae.meetingroom.booking;

import lombok.extern.slf4j.Slf4j;
import me.namchae.meetingroom.booking.domain.ReservationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public void execute(ReservationDto.Execute execute) {

//        execute.
    }
}
