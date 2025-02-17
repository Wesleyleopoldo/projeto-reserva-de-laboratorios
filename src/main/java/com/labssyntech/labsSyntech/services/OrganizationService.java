package com.labssyntech.labsSyntech.services;

import com.labssyntech.labsSyntech.dto.OrganizationDTO;
import com.labssyntech.labsSyntech.exception.InternalErrorException;
import com.labssyntech.labsSyntech.exception.NotFoundException;
import com.labssyntech.labsSyntech.exception.ResourceAlredyExistsException;
import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.repository.OrganizationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserServices userServices;

    public List<OrganizationDTO> getAllOrganizationService()
    {
        List<Organization> listOrganizations = organizationRepository.findAll();

        if (listOrganizations.isEmpty()) {
            throw new NotFoundException("Não organizações a serem listadas");
        }

        List<OrganizationDTO> listOrganizationDTO = listOrganizations.stream().map(organization -> new OrganizationDTO(organization.getOrganizationId(), organization.getOrganizationName())).toList();
        return listOrganizationDTO;
    }

    public OrganizationDTO createOrganization(UUID userId, String name) {
        
        Optional<Organization> newOrganizationOptional = organizationRepository.findByOrganizationName(name);

        if(newOrganizationOptional.isPresent())
        {
            throw new ResourceAlredyExistsException("Essa empresa já foi cadastrada!!!");
        }

        Organization newOrganization = new Organization(name);
        organizationRepository.save(newOrganization);

        userServices.updateUser(userId, newOrganization);

        OrganizationDTO organizationDTO = new OrganizationDTO(newOrganization.getOrganizationId(), newOrganization.getOrganizationName());

        return organizationDTO;
    }

    public String destroyOrganizationService(UUID organizationId) {
        UUID organizationUuid = organizationId;
        Organization organization = organizationRepository
                                    .findById(organizationUuid)
                                    .orElseThrow(() -> new NotFoundException("Organização não encontrada!!!"));

        try {
            organizationRepository.delete(organization);
            return "Sucesso!!!";
        } catch (Exception exception) {
            throw new InternalErrorException("Serviço indisponível temporariamente");
        }
    }
}
