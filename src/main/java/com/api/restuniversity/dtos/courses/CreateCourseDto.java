package com.api.restuniversity.dtos.courses;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCourseDto {
    @NotBlank
    private String name;
}
