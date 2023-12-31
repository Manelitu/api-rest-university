package com.api.restuniversity.services;

import com.api.restuniversity.dtos.users.CreateUserDto;
import com.api.restuniversity.dtos.users.UpdateUserDto;
import com.api.restuniversity.enums.Roles;
import com.api.restuniversity.exceptions.BadRequestException;
import com.api.restuniversity.exceptions.ConflictException;
import com.api.restuniversity.exceptions.NotFoundException;
import com.api.restuniversity.models.UserModel;
import com.api.restuniversity.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
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
        if (this.userRepository.findByEmail(createUserDto.getEmail()) != null) {
            throw new BadRequestException("Email already exists");
        }

        var userModel = new UserModel();

        String encryptedPassword = new BCryptPasswordEncoder().encode(createUserDto.getPassword());
        Roles permission = Roles.valueOf(createUserDto.getRoles());

        BeanUtils.copyProperties(createUserDto, userModel);

        ZoneId zoneid = ZoneId.of("UTC");
        LocalDateTime now = LocalDateTime.now(zoneid);

        userModel.setRoles(permission);
        userModel.setPassword(encryptedPassword);
        userModel.setCreatedAt(now);
        return userRepository.save(userModel);
    }

    public List<UserModel> list(Pageable pageable) {
        return userRepository.findByActiveTrue(pageable).getContent();
    }

    public Optional<UserModel> listById(UUID id) throws NotFoundException {
        Optional<UserModel> existUser = userRepository.findById(id);

        if (existUser.isEmpty()) {
            throw new NotFoundException(UserModel.class, "id");
        }

        return existUser;
    }

    @Transactional
    public UserModel delete(UUID id) throws NotFoundException {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(UserModel.class, "id"));
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(existingUser, userModel);
        userModel.setDeletedAt(now);
        userModel.setPassword(existingUser.getPassword());
        userModel.setActive(false);
        return userRepository.save(userModel);
    }

    @Transactional
    public UserModel update(UUID id, UpdateUserDto updateUserDto) throws NotFoundException {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(UserModel.class, "id"));

        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(updateUserDto, userModel);
        userModel.setId(id);
        userModel.setCreatedAt(existingUser.getCreatedAt());
        userModel.setUpdatedAt(now);
        userModel.setPassword(existingUser.getPassword());
        userModel.setRoles(existingUser.getRoles());

        if (!updateUserDto.getEmail().isEmpty()) {
            userModel.setEmail(updateUserDto.getEmail());
        }

        if (!updateUserDto.getName().isEmpty()) {
            userModel.setName(updateUserDto.getName());
        }

        return userRepository.save(userModel);
    }
}

