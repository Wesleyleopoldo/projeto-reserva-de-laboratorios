package com.labssyntech.labsSyntech.services;

import com.labssyntech.labsSyntech.dtos.OrganizationDTO;
import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<OrganizationDTO> getAllOrganizationService()
    {
        List<Organization> listOrganizations = organizationRepository.findAll();

        List<OrganizationDTO> listOrganizationDTO = listOrganizations.stream().map(organization -> new OrganizationDTO(organization.getOrganizationId(), organization.getOrganizationName())).toList();
        return listOrganizationDTO;
    }

    public OrganizationDTO createOrganization(String name) {
        Organization newOrganization = new Organization(name);

        organizationRepository.save(newOrganization);

        OrganizationDTO newOrganizationDTO = new OrganizationDTO(newOrganization.getOrganizationId(), newOrganization.getOrganizationName());

        return newOrganizationDTO;
    }
}
