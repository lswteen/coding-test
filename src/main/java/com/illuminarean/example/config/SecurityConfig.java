package com.illuminarean.example.config;

import com.illuminarean.example.config.properties.JwtTokenProperty;
import com.illuminarean.example.security.*;
import com.illuminarean.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProperty configure;

    private final UnauthorizedHandler unauthorizedHandler;

    public SecurityConfig(JwtTokenProperty configure, UnauthorizedHandler unauthorizedHandler) {
        this.configure = configure;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcAuthenticationProvider jdbcAuthenticationProvider(@Lazy UserService userService) {
        return new JdbcAuthenticationProvider(userService);
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder builder, JdbcAuthenticationProvider authenticationProvider) {
        builder.authenticationProvider(authenticationProvider);
    }

    @Bean
    public Jwt jwt() {
        return new Jwt(configure.getClientSecret(), configure.getExpiration());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().disable()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .formLogin().disable()
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpStatus.OK.value()))
                .and()
                .authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll();

        http
                .addFilterBefore(new JwtLoginFilter("/api/login", authenticationManager(), jwt()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(jwt()), UsernamePasswordAuthenticationFilter.class);

    }
}
