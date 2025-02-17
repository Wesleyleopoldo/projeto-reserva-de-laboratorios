package com.labssyntech.labsSyntech.controllers;

import com.labssyntech.labsSyntech.dto.OrganizationDTO;
import com.labssyntech.labsSyntech.requests.OrganizationRequestBody;
import com.labssyntech.labsSyntech.requests.OrganizationRequestUuid;
import com.labssyntech.labsSyntech.services.OrganizationService;
import com.labssyntech.labsSyntech.utils.UtilsDependences;

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

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UtilsDependences utilsDependences;
    // Endpoint que lista as organizações...
    @GetMapping("/administerW/{userId}")
    @PreAuthorize("@utilsDependences.validationUser(#userId)")
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations(@PathVariable UUID userId){
        List<OrganizationDTO> organizations = organizationService.getAllOrganizationService();
        return ResponseEntity.ok(organizations);
    }

    // Endpoint que deleta a organização...
    @DeleteMapping("/{userId}")
    @PreAuthorize("@utilsDependences.isAdmin(#userId)")
    public ResponseEntity<String> destroyOrganization(@PathVariable UUID userId, @RequestBody OrganizationRequestUuid organizationId) {
        return ResponseEntity.ok(organizationService.destroyOrganizationService(organizationId.organizationId()));
    }

    @PostMapping("/{userId}/registerorganization")
    public ResponseEntity<OrganizationDTO> createUser(@PathVariable UUID userId, @RequestBody OrganizationRequestBody organizationRequestBody) {
        return ResponseEntity.ok(organizationService.createOrganization(userId, organizationRequestBody.name()));
    }
}
