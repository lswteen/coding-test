package com.illuminarean.example.user.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


public class User {

    private Long id;

    private String email;

    private String password;

    private String name;

    private int loginCount;

    private LocalDateTime lastLoginDate;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;

    public User() {}

    private User(Long id, String email, String password, String name, int loginCount, LocalDateTime lastLoginDate, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.loginCount = loginCount;
        this.lastLoginDate = lastLoginDate;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }

    public void login(PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(credentials, this.password)) {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    public void afterLoginSuccess() {
        this.lastLoginDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("email", email)
                .add("password", password)
                .add("name", name)
                .add("loginCount", loginCount)
                .add("lastLoginDate", lastLoginDate)
                .add("createDate", createDate)
                .add("modifiedDate", modifiedDate)
                .toString();
    }

    static public class Builder {
        private Long id;

        private String email;

        private String password;

        private String name;

        private int loginCount;

        private LocalDateTime lastLoginDate;

        private LocalDateTime createDate;

        private LocalDateTime modifiedDate;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder loginCount(int loginCount) {
            this.loginCount = loginCount;
            return this;
        }

        public Builder lastLoginDate(LocalDateTime lastLoginDate) {
            this.lastLoginDate = lastLoginDate;
            return this;
        }

        public Builder createDate(LocalDateTime createDate) {
            this.createDate = createDate;
            return this;
        }

        public Builder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public User build() {
            return new User(
                    id,
                    email,
                    password,
                    name,
                    loginCount,
                    lastLoginDate,
                    createDate,
                    modifiedDate
            );
        }
    }
}

