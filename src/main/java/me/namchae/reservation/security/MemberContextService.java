package me.namchae.reservation.security;

import me.namchae.reservation.member.MemberRepository;
import me.namchae.reservation.member.domain.Member;
import me.namchae.reservation.member.exception.MemberNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MemberContextService implements UserDetailsService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberContextService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<Member> member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberNotFoundException(email));
        return null;
    }
}
