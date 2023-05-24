package com.illuminarean.example.user.service;

import com.illuminarean.example.error.NotFoundException;
import com.illuminarean.example.user.api.dto.UserDto;
import com.illuminarean.example.user.domain.User;
import com.illuminarean.example.user.domain.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User login(String email, String password) {
        checkNotNull(password, "password must be provided");
        User user = findByEmail(email);
        user.login(passwordEncoder, password);
        user.afterLoginSuccess();
        userRepository.update(user);
        return user;
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        checkNotNull(email, "email must be provided");
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Could nof found user for " + email));
    }

    public UserDto findById(Long id) {
        checkNotNull(id, "id must be provided");
        return new UserDto(userRepository.findById(id).orElseThrow(() -> new NotFoundException("Could nof found user for " + id)));
    }
}