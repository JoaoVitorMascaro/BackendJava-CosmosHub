package com.cosmoshub.api.controller;

import com.cosmoshub.api.dto.LoginRequest;
import com.cosmoshub.api.dto.LoginResponse;
import com.cosmoshub.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        return service.autenticar(req);
    }
}