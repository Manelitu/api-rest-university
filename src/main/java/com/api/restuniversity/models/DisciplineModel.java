package com.api.restuniversity.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Table(name = "disciplines")
@Entity
@Data
public class DisciplineModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "discipline_id")
    private UUID disciplineId;

    @NotBlank
    private String name;

    @NotNull
    private Integer hours;

    private Boolean active = true;
}
