package com.labssyntech.labsSyntech.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.labssyntech.labsSyntech.dtos.DaysInWeekDTO;
import com.labssyntech.labsSyntech.requestbodys.DaysInWeekRequestBody;
import com.labssyntech.labsSyntech.services.DaysInWeekService;

@Controller
@RequestMapping("/register")
public class DaysInWeekController {
    
    @Autowired
    private DaysInWeekService daysInWeekService;

    @PostMapping
    public ResponseEntity<List<DaysInWeekDTO>> createDaysInTheWeek(@RequestBody DaysInWeekRequestBody data) {
        List<DaysInWeekDTO> daysInWeekDTOs = daysInWeekService.createDaysInWeek(data.daysInTheWeekEnums(), data.organizationId());
        return ResponseEntity.ok(daysInWeekDTOs);
    }
}
