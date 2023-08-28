package com.api.restuniversity.controllers;

import com.api.restuniversity.dtos.UserDto;
import com.api.restuniversity.models.UserModel;
import com.api.restuniversity.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/users")
public class UserController {
    final private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDto userDto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);

        ZoneId zoneid = ZoneId.of("UTC");
        LocalDateTime now = LocalDateTime.now(zoneid);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(userModel));
    }
}
