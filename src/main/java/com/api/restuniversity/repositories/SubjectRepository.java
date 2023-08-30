package com.api.restuniversity.repositories;

import com.api.restuniversity.models.SubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<SubjectModel, UUID> {
}
