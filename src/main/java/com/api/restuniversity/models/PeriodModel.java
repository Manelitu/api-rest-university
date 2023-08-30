package com.api.restuniversity.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "periods")
@Entity
@Data
public class PeriodModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "period_id")
    private UUID periodId;

    @NotNull
    private Integer period;

    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private DisciplineModel discipline;

    private Boolean active = true;
}
