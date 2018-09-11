package me.namchae.meetingroom.booking.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservation_time", uniqueConstraints = @UniqueConstraint(columnNames = "time_id"))
@Getter
@ToString
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationTimeLine {

    @Id
    @Column(name = "time_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name = "time_id")
    private String id; // useDate + roomType;

    @Column(name = "use_date", nullable = false)
    private LocalDate useDate;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Booking booking;

    @Builder
    public ReservationTimeLine(String id, LocalDate useDate, String roomType) {
        this.id = id;
        this.useDate = useDate;
        this.roomType = roomType;
    }
}
