package com.api.restuniversity.repositories;

import com.api.restuniversity.models.DisciplineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DisciplineRepository extends JpaRepository<DisciplineModel, UUID> {
    Boolean existsByName(String name);

    Optional<DisciplineModel> findByName(String name);

}
