package com.api.restuniversity.dtos.disciplines;

import lombok.Data;

import java.util.UUID;

@Data
public class DisciplineDto {
    private String name;
    private Integer hours;
    private UUID periodId;
}