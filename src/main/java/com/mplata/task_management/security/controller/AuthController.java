package com.mplata.task_management.security.controller;

import com.mplata.task_management.dto.UserDTO;
import com.mplata.task_management.security.model.AuthRequest;
import com.mplata.task_management.security.model.AuthResponse;
import com.mplata.task_management.security.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello from API AUTH-USER";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.register(userDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }


}
