package com.api.restuniversity.services;

import com.api.restuniversity.dtos.disciplines.DisciplineDto;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import com.api.restuniversity.exceptions.NotFoundException;
import com.api.restuniversity.models.CourseModel;
import com.api.restuniversity.models.DisciplineModel;
import com.api.restuniversity.models.PeriodModel;
import com.api.restuniversity.repositories.CourseRepository;
import com.api.restuniversity.repositories.DisciplineRepository;
import com.api.restuniversity.repositories.PeriodRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DisciplineService {
    final private DisciplineRepository disciplineRepository;
    final private PeriodRepository periodRepository;

    public DisciplineService(
            DisciplineRepository disciplineRepository,
            PeriodRepository periodRepository
    ) {
        this.disciplineRepository = disciplineRepository;
        this.periodRepository = periodRepository;
    }

    @Transactional
    public DisciplineModel create(DisciplineDto params) throws ConflictException, BadRequestException {
        if (disciplineRepository.existsByName(params.getName())) {
            throw new ConflictException("Discipline already exists");
        }

        PeriodModel periodExists = periodRepository.findById(params.getPeriodId())
                .orElseThrow();

        var subjectModel = new DisciplineModel();
        BeanUtils.copyProperties(params, subjectModel);

        subjectModel.setPeriods(periodExists);

        return disciplineRepository.save(subjectModel);
    }

    public List<DisciplineModel> list(Pageable pageable) {
        return disciplineRepository.findAll(pageable).getContent();
    }

    @Transactional
    public DisciplineModel listById(UUID id) throws NotFoundException {
        return disciplineRepository.findById(id).orElseThrow();
    }

    @Transactional
    public DisciplineModel delete(UUID id) throws NotFoundException {
        DisciplineModel existingDiscipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(DisciplineModel.class, "id"));

        var discipline = new DisciplineModel();
        BeanUtils.copyProperties(existingDiscipline, discipline);
        discipline.setDisciplineId(id);
        discipline.setPeriods(existingDiscipline.getPeriods());
        discipline.setActive(false);
        return disciplineRepository.save(discipline);
    }

    @Transactional
    public DisciplineModel update(UUID id, DisciplineDto params) throws NotFoundException, BadRequestException {
        DisciplineModel existingDiscipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(DisciplineModel.class, "Subject does not exist"));

        var discipline = new DisciplineModel();
        discipline.setDisciplineId(id);
        discipline.setPeriods(existingDiscipline.getPeriods());
        discipline.setPeriods(existingDiscipline.getPeriods());
        discipline.setActive(existingDiscipline.getActive());
        BeanUtils.copyProperties(params, discipline);
        return disciplineRepository.save(discipline);
    }
}
