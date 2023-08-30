package com.api.restuniversity.dtos.disciplines;

import com.api.restuniversity.models.CourseModel;
import lombok.Data;

import java.util.UUID;

@Data
public class DisciplineDto {
    private String name;
    private Integer hours;
    private UUID courseId;
}
