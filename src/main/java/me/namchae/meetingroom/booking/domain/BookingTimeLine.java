package me.namchae.meetingroom.booking.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "booking_time", uniqueConstraints = @UniqueConstraint(columnNames = {"use_date_time", "room_type"}))
@Getter
@ToString(exclude = {"booking"})
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingTimeLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_seq")
    private Long seq;

    @Column(name = "use_date_time", nullable = false)
    private LocalDateTime useDateTime;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Builder
    public BookingTimeLine(LocalDateTime useDateTime, String roomType, Booking booking) {
        this.useDateTime = useDateTime;
        this.roomType = roomType;
        this.booking = booking;
    }
}
