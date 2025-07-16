package com.noatechsolutions.noaguard.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class ProtectedController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "🌐 This endpoint is public";
    }

    @GetMapping("/admin")
    public String onlyForAdmins() {
        return "✅ Access granted: DAYCARE_ADMIN";
    }
}
