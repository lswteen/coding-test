package com.illuminarean.example.security;


import com.illuminarean.example.error.NotFoundException;
import com.illuminarean.example.user.domain.User;
import com.illuminarean.example.user.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ClassUtils;

public class JdbcAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    public JdbcAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return processUserAuthentication(
                String.valueOf(authentication.getPrincipal()),
                String.valueOf(authentication.getCredentials().toString())
        );
    }

    private Authentication processUserAuthentication(String email, String password) {
        try {
            User user = userService.login(email, password);
            JdbcAuthenticationToken token = new JdbcAuthenticationToken(
                    new JdbcAuthenticationPrincipal(user.getId(), user.getEmail()),
                    password,
                    AuthorityUtils.createAuthorityList("USER")
            );
            token.setDetails(user);
            return token;
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ClassUtils.isAssignable(JdbcAuthenticationToken.class, authentication);
    }
}
