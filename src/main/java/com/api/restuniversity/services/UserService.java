package com.api.restuniversity.services;

import com.api.restuniversity.dtos.UserDto;
import com.api.restuniversity.enums.Permission;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import com.api.restuniversity.models.UserModel;
import com.api.restuniversity.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class UserService {
    final private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel create(UserDto userDto) throws ConflictException, BadRequestException {
        var userModel = new UserModel();
        Permission permission = Permission.valueOf(userDto.getPermissions());

        BeanUtils.copyProperties(userDto, userModel);

        userModel.setPermissions(permission);

        ZoneId zoneid = ZoneId.of("UTC");
        LocalDateTime now = LocalDateTime.now(zoneid);

        return userRepository.save(userModel);
    }

    public List<UserModel> list() {
        return userRepository.findAll();
    }
}

