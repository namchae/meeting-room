package me.namchae.meetingroom.booking.domain;


import com.google.common.collect.Lists;
import lombok.*;
import me.namchae.meetingroom.booking.endpoint.BookingDto;
import me.namchae.meetingroom.booking.helper.TimeHelper;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "booking")
@Getter
@ToString(exclude = "bookingTimeLines")
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    @Column(name = "booker", nullable = false)
    private String booker;

    @Column(name = "repetition_count", nullable = false)
    private Integer repetitionCount;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BookingTimeLine> bookingTimeLines = Lists.newArrayList();

    @Builder
    public Booking(String booker, Integer repetitionCount) {
        this.booker = booker;
        this.repetitionCount = repetitionCount;
    }

    public void addTimeLine(BookingDto.CreateReq createReq) {
        for (LocalDate repetitionUseDate : createReq.repetitionDate()) {
            List<LocalTime> times = TimeHelper.getRangeTimeList(createReq.getBookingTime().getStartTime(), createReq.getBookingTime().getEndTime());
            for (LocalTime time : times) {
                BookingTimeLine timeLine = createReq.toTimeLine(this, LocalDateTime.of(repetitionUseDate, time));
                this.bookingTimeLines.add(timeLine);
            }
        }
    }
}
