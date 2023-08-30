package com.api.restuniversity.dtos.subjects;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateSubjectDto {
    private String name;
    private UUID courseId;
}
