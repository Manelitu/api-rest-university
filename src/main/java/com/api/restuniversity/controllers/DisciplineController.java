package com.api.restuniversity.controllers;

import com.api.restuniversity.dtos.disciplines.DisciplineDto;
import com.api.restuniversity.dtos.disciplines.UpdateDisciplineDto;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import com.api.restuniversity.exceptions.NotFoundException;
import com.api.restuniversity.models.DisciplineModel;

import com.api.restuniversity.services.DisciplineService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/discipline")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DisciplineController {

    final private DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid DisciplineDto params) throws ConflictException, BadRequestException {
        return ResponseEntity.ok(disciplineService.create(params));
    }

    @GetMapping
    public ResponseEntity<List<DisciplineModel>> list(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "name",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {
        return ResponseEntity.ok(disciplineService.list(pageable));

    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineModel> listById(@PathVariable("id") UUID id) throws NotFoundException, BadRequestException {
        return ResponseEntity.ok(disciplineService.listById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DisciplineModel> delete(
            @PathVariable(value = "id") UUID id
    ) throws NotFoundException {
        return ResponseEntity.ok(disciplineService.delete(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid UpdateDisciplineDto params
    ) throws NotFoundException, BadRequestException {
        return ResponseEntity.ok(disciplineService.update(id, params));
    }
}
