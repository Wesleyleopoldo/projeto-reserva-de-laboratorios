package com.labssyntech.labsSyntech.controllers;

import com.labssyntech.labsSyntech.dto.organization.OrganizationDTO;
import com.labssyntech.labsSyntech.requests.organization.OrganizationRequestBody;
import com.labssyntech.labsSyntech.services.OrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    // Endpoint que lista as organizações...
    @GetMapping("/administerW/{userId}")
    @PreAuthorize("@helperUser.isAdminSyntech(#userId)")
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations(@PathVariable UUID userId){
        List<OrganizationDTO> organizations = organizationService.getAllOrganizationService();
        return ResponseEntity.ok(organizations);
    }

    // Endpoint que deleta a organização...
    @DeleteMapping("/{userId}/{organizationId}")
    @PreAuthorize("@helperUser.isPresidentOrAdmin(#userId, #organizationId)")
    public ResponseEntity<String> destroyOrganization(@PathVariable UUID userId, @PathVariable UUID organizationId) {
        return ResponseEntity.ok(organizationService.destroyOrganizationService(organizationId));
    }

    @PostMapping("/{userId}/registerorganization")
    public ResponseEntity<OrganizationDTO> createOrganization(@PathVariable UUID userId, @RequestBody OrganizationRequestBody organizationRequestBody) {
        return ResponseEntity.ok(organizationService.createOrganization(userId, organizationRequestBody.name()));
    }

    @GetMapping("/getorganization/{userId}/{organizationId}")
    @PreAuthorize("@helperUser.isPresidentOrAdmin(#userId, #organizationId)")
    public ResponseEntity<OrganizationDTO> getOrganization(@PathVariable UUID userId,@PathVariable UUID organizationId) {
        return ResponseEntity.ok(organizationService.getOrganizationForUUID(organizationId));
    }

    @PutMapping("/updatename/{userId}/{organizationId}")
    @PreAuthorize("@helperUser.isPresidentOrAdmin(#userId, #organizationId)")
    public ResponseEntity<OrganizationDTO> updateOrganizationName(@PathVariable UUID userId,@PathVariable UUID organizationId, @RequestBody OrganizationRequestBody organizationRequestBody){
        return ResponseEntity.ok(organizationService.updateOrganizationName(organizationId, organizationRequestBody.name()));
    }
}
