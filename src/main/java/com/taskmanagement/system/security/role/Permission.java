package com.taskmanagement.system.security.role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    USER_CREATE("user:create"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),
    USER_READ("user:read");
    private final String permission;
}
