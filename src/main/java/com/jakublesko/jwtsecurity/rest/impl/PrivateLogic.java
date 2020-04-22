package com.jakublesko.jwtsecurity.rest.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class PrivateLogic {

    @PreAuthorize("hasRole('USER')")
    public String getUserMessage() {
        return "Hello from private API controller for USER";
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminMessage() {
        return "Hello from private API controller for ADMIN";
    }
}
