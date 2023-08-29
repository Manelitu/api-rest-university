package com.api.restuniversity.services;

import com.api.restuniversity.dtos.users.CreateUserDto;
import com.api.restuniversity.dtos.users.UpdateUserDto;
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
    public UserModel create(CreateUserDto createUserDto) throws ConflictException, BadRequestException {
        var userModel = new UserModel();
        Permission permission = Permission.valueOf(createUserDto.getPermissions());

        BeanUtils.copyProperties(createUserDto, userModel);

        userModel.setPermissions(permission);

        ZoneId zoneid = ZoneId.of("UTC");
        LocalDateTime now = LocalDateTime.now(zoneid);

        userModel.setCreatedAt(now);
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

    @Transactional
    public UserModel update(UUID id, UpdateUserDto updateUserDto) throws NotFoundException, BadRequestException {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(UserModel.class, "id"));

        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(updateUserDto, userModel);
        userModel.setId(id);
        userModel.setCreatedAt(existingUser.getCreatedAt());
        userModel.setUpdatedAt(now);
        userModel.setPassword(existingUser.getPassword());

        if (!updateUserDto.getEmail().isEmpty()) {
            userModel.setEmail(updateUserDto.getEmail());
        }

        if (!updateUserDto.getPermissions().isEmpty()) {
            userModel.setPermissions(Permission.valueOf(updateUserDto.getPermissions()));
        }

        if (!updateUserDto.getName().isEmpty()) {
            userModel.setName(updateUserDto.getName());
        }

        return userModel;
    }
}

