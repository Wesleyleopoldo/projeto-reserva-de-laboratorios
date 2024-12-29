package com.labssyntech.labsSyntech.dto;

import com.labssyntech.labsSyntech.models.Organization;

import java.util.UUID;

public record OrganizationDTO(UUID organizationId, String organizationName) {

    public OrganizationDTO(Organization organization){
        this(
                organization.getOrganizationId(),
                organization.getOrganizationName()
        );
    }
}
