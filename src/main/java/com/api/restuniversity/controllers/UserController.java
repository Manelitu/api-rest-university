package com.api.restuniversity.controllers;

import com.api.restuniversity.services.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/users")
public class UserController {
    final private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }
}
