package com.api.restuniversity.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserDto {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String name;
}