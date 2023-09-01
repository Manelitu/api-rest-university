package com.api.restuniversity.repositories;

import com.api.restuniversity.models.CourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CourseRepository extends JpaRepository<CourseModel, UUID> {
    Optional<CourseModel> findByName(String name);

    Page<CourseModel> findByActiveTrue(Pageable pageable);
}
