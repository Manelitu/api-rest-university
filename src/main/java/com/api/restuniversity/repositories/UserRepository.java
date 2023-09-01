package com.api.restuniversity.repositories;

import com.api.restuniversity.models.DisciplineModel;
import com.api.restuniversity.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserDetails findByEmail(String email);
    Boolean existsByEmail(String email);
    Page<UserModel> findByActiveTrue(Pageable pageable);
}
