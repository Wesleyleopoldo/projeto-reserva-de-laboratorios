package com.labssyntech.labsSyntech.utils;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.labssyntech.labsSyntech.config.ConfigService;
import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.repository.OrganizationRepository;
import com.labssyntech.labsSyntech.repository.UserRepository;

@Component
public class UtilsDependences {
    
    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ConfigService configService;

    @Autowired
    private UserRepository userRepository;

    public Organization getOrganization(UUID organizationId){
        return UtilsOrganization.newOrganization(organizationRepository, organizationId);
    }

    // public boolean validationUser(UUID userId) {
    //     return UtilsUser.validationUser(configService, userId);
    // }

    public boolean isPresidentOrAdmin(UUID userId) {
        return UtilsUser.isPresidentOrAdmin(configService, userRepository, userId);
    }

    public boolean isAdminSyntech(UUID userId) {
        return UtilsUser.isAdmin(configService, userId);
    }
}
