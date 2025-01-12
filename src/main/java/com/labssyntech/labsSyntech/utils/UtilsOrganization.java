package com.labssyntech.labsSyntech.utils;

import java.util.Optional;
import java.util.UUID;

import com.labssyntech.labsSyntech.exception.NotFoundException;
import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.repository.OrganizationRepository;

public class UtilsOrganization {

    private UtilsOrganization(){
        throw new UnsupportedOperationException("Essa classe é utilitária e não pode ser instanciada!!");
    }
    
    static Organization newOrganization(OrganizationRepository repository, UUID organizationId) {

        OrganizationRepository organizationRepository = repository;

        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);

        if(organizationOptional.isEmpty()){
            throw new NotFoundException("Essa empresa não existe na nossa base de dados...");
        }

        return new Organization(
            organizationId,
            organizationOptional
            .get()
            .getOrganizationName()
        );

    }
}
