package com.api.restuniversity.dtos.periods;

import com.api.restuniversity.models.DisciplineModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PeriodDto {
    private List<UUID> disciplines;
}
