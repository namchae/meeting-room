package me.namchae.reservation.member.domain;

import lombok.*;
import me.namchae.reservation.member.code.MemberRole;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@ToString
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Embedded
    private Password password;


    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole memberRole;

    @Column(name = "joined", nullable = false)
    private LocalDateTime joined;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @Builder
    public Member(String email, String nickName, Password password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.joined = LocalDateTime.now();
    }
}