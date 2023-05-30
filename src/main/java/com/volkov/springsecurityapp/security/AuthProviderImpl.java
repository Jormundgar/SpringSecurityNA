package com.volkov.springsecurityapp.security;

import com.volkov.springsecurityapp.services.UsersDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthProviderImpl implements AuthenticationProvider {

    private final UsersDetailsService usersDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        var userDetails = usersDetailsService.loadUserByUsername(username);
        var password = authentication.getCredentials().toString();
        if(!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
//        return User.class.equals(authentication);
        return true;
    }
}
