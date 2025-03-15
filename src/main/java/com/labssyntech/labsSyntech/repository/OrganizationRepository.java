package com.labssyntech.labsSyntech.repository;

import com.labssyntech.labsSyntech.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

// Repositorio que faz a persistencia dos dados da Organização...
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    // Método que cria uma consulta personalizada para buscar organização pelo nome...
    Optional<Organization> findByOrganizationName(String name);
}
