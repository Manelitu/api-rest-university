package com.api.restuniversity.controllers;

import com.api.restuniversity.dtos.courses.CourseDto;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import com.api.restuniversity.exceptions.NotFoundException;
import com.api.restuniversity.models.CourseModel;
import com.api.restuniversity.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    final private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid CourseDto courseDto) throws ConflictException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(courseService.create(courseDto));
    }

    @GetMapping
    public ResponseEntity<List<CourseModel>> list(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "name",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseService.list(pageable));

    }

    @GetMapping("/{name}")
    public ResponseEntity<Optional<CourseModel>> listOne(@PathVariable("name") String name) throws NotFoundException, BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.listById(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CourseModel> delete(
            @PathVariable(value = "id") UUID id
    ) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.delete(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourseModel> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid CourseDto params
    ) throws NotFoundException, BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.update(id, params));
    }
}
