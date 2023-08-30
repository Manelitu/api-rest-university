package com.api.restuniversity.dtos.periods;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PeriodDto {
    private List<UUID> disciplines;
}
