package me.namchae.reservation.member.exception;

import lombok.Getter;

// 이메일기준 회원을 못찾았을 경우 Exception 정의.
@Getter
public class MemberNotFoundException extends RuntimeException {

    private final String email;

    public MemberNotFoundException(String email) {
        super(email + "is not found");
        this.email = email;
    }
}
