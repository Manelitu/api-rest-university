package com.api.restuniversity.services;

import com.api.restuniversity.dtos.courses.CourseDto;
import com.api.restuniversity.dtos.periods.PeriodDto;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.NotFoundException;
import com.api.restuniversity.models.CourseModel;
import com.api.restuniversity.models.DisciplineModel;
import com.api.restuniversity.models.PeriodModel;
import com.api.restuniversity.repositories.CourseRepository;
import com.api.restuniversity.repositories.DisciplineRepository;
import com.api.restuniversity.repositories.PeriodRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
        CourseModel course =  courseRepository.findById(params.getCourseId())
                .orElseThrow(() -> new BadRequestException(DisciplineModel.class, "ID doesnt exists"));


        PeriodModel periodModel = new PeriodModel();
        BeanUtils.copyProperties(params, periodModel);
        periodModel.setCourse(course);
        periodModel.setCourseOnUUID();

        return periodRepository.save(periodModel);
    }


    public List<PeriodModel> list(Pageable pageable) {
        return periodRepository.findByActiveTrue(pageable).getContent();
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
        PeriodModel existingPeriod = periodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PeriodModel.class, "id"));

        var periodModel = new PeriodModel();
        BeanUtils.copyProperties(existingPeriod, periodModel);
        periodModel.setPeriod(existingPeriod.getPeriod());
        periodModel.setDisciplines(existingPeriod.getDisciplines());
        periodModel.setPeriodId(id);
        periodModel.setActive(false);
        periodModel.setCourseOnUUID();
        return periodRepository.save(periodModel);
    }

    @Transactional
    public PeriodModel update(UUID id, PeriodDto params) throws NotFoundException, BadRequestException {
        PeriodModel existingPeriod = periodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(PeriodModel.class, "id"));

        PeriodModel periodModel = new PeriodModel();

        periodModel.setPeriod(existingPeriod.getPeriod());
        periodModel.setDisciplines(existingPeriod.getDisciplines());
        periodModel.setPeriodId(id);
        periodModel.setActive(existingPeriod.getActive());
        periodModel.setCourseOnUUID();

        BeanUtils.copyProperties(params, periodModel);

        return periodRepository.save(periodModel);
    }
}
