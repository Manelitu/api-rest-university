package com.api.restuniversity.controllers;

import com.api.restuniversity.dtos.auth.AuthDto;
import com.api.restuniversity.dtos.auth.LoginResponseDto;
import com.api.restuniversity.models.UserModel;
import com.api.restuniversity.services.TokenService;
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
    final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthDto authDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authDto.getLogin(), authDto.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((UserModel) auth.getPrincipal());
        var response = new LoginResponseDto(token);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
