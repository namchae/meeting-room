package me.namchae.reservation.member.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MemberDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    @ApiModel(value = "MemberDto.SignUp", description = "회원가입")
    public static class SignUp {

        @Email
        @ApiModelProperty(notes = "이메일", required = true, example = "namchae@gmail.com")
        private String email;

        @NotBlank
        @Size(min = 3, max = 20)
        @ApiModelProperty(notes = "닉네임", required = true, example = "namchae")
        private String nickName;

        @NotBlank
        @Size(min = 5, max = 20)
        @ApiModelProperty(notes = "비밀번호", required = true, example = "namchae12")
        private String password;

        @Builder
        public SignUp(String email, String nickName, String password) {
            this.email = email;
            this.nickName = nickName;
            this.password = password;
        }

        public Member toEntity() {
            return Member.builder()
                    .email(this.email)
                    .nickName(this.nickName)
                    .password(Password.builder().value(this.password).build())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    @ApiModel(value = "MemberDto.Login", description = "로그인")
    public static class Login {

        @Email
        @ApiModelProperty(notes = "이메일", required = true, example = "gabriel870214@gmail.com")
        private String email;

        @NotBlank
        @Size(min = 5)
        @ApiModelProperty(notes = "비밀번호", required = true, example = "namchae12")
        private String password;

        @Builder
        public Login(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Getter
    public static class Response {

        @ApiModelProperty(notes = "회원ID", required = true, example = "1")
        private Long id;

        @ApiModelProperty(notes = "이메일", required = true, example = "namchaeya@gmail.com")
        private String email;

        @ApiModelProperty(notes = "닉네임", required = true, example = "namchae")
        private String nickName;

        @ApiModelProperty(notes = "회원가입 날짜", required = true, example = "2018-09-01 15:00:00")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime joined;

        public Response(Member member) {
            this.id = member.getId();
            this.email = member.getEmail();
            this.nickName = member.getNickName();
            this.joined = member.getJoined();
        }
    }
}