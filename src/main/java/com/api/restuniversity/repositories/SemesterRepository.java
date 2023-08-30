package com.api.restuniversity.repositories;

import com.api.restuniversity.models.SemesterModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SemesterRepository extends JpaRepository<SemesterModel, UUID> {
}
