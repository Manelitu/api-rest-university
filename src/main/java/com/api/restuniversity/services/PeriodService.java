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
    final private DisciplineRepository disciplineRepository;

    public PeriodService(
            PeriodRepository periodRepository,
            DisciplineRepository disciplineRepository
    ) {
        this.periodRepository = periodRepository;
        this.disciplineRepository = disciplineRepository;
    }

    @Transactional
    public PeriodModel create(PeriodDto params) throws BadRequestException {
        List<UUID> disciplineUuids = params.getDisciplines();

        boolean allDisciplinesExist = disciplineUuids.stream()
                .allMatch(disciplineRepository::existsById);

        if (!allDisciplinesExist) {
            throw new BadRequestException("One or more disciplines do not exist");
        }

        PeriodModel periodModel = new PeriodModel();
        BeanUtils.copyProperties(params, periodModel);

        List<DisciplineModel> disciplines = disciplineUuids.stream()
                .map(disciplineUuid -> disciplineRepository.findById(disciplineUuid)
                        .orElseThrow(() -> new EntityNotFoundException("Discipline not found")))
                .collect(Collectors.toList());

        periodModel.setDisciplines(disciplines);

        return periodRepository.save(periodModel);
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
    public PeriodModel update(UUID id, PeriodDto params) throws NotFoundException, BadRequestException {
        boolean allDisciplinesExist = params.getDisciplines().stream()
                .allMatch(disciplineRepository::existsById);

        if (!allDisciplinesExist) {
            throw new BadRequestException("One or more disciplines do not exist");
        }

        PeriodModel periodModel = new PeriodModel();
        BeanUtils.copyProperties(params, periodModel);

        List<DisciplineModel> disciplines = params.getDisciplines().stream()
                .map(disciplineUuid -> disciplineRepository.findById(disciplineUuid)
                        .orElseThrow(() -> new EntityNotFoundException("Discipline not found")))
                .collect(Collectors.toList());

        periodModel.setDisciplines(disciplines);

        return periodRepository.save(periodModel);
    }
}
