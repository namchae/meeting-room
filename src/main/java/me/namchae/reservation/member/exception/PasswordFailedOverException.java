package me.namchae.reservation.member.exception;

import lombok.Getter;


// 비밀번호 초과 Exception.
@Getter
public class PasswordFailedOverException extends RuntimeException {

    private final int failedCount;

    public PasswordFailedOverException(int failedCount) {
        super("failCount is over");
        this.failedCount = failedCount;
    }
}
