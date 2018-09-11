package me.namchae.meetingroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EntityScan(
        basePackageClasses = {Jsr310JpaConverters.class},
        basePackages = {"me.namchae.meetingroom"})
public class MeetingRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetingRoomApplication.class, args);
    }
}
