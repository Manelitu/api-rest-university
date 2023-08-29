package com.api.restuniversity.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginResponseDto {
    @NotBlank
    private String token;

    public LoginResponseDto(String token) {
        this.token = token;
    }
}
