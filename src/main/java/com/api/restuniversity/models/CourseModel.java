package com.api.restuniversity.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "courses")
@Data
public class CourseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id")
    private UUID courseId;
    @NotBlank
    private String name;
    @NotNull
    private Integer periods;
    @OneToMany(mappedBy = "curso")
    private List<DisciplineModel> disciplines;
    private Boolean active = true;
}