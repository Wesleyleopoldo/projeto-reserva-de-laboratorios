package com.labssyntech.labsSyntech.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.labssyntech.labsSyntech.dto.DaysInWeekDTO;
import com.labssyntech.labsSyntech.dto.DaysInWeekDTOSet;
import com.labssyntech.labsSyntech.requests.DaysInWeekRequestBody;
import com.labssyntech.labsSyntech.services.DaysInWeekService;

@Controller
@RequestMapping("/daysinweek")
public class DaysInWeekController {
    
    @Autowired
    private DaysInWeekService daysInWeekService;

    @PostMapping("/register/{id}")
    public ResponseEntity<List<DaysInWeekDTO>> createDaysInTheWeek(@PathVariable("id") UUID organizationId, @RequestBody DaysInWeekRequestBody data) {
        
        List<DaysInWeekDTO> daysInWeekDTOs = daysInWeekService.createDaysInWeek(data.daysInTheWeekEnums(), organizationId);
        return ResponseEntity.ok(daysInWeekDTOs);
            
    }

    // Lista todas organizações...
    @GetMapping("/days")
    public ResponseEntity<List<DaysInWeekDTOSet>> getAllDaysInWeek() {

        List<DaysInWeekDTOSet> daysInWeekDTOSets = daysInWeekService.getAllInWeekServices();

        return ResponseEntity.ok(daysInWeekDTOSets);
    }
}
