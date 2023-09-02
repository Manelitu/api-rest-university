package com.api.restuniversity.dtos.disciplines;

import lombok.Data;

@Data
public class UpdateDisciplineDto {
    private String name;
    private Integer hours;
    private String description;
}
