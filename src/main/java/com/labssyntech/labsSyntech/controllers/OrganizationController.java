package com.labssyntech.labsSyntech.controllers;

import com.labssyntech.labsSyntech.dtos.OrganizationDTO;
import com.labssyntech.labsSyntech.requestbodys.OrganizationRequestBody;
import com.labssyntech.labsSyntech.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    // Endpoint que lista as organizações...
    @GetMapping("/administerW")
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations(){

        List<OrganizationDTO> organizations = organizationService.getAllOrganizationService();

        if(!organizations.isEmpty()) {
            return ResponseEntity.ok(organizations);
        }
        return ResponseEntity.noContent().build();

    }
    // Endpoint que cria organização...
    @PostMapping("/registerorganization")
    public ResponseEntity<OrganizationDTO> createOrganization(@RequestBody OrganizationRequestBody newOrganizationData){
        OrganizationDTO newOrganization = organizationService.createOrganization(newOrganizationData.name());
        return ResponseEntity.ok(newOrganization);
    }
}
