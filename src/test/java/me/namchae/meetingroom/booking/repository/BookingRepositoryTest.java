package me.namchae.meetingroom.booking.repository;

import me.namchae.meetingroom.booking.domain.Booking;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;



    @Test
    public void getTest() {

        Booking booking = bookingRepository.getOne(1L);

        System.out.println(
        booking
        );
//        System.out.println(
//        booking.getBookingTimeLines()
//        );
    }

}