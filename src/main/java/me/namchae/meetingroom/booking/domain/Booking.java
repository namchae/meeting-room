package me.namchae.meetingroom.reservation.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meetingroom")
@Getter
@ToString
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;


//    @ManyToOne(fetch  = FetchType.LAZY)
//    @JoinColumn(name = "user_name", nullable = false)
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "repetition_count", nullable = false)
    private Integer repetitionCount;

    @OneToMany(mappedBy = "meetingroom", cascade = CascadeType.ALL, fetch  = FetchType.LAZY)
    private List<ReservationTimeLine> reservationTimeLines = new ArrayList<>();

    @Builder
    public Reservation(String userName, Integer repetitionCount, List<ReservationTimeLine> reservationTimeLines) {
        this.userName = userName;
        this.repetitionCount = repetitionCount;
        this.reservationTimeLines = reservationTimeLines;
    }
}
