package com.banking.paymentrequest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banking.paymentrequest.dto.response.GenericResponse;
import com.banking.paymentrequest.security.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

   @GetMapping("/token")
    public ResponseEntity<GenericResponse<String>> getToken(@Valid @RequestParam String email, @RequestParam String phone) {
        String token = jwtUtil.generateToken(email, phone);
        GenericResponse<String> response = new GenericResponse<>(
            "Token generated successfully",
            "20002",
            token,
            "SUCCESS"
        );
        return ResponseEntity.ok(response);
    }
}
