package com.labssyntech.labsSyntech.helper;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.labssyntech.labsSyntech.exception.NotFoundException;
import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.repository.OrganizationRepository;

@Component
public class HelperOrganization {

    @Autowired
    private OrganizationRepository organizationRepository;
    
    public Organization getOrganizationHelper(UUID organizationId) {

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

    public Organization findOrganizationById(UUID organizationId, String exceptionMessege) {
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);

        if(organizationOptional.isEmpty()) {
            throw new NotFoundException(exceptionMessege);
        }

        Organization organization = organizationOptional.get();

        return organization;
    }
}
