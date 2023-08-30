package com.api.restuniversity.dtos.courses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseDto {
    @NotBlank
    private String name;
    private Integer periods;
}
