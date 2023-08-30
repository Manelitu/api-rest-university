package com.api.restuniversity.repositories;

import com.api.restuniversity.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CourseRepository extends JpaRepository<CourseModel, UUID> {
    CourseModel findByName(String name);
}
