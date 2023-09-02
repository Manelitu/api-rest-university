package com.api.restuniversity.controllers;

import com.api.restuniversity.dtos.users.CreateUserDto;
import com.api.restuniversity.dtos.users.UpdateUserDto;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import com.api.restuniversity.exceptions.NotFoundException;
import com.api.restuniversity.models.UserModel;
import com.api.restuniversity.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    final private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid CreateUserDto createUserDto) throws BadRequestException, ConflictException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(createUserDto));
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> listUsers(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.list(pageable));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserModel>> listUserById(@PathVariable(value = "id")UUID id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.listById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserModel> deleteUser(
            @PathVariable(value = "id") UUID id
    ) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.delete(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid UpdateUserDto updateUserDto
    ) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, updateUserDto));
    }

}
