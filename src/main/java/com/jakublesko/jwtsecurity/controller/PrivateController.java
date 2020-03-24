package com.jakublesko.jwtsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private")
public class PrivateController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "user")
    public String user() {
        return "Hello from private API controller for USER";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "admin")
    public String admin() {
        return "Hello from private API controller for ADMIN";
    }
}
