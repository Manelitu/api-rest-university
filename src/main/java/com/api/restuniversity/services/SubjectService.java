package com.api.restuniversity.services;

import com.api.restuniversity.dtos.courses.CreateCourseDto;
import com.api.restuniversity.dtos.subjects.CreateSubjectDto;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import com.api.restuniversity.exceptions.NotFoundException;
import com.api.restuniversity.models.CourseModel;
import com.api.restuniversity.models.SubjectModel;
import com.api.restuniversity.repositories.CourseRepository;
import com.api.restuniversity.repositories.SubjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class SubjectService {
    final private SubjectRepository subjectRepository;
    final private CourseRepository courseRepository;

    public SubjectService(
            SubjectRepository subjectRepository,
            CourseRepository courseRepository
    ) {
        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public SubjectModel create(CreateSubjectDto params) throws ConflictException, BadRequestException {
        Optional<CourseModel> existingCourse = courseRepository.findById(params.getCourseId());

        if (existingCourse.isEmpty()) {
            throw new BadRequestException("Course does not exist");
        }

        if (subjectRepository.existsByName(params.getName())) {
            throw new ConflictException("Subject already exists");
        }

        var subjectModel = new SubjectModel();
        BeanUtils.copyProperties(params, subjectModel);

        existingCourse.ifPresent(subjectModel::setCourse);

        return subjectRepository.save(subjectModel);
    }

    public Page<SubjectModel> list(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    public Optional<SubjectModel> listById(UUID id) throws NotFoundException {
        Optional<SubjectModel> existSubject = subjectRepository.findById(id);

        if (existSubject.isEmpty()) {
            throw new NotFoundException(SubjectModel.class, "id");
        }

        return existSubject;
    }

    @Transactional
    public SubjectModel delete(UUID id) throws NotFoundException {
        SubjectModel existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(SubjectModel.class, "id"));

        var subjectModel = new SubjectModel();
        BeanUtils.copyProperties(existingSubject, subjectModel);
        subjectModel.setActive(false);
        return subjectRepository.save(subjectModel);
    }

    @Transactional
    public SubjectModel update(UUID id, CreateSubjectDto params) throws NotFoundException, BadRequestException {
        SubjectModel existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(SubjectModel.class, "Subject does not exist"));

        var existingCourse = courseRepository.findById(params.getCourseId());

        if (existingCourse.isEmpty()) {
            throw new NotFoundException(CourseModel.class, "Course does not exist");
        }

        var subjectModel = new SubjectModel();
        BeanUtils.copyProperties(existingSubject, subjectModel);

        if (params.getName().isEmpty()) {
            subjectModel.setName(existingSubject.getName());
        } else {
            subjectModel.setName(params.getName());
        }

        subjectModel.setSubjectId(existingSubject.getSubjectId());
        existingCourse.ifPresent(subjectModel::setCourse);
        return subjectRepository.save(subjectModel);
    }
}
