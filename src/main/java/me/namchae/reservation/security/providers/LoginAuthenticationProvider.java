package me.namchae.reservation.security.providers;

import me.namchae.reservation.security.tokens.PreAuthorizationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class LoginAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
