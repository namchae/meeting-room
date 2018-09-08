package me.namchae.reservation.member.exception;

import lombok.Getter;

// 회원 이메일 중복가입 Exception 정의
@Getter
public class MemberDuplicateException extends RuntimeException {
    private final String email;

    public MemberDuplicateException(String email) {
        super(email + " is existed");
        this.email = email;
    }
}
