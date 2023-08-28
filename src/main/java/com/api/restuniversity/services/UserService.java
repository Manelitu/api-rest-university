package com.api.restuniversity.services;

import com.api.restuniversity.dtos.UserDto;
import com.api.restuniversity.enums.Permission;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import com.api.restuniversity.exceptions.NotFoundException;
import com.api.restuniversity.models.UserModel;
import com.api.restuniversity.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

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

    public Page<UserModel> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<UserModel> listById(UUID id) throws NotFoundException {
        Optional<UserModel> existUser = userRepository.findById(id);

        if (existUser.isEmpty()) {
            throw new NotFoundException(UserModel.class, "id");
        }

        return existUser;
    }
}

