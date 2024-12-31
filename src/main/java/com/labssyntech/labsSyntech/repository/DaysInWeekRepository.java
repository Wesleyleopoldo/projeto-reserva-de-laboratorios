package com.labssyntech.labsSyntech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.labssyntech.labsSyntech.models.DaysInTheWeek;
import com.labssyntech.labsSyntech.models.Organization;

@Repository
public interface DaysInWeekRepository extends JpaRepository<DaysInTheWeek, Long>{

    Optional<List<DaysInTheWeek>> findDaysInTheWeekByFkOrganizationId(Organization organization);

}
