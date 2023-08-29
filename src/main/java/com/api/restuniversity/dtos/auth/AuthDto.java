package com.api.restuniversity.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthDto {
    @NotBlank
    @Email
    private String login;

    @NotBlank
    private String password;
}
