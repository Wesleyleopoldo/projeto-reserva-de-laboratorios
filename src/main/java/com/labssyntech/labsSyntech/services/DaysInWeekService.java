package com.labssyntech.labsSyntech.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.labssyntech.labsSyntech.dto.DaysInWeekDTO;
import com.labssyntech.labsSyntech.dto.DaysInWeekDTOSet;
import com.labssyntech.labsSyntech.exception.InternalErrorException;
import com.labssyntech.labsSyntech.exception.ResourceAlredyExistsException;
import com.labssyntech.labsSyntech.helper.HelperOrganization;
import com.labssyntech.labsSyntech.models.DaysInTheWeek;
import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.repository.DaysInWeekRepository;

@Service
public class DaysInWeekService {
    
    @Autowired
    private DaysInWeekRepository daysInWeekRepository;

    @Autowired
    private HelperOrganization helperOrganization; // Serve para usar metodos estaticos que precisam de uma injeção de dependencia direta...

    public List<DaysInWeekDTO> createDaysInWeek(List<String> daysInWeek, UUID organizationId) {
        
        Organization organizationClass = helperOrganization.getOrganizationHelper(organizationId);

        // Verifica se a organização já criou um cronograma...
        List<DaysInTheWeek> organizationCronogram = daysInWeekRepository.findDaysInTheWeekByFkOrganizationId(organizationClass)
        .orElseThrow(() -> new InternalErrorException("Servidor indisponível ;("));

        if(organizationCronogram.isEmpty()){
            List<DaysInTheWeek> daysInWeekList = new ArrayList<DaysInTheWeek>();

            Organization organization = helperOrganization.getOrganizationHelper(organizationId);

            for (int index = 0; index < daysInWeek.size(); index ++) {
                daysInWeekList.add(new DaysInTheWeek(daysInWeek.get(index), organizationClass));
            }

            daysInWeekRepository.saveAll(daysInWeekList);

            List<DaysInWeekDTO> daysInWeekDTOs = daysInWeekList.stream().map(day -> new DaysInWeekDTO(day.getDaysInWeekId(), day.getDayInTheWeek(), organization.getOrganizationId())).toList();
        
            return daysInWeekDTOs;
        } else {
            throw new ResourceAlredyExistsException("Cronograma já criado!!!");
        }
    }

    public List<DaysInWeekDTOSet> getAllInWeekServices(UUID organizationId) {
        Organization organization = helperOrganization.getOrganizationHelper(organizationId);
        Optional<List<DaysInTheWeek>> daysInTheWeeksList = daysInWeekRepository.findDaysInTheWeekByFkOrganizationId(organization);
        List<DaysInWeekDTOSet> daysInWeekDTOs = daysInTheWeeksList.get().stream().map(days -> new DaysInWeekDTOSet(days.getDaysInWeekId(), days.getDayInTheWeek(), organization)).toList();
        return daysInWeekDTOs;
    }
}
