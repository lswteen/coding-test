package com.illuminarean.example.security;

import static com.google.common.base.Preconditions.checkNotNull;

public class JdbcAuthenticationPrincipal {

    private final Long id;

    private final String name;

    JdbcAuthenticationPrincipal(Long id, String name) {
        checkNotNull(id, "id must be provided");
        checkNotNull(name, "name must be provided");

        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
