package com.api.restuniversity.repositories;

import com.api.restuniversity.models.PeriodModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PeriodRepository extends JpaRepository<PeriodModel, UUID> {
}
