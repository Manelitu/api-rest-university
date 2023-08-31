package com.api.restuniversity.dtos.periods;

import lombok.Data;

import java.util.UUID;

@Data
public class PeriodDto {
    private Integer period;
    private UUID courseId;
}
