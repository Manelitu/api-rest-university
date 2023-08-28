package com.api.restuniversity.controllers;

import com.api.restuniversity.dtos.UserDto;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import com.api.restuniversity.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/users")
public class UserController {
    final private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDto userDto) throws BadRequestException, ConflictException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(userDto));
    }
}
