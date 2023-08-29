package com.api.restuniversity.controllers;

import com.api.restuniversity.dtos.auth.AuthDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthDto authDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authDto.getLogin(), authDto.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
