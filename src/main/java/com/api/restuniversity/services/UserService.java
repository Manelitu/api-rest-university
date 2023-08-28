package com.api.restuniversity.services;

import com.api.restuniversity.models.UserModel;
import com.api.restuniversity.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    final private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel create(UserModel userModel) {
        return userRepository.save(userModel);
    }
}

