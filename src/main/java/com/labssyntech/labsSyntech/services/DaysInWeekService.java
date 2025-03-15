package com.labssyntech.labsSyntech.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.labssyntech.labsSyntech.dto.daysinweek.DaysInWeekDTO;
import com.labssyntech.labsSyntech.dto.daysinweek.DaysInWeekDTOSet;
import com.labssyntech.labsSyntech.exception.ResourceAlredyExistsException;
import com.labssyntech.labsSyntech.helper.HelperOrganization;
import com.labssyntech.labsSyntech.models.DaysInTheWeek;
import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.repository.DaysInWeekRepository;
import com.labssyntech.labsSyntech.utils.UtilsDate;

@Service
public class DaysInWeekService {
    
    @Autowired
    private DaysInWeekRepository daysInWeekRepository;

    @Autowired
    private HelperOrganization helperOrganization; // Serve para usar métodos estáticos que precisam de uma injeção de dependência direta

    // Método para criar dias da semana para uma organização
    public List<DaysInWeekDTO> createDaysInWeek(List<String> daysInWeek, UUID organizationId) {
        
        // Obtém a organização pelo ID
        Organization organizationClass = helperOrganization.getOrganizationHelper(organizationId);

        // Verifica se a organização já criou um cronograma
        List<DaysInTheWeek> organizationCronogram = daysInWeekRepository.findDaysInTheWeekByFkOrganizationId(organizationClass);
        
        // Lista de dias da semana formatados...
        List<String> daysInWeekFormatted;
        // Lista de dias da semana...
        List<DaysInTheWeek> daysInWeekList = new ArrayList<>();


        // Se a organização já tiver 7 dias no cronograma, lança uma exceção
        if(organizationCronogram.size() == 7) {
            throw new ResourceAlredyExistsException("Cronograma lotado!! Não existe mais de 7 dias numa semana...");
        }
        // Formata os dias da semana...
        daysInWeekFormatted = UtilsDate.validationDaysInWeek(daysInWeek);

        // Adiciona os dias da semana à lista
        for (String day : daysInWeekFormatted) {
            daysInWeekList.add(new DaysInTheWeek(day, organizationClass));
        }

        // Salva todos os dias da semana no repositório
        daysInWeekRepository.saveAll(daysInWeekList);

        // Converte a lista de DaysInTheWeek para DaysInWeekDTO
        List<DaysInWeekDTO> daysInWeekDTOs = daysInWeekList.stream()
            .map(day -> new DaysInWeekDTO(day.getDaysInWeekId(), day.getDayInTheWeek(), organizationClass.getOrganizationId()))
            .toList();
        
        return daysInWeekDTOs;
    }

    // Método para obter todos os dias da semana de uma organização
    public List<DaysInWeekDTOSet> getAllInWeekServices(UUID organizationId) {
        Organization organization = helperOrganization.getOrganizationHelper(organizationId);
        List<DaysInTheWeek> daysInTheWeeksList = daysInWeekRepository.findDaysInTheWeekByFkOrganizationId(organization);
        List<DaysInWeekDTOSet> daysInWeekDTOs = daysInTheWeeksList.stream()
            .map(days -> new DaysInWeekDTOSet(days.getDaysInWeekId(), days.getDayInTheWeek(), organization))
            .toList();
        return daysInWeekDTOs;
    }

    // Método para deletar dias da semana por id
    public List<DaysInWeekDTOSet> destroyDaysInTheWeekById(UUID organizationId, List<Long> dayInWeekId) {
        Organization organization = helperOrganization.getOrganizationHelper(organizationId);
        List<DaysInTheWeek> daysInTheWeekList = new ArrayList<>();

        // Procura os dias da semana pelo ID e adiciona à lista
        for (Long id : dayInWeekId) {
            Optional<DaysInTheWeek> daysInTheWeekOptional = daysInWeekRepository.findById(id);
            if(daysInTheWeekOptional.isPresent() && daysInTheWeekOptional.get().getOrganization().equals(organization)) {
                daysInTheWeekList.add(daysInTheWeekOptional.get());
            }
        }

        // Se a lista estiver vazia, lança uma exceção
        if(daysInTheWeekList.isEmpty()) {
            throw new ResourceAlredyExistsException("Nenhum dia na semana não encontrado!!");
        }

        // Deleta todos os dias da semana da lista
        daysInWeekRepository.deleteAll(daysInTheWeekList);

        // Converte a lista de DaysInTheWeek para DaysInWeekDTOSet
        List<DaysInWeekDTOSet> daysInWeekDTO = daysInTheWeekList.stream()
            .map(day -> new DaysInWeekDTOSet(day.getDaysInWeekId(), day.getDayInTheWeek(), day.getOrganization()))
            .toList();

        return daysInWeekDTO;
    }
}