package com.labssyntech.labsSyntech.utils;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.repository.OrganizationRepository;

@Component
public class UtilsDependences {
    
    @Autowired
    private OrganizationRepository organizationRepository;

    public Organization getOrganization(UUID organizationId){
        return UtilsOrganization.newOrganization(organizationRepository, organizationId);
    }
}
