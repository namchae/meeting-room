package me.namchae.reservation.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PostAuthorizationToken(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
    }
}
