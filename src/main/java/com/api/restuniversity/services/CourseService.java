package com.api.restuniversity.services;

import com.api.restuniversity.dtos.courses.CourseDto;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import com.api.restuniversity.exceptions.NotFoundException;
import com.api.restuniversity.models.CourseModel;
import com.api.restuniversity.repositories.CourseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {
    final private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public CourseModel create(CourseDto params) throws ConflictException {
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(params, courseModel);
        return courseRepository.save(courseModel);
    }

    public List<CourseModel> list(Pageable pageable) {
        return courseRepository.findByActiveTrue(pageable).getContent();
    }

    public Optional<CourseModel> listById(String name) throws NotFoundException {
        Optional<CourseModel> existCourse = courseRepository.findByName(name);

        if (existCourse.isEmpty()) {
            throw new NotFoundException(CourseModel.class, "id");
        }

        return existCourse;
    }

    @Transactional
    public CourseModel delete(String name) throws NotFoundException {
        CourseModel existingCourse = courseRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(CourseModel.class, "id"));

        var courseModel = new CourseModel();
        BeanUtils.copyProperties(existingCourse, courseModel);
        courseModel.setActive(false);
        courseModel.setDescription(existingCourse.getDescription());
        return courseRepository.save(courseModel);
    }

    @Transactional
    public CourseModel update(String name, CourseDto params) throws NotFoundException, BadRequestException {
        CourseModel existingCourse = courseRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(CourseModel.class, "id"));
        if (params.getName().isEmpty()) {
            throw new BadRequestException("Name must exists");
        }
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(existingCourse, courseModel);
        courseModel.setName(params.getName());
        courseModel.setDescription(params.getDescription());

        return courseRepository.save(courseModel);
    }
}
