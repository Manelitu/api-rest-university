package com.api.restuniversity.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Table(name = "subject")
@Entity
@Data
public class SubjectModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_id")
    private UUID subjectId;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseModel course;
}
