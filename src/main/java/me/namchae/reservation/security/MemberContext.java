package me.namchae.reservation.security;

import me.namchae.reservation.member.code.MemberRole;
import me.namchae.reservation.member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class MemberContext extends User {

    private MemberContext(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
    }

    public static MemberContext fromMemberModel(Member member) {
        return new MemberContext(member.getEmail(), member.getPassword().getValue(), parseAuthorities(member.getMemberRole()));
    }

    public static List<SimpleGrantedAuthority> parseAuthorities(MemberRole memberRole) {
        return Arrays.asList(memberRole).stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(toList());
    }
}
