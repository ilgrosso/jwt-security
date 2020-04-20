package com.jakublesko.jwtsecurity.security;

import java.util.Set;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private static final Set<String> USERNAMES = Set.of("user", "admin");

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        if (!USERNAMES.contains(authentication.getName())) {
            throw new BadCredentialsException("User " + authentication.getName() + " not authenticated");
        }

        return new UsernamePasswordAuthenticationToken(
                authentication.getName(),
                null,
                Set.of(new SimpleGrantedAuthority(authentication.getName().toUpperCase())));
    }

    @Override
    public boolean supports(final Class<? extends Object> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }
}
