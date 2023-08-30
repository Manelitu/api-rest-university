package com.api.restuniversity.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Table(name = "semester")
@Entity
@Data
public class SemesterModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "semester_id")
    private UUID semesterId;

    @NotBlank
    private String period;

    @ManyToMany
    @JoinTable(name = "semester_subject",
            joinColumns = @JoinColumn(name = "semester_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<SubjectModel> subjectModels;
}
