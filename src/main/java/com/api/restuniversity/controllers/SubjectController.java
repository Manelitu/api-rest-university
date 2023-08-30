package com.api.restuniversity.controllers;

import com.api.restuniversity.dtos.subjects.CreateSubjectDto;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import com.api.restuniversity.exceptions.NotFoundException;
import com.api.restuniversity.models.SubjectModel;
import com.api.restuniversity.services.SubjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subject")
@PreAuthorize("hasAuthority('ROLE_COORDINATOR')")
public class SubjectController {

    final private SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid CreateSubjectDto params) throws ConflictException, BadRequestException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(subjectService.create(params));
    }

    @GetMapping
    public ResponseEntity<Page<SubjectModel>> list(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "name",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subjectService.list(pageable));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<SubjectModel>> listById(@PathVariable("id") UUID id) throws NotFoundException, BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(subjectService.listById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SubjectModel> delete(
            @PathVariable(value = "id") UUID id
    ) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(subjectService.delete(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid CreateSubjectDto params
    ) throws NotFoundException, BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(subjectService.update(id, params));
    }
}
