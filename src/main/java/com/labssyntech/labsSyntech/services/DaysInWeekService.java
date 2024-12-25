package com.labssyntech.labsSyntech.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.labssyntech.labsSyntech.dtos.DaysInWeekDTO;
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
        List<DaysInTheWeek> daysInWeekList;

        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
        Organization organization = new Organization(organizationOptional.get().getOrganizationId(), organizationOptional.get().getOrganizationName());

        daysInWeekList = daysInWeek.stream().map(dayInWeek -> new DaysInTheWeek(dayInWeek, organization)).toList();

        for (DaysInTheWeek daysInTheWeek : daysInWeekList) {
           daysInWeekRepository.save(daysInTheWeek);
        }

        List<DaysInWeekDTO> daysInWeekDTOs = daysInWeekList.stream().map(day -> new DaysInWeekDTO(day.getDaysInWeekId(), day.getDaysInTheWeekEnum(), organization.getOrganizationId())).toList();
    
        return daysInWeekDTOs;
    }
}
