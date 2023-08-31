package com.api.restuniversity.controllers;

import com.api.restuniversity.dtos.periods.PeriodDto;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import com.api.restuniversity.exceptions.NotFoundException;
import com.api.restuniversity.models.PeriodModel;
import com.api.restuniversity.services.PeriodService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/period")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PeriodController {

    final private PeriodService periodService;

    public PeriodController(PeriodService periodService) {
        this.periodService = periodService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid PeriodDto params) throws ConflictException, BadRequestException {
        return ResponseEntity.ok(periodService.create(params));
    }

    @GetMapping
    public ResponseEntity<List<PeriodModel>> list(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "period",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {
        return ResponseEntity.ok(periodService.list(pageable));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PeriodModel>> listById(@PathVariable("id") UUID id) throws NotFoundException, BadRequestException {
        return ResponseEntity.ok(periodService.listById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PeriodModel> delete(
            @PathVariable(value = "id") UUID id
    ) throws NotFoundException {
        return ResponseEntity.ok(periodService.delete(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid PeriodDto params
    ) throws NotFoundException, BadRequestException {
        return ResponseEntity.ok(periodService.update(id, params));
    }
}
