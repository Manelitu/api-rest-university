package com.api.restuniversity.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Optional;

@Data
public class UpdateUserDto {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String roles;
}