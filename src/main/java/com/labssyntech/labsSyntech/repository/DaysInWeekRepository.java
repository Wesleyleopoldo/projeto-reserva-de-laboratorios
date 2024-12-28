package com.labssyntech.labsSyntech.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.labssyntech.labsSyntech.models.DaysInTheWeek;

@Repository
public interface DaysInWeekRepository extends JpaRepository<DaysInTheWeek, Long>{

    Optional<List<DaysInTheWeek>> findByOrganization_OrganizationId(UUID uuid);

}
