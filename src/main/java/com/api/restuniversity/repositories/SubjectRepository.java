package com.api.restuniversity.repositories;

import com.api.restuniversity.models.SubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectModel, UUID> {
}
