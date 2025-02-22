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

    public OrganizationDTO getOrganizationForUUID(UUID organizationId) {
        Organization organization = findOrganization(organizationId, "Nenhuma organização encontrada...");
        OrganizationDTO organizationDTO = new OrganizationDTO(organization);
        return organizationDTO;
    }

    public OrganizationDTO createOrganization(UUID userId, String name) {
        
        Optional<Organization> newOrganizationOptional = organizationRepository.findByOrganizationName(name);

        if(newOrganizationOptional.isPresent())
        {
            throw new ResourceAlredyExistsException("Essa empresa já foi cadastrada!!!");
        }

        Organization newOrganization = new Organization(name);
        organizationRepository.save(newOrganization);

        userServices.updateUserOrganization(userId, newOrganization);

        userServices.updateIsAdmin(userId, true);

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

    public OrganizationDTO updateOrganizationName(UUID organizationId, String newOrganizationName) {
        Organization organization = findOrganization(organizationId, "Não foi encontrada nenhuma organização com esse nome...");

        organization.setOrganizationName(newOrganizationName);
        organizationRepository.save(organization);

        OrganizationDTO organizationDTO = new OrganizationDTO(organization);

        return organizationDTO;
    }

    Organization findOrganization(UUID organizationId, String exceptionMessege) {
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);

        if(organizationOptional.isEmpty()) {
            throw new NotFoundException(exceptionMessege);
        }

        Organization organization = organizationOptional.get();

        return organization;
    }
}
