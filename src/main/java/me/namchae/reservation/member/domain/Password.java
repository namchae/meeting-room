package me.namchae.reservation.member.domain;


import lombok.*;
import me.namchae.reservation.member.exception.PasswordFailedOverException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    @Column(name = "password", nullable = false)
    private String value;

    @Column(name = "password_failed_count", nullable = false)
    private int failedCount;

    @Builder
    public Password(final String value) {
        this.value = encodePassword(value);
    }

    /**
     * 로그인시, 비밀번호 검증.
     * @param rawPassword (요청된 비밀번호)
     * @return 비밀번호 검증 결과.
     * @exception PasswordFailedOverException
     */
    public boolean isMatch(final String rawPassword) {
        final int MAX_FAILED_COUNT = 5;

        if (failedCount >= MAX_FAILED_COUNT) {
            throw new PasswordFailedOverException(this.failedCount);
        }

        final boolean matches = isMatches(rawPassword);

        updateFailedCount(matches);
        return matches;
    }


    // 비밀번호 암호화
    private String encodePassword(final String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    // 비밀번호 검증
    private boolean isMatches(String rawPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, this.value);
    }

    // 비밀번호 match에 따라 실패 횟수 증가 또는 초기화.
    private void updateFailedCount(boolean matches) {
        if (matches) {
            initFailedCount();
        } else {
            increaseFailedCount();
        }
    }

    // 비밀번호 실패 횟수 초기화
    private void initFailedCount() {
        this.failedCount = 0;
    }

    // 비밀번호 실패 횟수 증가
    private void increaseFailedCount() {
        this.failedCount++;
    }
}
