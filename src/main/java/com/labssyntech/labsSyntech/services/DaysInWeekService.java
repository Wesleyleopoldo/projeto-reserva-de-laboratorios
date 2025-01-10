package com.labssyntech.labsSyntech.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.labssyntech.labsSyntech.dto.DaysInWeekDTO;
import com.labssyntech.labsSyntech.dto.DaysInWeekDTOSet;
import com.labssyntech.labsSyntech.exception.InternalErrorException;
import com.labssyntech.labsSyntech.exception.NotFoundException;
import com.labssyntech.labsSyntech.exception.ResourceAlredyExistsException;
import com.labssyntech.labsSyntech.models.DaysInTheWeek;
import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.repository.DaysInWeekRepository;
import com.labssyntech.labsSyntech.repository.OrganizationRepository;
import com.labssyntech.labsSyntech.utils.DaysInTheWeekEnum;

@Service
public class DaysInWeekService {
    
    @Autowired
    private DaysInWeekRepository daysInWeekRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    public List<DaysInWeekDTO> createDaysInWeek(List<DaysInTheWeekEnum> daysInWeek, UUID organizationId) {

        Optional<Organization> organizationCreated = organizationRepository.findById(organizationId);

        Organization organizationClass = new Organization(
            organizationCreated
            .get()
            .getOrganizationId(), 
            organizationCreated
            .get()
            .getOrganizationName()
        );

        List<DaysInTheWeek> organizationUuid = daysInWeekRepository.findDaysInTheWeekByFkOrganizationId(organizationClass)
        .orElseThrow(() -> new InternalErrorException("Servidor indisponível ;("));

        if(organizationUuid.isEmpty()){
            List<DaysInTheWeek> daysInWeekList;

            Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
            Organization organization = new Organization(organizationOptional.get().getOrganizationId(), organizationOptional.get().getOrganizationName());

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

    public List<DaysInWeekDTOSet> getAllInWeekServices() {
        List<DaysInTheWeek> daysInTheWeeksList = daysInWeekRepository.findAll();
        List<DaysInWeekDTOSet> daysInWeekDTOs = daysInTheWeeksList.stream().map(daysData -> new DaysInWeekDTOSet(daysData.getDaysInWeekId(), daysData.getDaysInTheWeekEnum(), daysData.getOrganization())).toList();
        return daysInWeekDTOs;
    }
}
