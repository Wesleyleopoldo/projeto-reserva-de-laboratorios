package com.labssyntech.labsSyntech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.labssyntech.labsSyntech.models.DaysInTheWeek;
import com.labssyntech.labsSyntech.models.Organization;

// Repositorio que faz a persistencia dos dados de Dias na Semana...
@Repository
public interface DaysInWeekRepository extends JpaRepository<DaysInTheWeek, Long>{

    // Método que cria uma consulta personalizada para buscar Dias na semana com UUID da organização através do Jpa...
    List<DaysInTheWeek> findDaysInTheWeekByFkOrganizationId(Organization organization);

}
