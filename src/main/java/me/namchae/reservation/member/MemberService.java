package me.namchae.reservation.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Service
public class MemberService {

//    private final JwtGenerator jwtGenerator;
//
//    private final MemberRepository memberRepository;
//
//    @Autowired
//    public MemberService(JwtGenerator jwtGenerator,
//                         MemberRepository memberRepository) {
//        this.jwtGenerator = jwtGenerator;
//        this.memberRepository = memberRepository;
//    }

    // 테스트환경에서 임시로 캐시에 넣어놓는다.
    // ActiveProfile을 개발환경으로 하고 하면 되지만 시간이 없다.
//    @PostConstruct
//    public void testInit() {
//        Member member = findMemberById(1L).get();
//        jwtGenerator.putToken(member);
//    }
//
//    // 회원가입
//    public TokenDto.Response signUp(MemberDto.SignUp signUp) {
//        if (isExistByEmail(signUp.getEmail())) {
//            throw new MemberDuplicateException(signUp.getEmail());
//        }
//
//        Member member = memberRepository.save(signUp.toEntity());
//        log.info("[{}] signUp", member.getId());
//        return generateToken(member);
//    }
//
//    public Member findByEmail(String email) {
//        Optional<Member> memberOptional = memberRepository.findByEmail(email);
//        if (!memberOptional.isPresent()) {
//            throw new MemberDuplicateException(email);
//        }
//        return memberOptional.get();
//    }
//
//    // 회원정보로 token 생성
//    private TokenDto.Response generateToken(Member member) {
//        return jwtGenerator.generate(member);
//    }
//
//    // 로그인
//    public TokenDto.Response login(MemberDto.Login login) {
//        Optional<Member> memberOptional = memberRepository.findByEmail(login.getEmail());
//        if (!memberOptional.isPresent()) {
//            throw new MemberNotFoundException(login.getEmail());
//        }
//        boolean matches = memberOptional.get().getPassword().isMatch(login.getPassword());
//        if (matches) {
//            return generateToken(memberOptional.get());
//        } else {
//            throw new InvalidPasswordException();
//        }
//    }
//
//    // 이메일로 회원 유무 확인
//    public boolean isExistByEmail(String email) {
//        Optional<Member> memberOptional = memberRepository.findByEmail(email);
//        return memberOptional.isPresent();
//    }
//
//    // 회원번호로 회원 유무 확인
//    public boolean isExistById(long id) {
//        Optional<Member> memberOptional = memberRepository.findById(id);
//        return memberOptional.isPresent();
//    }
//
//    public Optional<Member> findMemberById(long id) {
//        return memberRepository.findById(id);
//    }
//
//    // 토큰으로 회원Email 확인
//    public Long getTokenByMemberId(String token) {
//        TokenDto.Response tokenResponse = jwtGenerator.validate(token);
//        System.out.println(tokenResponse);
//        return tokenResponse.getMemberId();
//    }
//
//    // 로그아웃 : 캐시 토큰제거.
//    public void logout(String token) {
//        TokenDto.Response tokenResponse = jwtGenerator.validate(token);
//        jwtGenerator.clearCache(tokenResponse.getEmail());
//    }

}
