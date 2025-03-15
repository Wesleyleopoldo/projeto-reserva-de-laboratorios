package com.labssyntech.labsSyntech.services;

import com.labssyntech.labsSyntech.dto.organization.OrganizationDTO;
import com.labssyntech.labsSyntech.exception.InternalErrorException;
import com.labssyntech.labsSyntech.exception.NotFoundException;
import com.labssyntech.labsSyntech.exception.ResourceAlredyExistsException;
import com.labssyntech.labsSyntech.helper.HelperOrganization;
import com.labssyntech.labsSyntech.helper.HelperUser;
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
    private HelperUser helperUser;

    @Autowired
    private HelperOrganization helperOrganization;

    // Método para obter todas as organizações
    public List<OrganizationDTO> getAllOrganizationService() {
        List<Organization> listOrganizations = organizationRepository.findAll();

        // Verifica se a lista de organizações está vazia
        if (listOrganizations.isEmpty()) {
            throw new NotFoundException("Não organizações a serem listadas");
        }

        // Converte a lista de Organization para OrganizationDTO
        List<OrganizationDTO> listOrganizationDTO = listOrganizations.stream()
            .map(organization -> new OrganizationDTO(organization.getOrganizationId(), organization.getOrganizationName()))
            .toList();
        return listOrganizationDTO;
    }

    // Método para obter uma organização pelo UUID
    public OrganizationDTO getOrganizationForUUID(UUID organizationId) {
        Organization organization = helperOrganization.findOrganizationById(organizationId, "Nenhuma organização encontrada...");
        OrganizationDTO organizationDTO = new OrganizationDTO(organization);
        return organizationDTO;
    }

    // Método para criar uma nova organização
    public OrganizationDTO createOrganization(UUID userId, String name) {
        
        // Verifica se a organização já existe pelo nome
        Optional<Organization> newOrganizationOptional = organizationRepository.findByOrganizationName(name);

        if(newOrganizationOptional.isPresent()) {
            throw new ResourceAlredyExistsException("Essa empresa já foi cadastrada!!!");
        }

        // Cria uma nova organização e salva no repositório
        Organization newOrganization = new Organization(name);
        organizationRepository.save(newOrganization);

        // Define o usuário como presidente da nova organização
        helperUser.setOrganization(userId, newOrganization);
        helperUser.setIsPresident(userId, true);

        OrganizationDTO organizationDTO = new OrganizationDTO(newOrganization.getOrganizationId(), newOrganization.getOrganizationName());

        return organizationDTO;
    }

    // Método para deletar uma organização pelo UUID
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

    // Método para atualizar o nome de uma organização
    public OrganizationDTO updateOrganizationName(UUID organizationId, String newOrganizationName) {
        Organization organization = helperOrganization.findOrganizationById(organizationId, "Não foi encontrada nenhuma organização com esse nome...");

        // Atualiza o nome da organização e salva no repositório
        organization.setOrganizationName(newOrganizationName);
        organizationRepository.save(organization);

        OrganizationDTO organizationDTO = new OrganizationDTO(organization);

        return organizationDTO;
    }
}