package com.labssyntech.labsSyntech.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.labssyntech.labsSyntech.dto.daysinweek.DaysInWeekDTO;
import com.labssyntech.labsSyntech.dto.daysinweek.DaysInWeekDTOSet;
import com.labssyntech.labsSyntech.requests.daysinweek.DaysInWeekRequestBody;
import com.labssyntech.labsSyntech.requests.daysinweek.DaysInWeekRequestId;
import com.labssyntech.labsSyntech.services.DaysInWeekService;

@Controller
@RequestMapping("/daysinweek")
public class DaysInWeekController {
    
    @Autowired
    private DaysInWeekService daysInWeekService;

    @PostMapping("/register/{adminId}/{organizationId}")
    @PreAuthorize("@helperUser.isPresidentOrAdmin(#adminId, #organizationId)")
    public ResponseEntity<List<DaysInWeekDTO>> createDaysInTheWeek(@PathVariable UUID adminId, @PathVariable UUID organizationId, @RequestBody DaysInWeekRequestBody data) {
        return ResponseEntity.ok(daysInWeekService.createDaysInWeek(data.daysInTheWeek(), organizationId));
    }

    // Lista todos dias na semana...
    @GetMapping("/days/{id}")
    public ResponseEntity<List<DaysInWeekDTOSet>> getAllDaysInWeek(@PathVariable("id") UUID organizationId) {
        List<DaysInWeekDTOSet> daysInWeekDTOSets = daysInWeekService.getAllInWeekServices(organizationId);
        return ResponseEntity.ok(daysInWeekDTOSets);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<List<DaysInWeekDTOSet>> deleteDaysInTheWeek(@PathVariable UUID userId, @PathVariable UUID organizationId, @RequestBody DaysInWeekRequestId daysId) {
        return ResponseEntity.ok(daysInWeekService.destroyDaysInTheWeekById(organizationId, daysId.daysId()));
    }
}
