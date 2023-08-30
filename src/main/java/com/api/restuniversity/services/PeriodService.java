package com.api.restuniversity.services;

import com.api.restuniversity.dtos.courses.CreateCourseDto;
import com.api.restuniversity.dtos.periods.PeriodDto;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.NotFoundException;
import com.api.restuniversity.models.CourseModel;
import com.api.restuniversity.models.PeriodModel;
import com.api.restuniversity.repositories.CourseRepository;
import com.api.restuniversity.repositories.PeriodRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PeriodService {
    final private PeriodRepository periodRepository;
    final private CourseRepository courseRepository;

    public PeriodService(
            PeriodRepository periodRepository,
            CourseRepository courseRepository
    ) {
        this.periodRepository = periodRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public PeriodModel create(PeriodDto params) throws BadRequestException {
        Optional<CourseModel> existingCourse = courseRepository.findById(params.getCourseId());

        if (existingCourse.isEmpty()) {
            throw new BadRequestException("Course does not exist");
        }

        var semesterModel = new PeriodModel();
        BeanUtils.copyProperties(params, semesterModel);
        existingCourse.ifPresent(semesterModel::setCourse);

        return periodRepository.save(semesterModel);
    }

    public Page<PeriodModel> list(Pageable pageable) {
        return periodRepository.findAll(pageable);
    }

    public Optional<PeriodModel> listById(UUID id) throws NotFoundException {
        Optional<PeriodModel> existCourse = periodRepository.findById(id);

        if (existCourse.isEmpty()) {
            throw new NotFoundException(CourseModel.class, "id");
        }

        return existCourse;
    }

    @Transactional
    public PeriodModel delete(UUID id) throws NotFoundException {
        PeriodModel existingCourse = periodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PeriodModel.class, "id"));

        var courseModel = new PeriodModel();
        BeanUtils.copyProperties(existingCourse, courseModel);
        courseModel.setActive(false);
        return periodRepository.save(courseModel);
    }

    @Transactional
    public PeriodModel update(UUID id, CreateCourseDto params) throws NotFoundException, BadRequestException {
        PeriodModel existingCourse = periodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PeriodModel.class, "id"));
        if (params.getName().isEmpty()) {
            throw new BadRequestException("Name must exists");
        }
        var courseModel = new PeriodModel();
        BeanUtils.copyProperties(existingCourse, courseModel);

        return periodRepository.save(courseModel);
    }
}
