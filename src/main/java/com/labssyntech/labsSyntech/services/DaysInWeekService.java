package com.labssyntech.labsSyntech.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.labssyntech.labsSyntech.dto.DaysInWeekDTO;
import com.labssyntech.labsSyntech.dto.DaysInWeekDTOSet;
import com.labssyntech.labsSyntech.exception.InternalErrorException;
import com.labssyntech.labsSyntech.exception.ResourceAlredyExistsException;
import com.labssyntech.labsSyntech.models.DaysInTheWeek;
import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.repository.DaysInWeekRepository;
import com.labssyntech.labsSyntech.utils.DaysInTheWeekEnum;
import com.labssyntech.labsSyntech.utils.UtilsDependences;

@Service
public class DaysInWeekService {
    
    @Autowired
    private DaysInWeekRepository daysInWeekRepository;

    @Autowired
    private UtilsDependences utilsDependences; // Serve para usar metodos estaticos que precisam de uma injeção de dependencia direta...

    public List<DaysInWeekDTO> createDaysInWeek(List<DaysInTheWeekEnum> daysInWeek, UUID organizationId) {

        Organization organizationClass = utilsDependences.getOrganization(organizationId);

        List<DaysInTheWeek> organizationUuid = daysInWeekRepository.findDaysInTheWeekByFkOrganizationId(organizationClass)
        .orElseThrow(() -> new InternalErrorException("Servidor indisponível ;("));

        if(organizationUuid.isEmpty()){
            List<DaysInTheWeek> daysInWeekList;

            Organization organization = utilsDependences.getOrganization(organizationId);

            daysInWeekList = daysInWeek.stream().map(dayInWeek -> new DaysInTheWeek(dayInWeek, organization)).toList();

            for (DaysInTheWeek daysInTheWeek : daysInWeekList) {
                daysInWeekRepository.save(daysInTheWeek);
            }

            List<DaysInWeekDTO> daysInWeekDTOs = daysInWeekList.stream().map(day -> new DaysInWeekDTO(day.getDaysInWeekId(), day.getDaysInTheWeekEnum(), organization.getOrganizationId())).toList();
        
            return daysInWeekDTOs;
        } else {
            throw new ResourceAlredyExistsException("Cronograma já criado!!!");
        }
    }

    public List<DaysInWeekDTOSet> getAllInWeekServices(UUID organizationId) {
        Organization organization = utilsDependences.getOrganization(organizationId);
        Optional<List<DaysInTheWeek>> daysInTheWeeksList = daysInWeekRepository.findDaysInTheWeekByFkOrganizationId(organization);
        List<DaysInWeekDTOSet> daysInWeekDTOs = daysInTheWeeksList.get().stream().map(days -> new DaysInWeekDTOSet(days.getDaysInWeekId(), days.getDaysInTheWeekEnum(), organization)).toList();
        return daysInWeekDTOs;
    }
}
