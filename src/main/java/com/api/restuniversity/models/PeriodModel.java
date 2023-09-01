package com.api.restuniversity.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "periods")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class PeriodModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "period_id")
    private UUID periodId;

    @NotNull
    private String period;
    private UUID course_uuid;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseModel course;

    @JsonManagedReference
    @OneToMany(mappedBy = "periods", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("courses")
    private List<DisciplineModel> disciplines;

    private Boolean active = true;

    public void setCourseOnUUID() {
        this.course_uuid = course.getCourseId();
    }
}
