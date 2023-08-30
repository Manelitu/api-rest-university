package com.api.restuniversity.services;

import com.api.restuniversity.dtos.disciplines.DisciplineDto;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import com.api.restuniversity.exceptions.NotFoundException;
import com.api.restuniversity.models.CourseModel;
import com.api.restuniversity.models.DisciplineModel;
import com.api.restuniversity.repositories.DisciplineRepository;
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

    public DisciplineService( DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    @Transactional
    public DisciplineModel create(DisciplineDto params) throws ConflictException, BadRequestException {
        if (disciplineRepository.existsByName(params.getName())) {
            throw new ConflictException("Discipline already exists");
        }

        var subjectModel = new DisciplineModel();
        BeanUtils.copyProperties(params, subjectModel);

        return disciplineRepository.save(subjectModel);
    }

    public List<DisciplineModel> list(Pageable pageable) {
        return disciplineRepository.findAll(pageable).getContent();
    }

    public Optional<DisciplineModel> listById(UUID id) throws NotFoundException {
        Optional<DisciplineModel> existSubject = disciplineRepository.findById(id);

        if (existSubject.isEmpty()) {
            throw new NotFoundException(DisciplineModel.class, "id");
        }

        return existSubject;
    }

    @Transactional
    public DisciplineModel delete(UUID id) throws NotFoundException {
        DisciplineModel existingSubject = disciplineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(DisciplineModel.class, "id"));

        var subjectModel = new DisciplineModel();
        BeanUtils.copyProperties(existingSubject, subjectModel);
        subjectModel.setDisciplineId(id);
        subjectModel.setActive(false);
        return disciplineRepository.save(subjectModel);
    }

    @Transactional
    public DisciplineModel update(UUID id, DisciplineDto params) throws NotFoundException, BadRequestException {
        DisciplineModel existingDiscipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(DisciplineModel.class, "Subject does not exist"));

        var subjectModel = new DisciplineModel();
        BeanUtils.copyProperties(existingDiscipline, subjectModel);
        subjectModel.setDisciplineId(id);
        return disciplineRepository.save(subjectModel);
    }
}
