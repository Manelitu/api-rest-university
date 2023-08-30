package com.api.restuniversity.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseModel course;

    @JsonManagedReference
    @OneToMany(mappedBy = "disciplines", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("courses")
    private List<PeriodModel> periods;

    private Boolean active = true;
}
