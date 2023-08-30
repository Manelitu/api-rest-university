package com.api.restuniversity.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "semester")
@Entity
@Data
public class PeriodModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "period_id")
    private UUID periodId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseModel course;

    @ManyToMany
    @JoinTable(name = "period_discipline",
            joinColumns = @JoinColumn(name = "period_id"),
            inverseJoinColumns = @JoinColumn(name = "discipline_id"))
    private List<DisciplineModel> disciplines = new ArrayList<>();

    private Boolean active = true;
}
