package me.namchae.reservation.member;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.namchae.reservation.member.domain.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Slf4j
@Api("회원 API")
@RequestMapping("members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

//    @ApiOperation(value = "회원가입", notes = "요청된 회원정보를 가입")
//    @PostMapping("/signUp")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void signUp(@Valid @RequestBody final MemberDto.SignUp signUp, HttpServletResponse response) {
//        TokenDto.Response token = memberService.signUp(signUp);
//        setHttpHeadersToken(token, response);
//    }
//
//    @ApiOperation(value = "로그인", notes = "요청된 회원정보 로그인")
//    @PostMapping("/login")
//    @ResponseStatus(HttpStatus.OK)
//    public void login(@Valid @RequestBody final MemberDto.Login login, HttpServletResponse response) {
//        TokenDto.Response token = memberService.login(login);
//        setHttpHeadersToken(token, response);
//    }

//    @ApiOperation(value = "로그아웃", notes = "요청된 회원 로그아웃")
//    @PostMapping("/logout")
//    @ResponseStatus(HttpStatus.OK)
//    public void login(@CookieValue("token") String token) {
//        memberService.logout(token);
//    }

    // Token을 header에 저장.
//    private void setHttpHeadersToken(TokenDto.Response token, HttpServletResponse response) {
//        response.setHeader(HEADER_TOKEN_KEY, token.getValue());
//    }

}
