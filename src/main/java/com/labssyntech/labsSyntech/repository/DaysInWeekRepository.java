package com.labssyntech.labsSyntech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.labssyntech.labsSyntech.models.DaysInTheWeek;

@Repository
public interface DaysInWeekRepository extends JpaRepository<DaysInTheWeek, Long>{
}
