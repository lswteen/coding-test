package com.illuminarean.example.user.domain.repo;


import com.illuminarean.example.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);

    Optional<User> findById(long id);

    void update(User user);

}
