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

import com.labssyntech.labsSyntech.dto.UserDTO;
import com.labssyntech.labsSyntech.requests.PresidentSignupRequest;
import com.labssyntech.labsSyntech.requests.UserRequestUuid;
import com.labssyntech.labsSyntech.requests.UserSignupRequest;
import com.labssyntech.labsSyntech.services.UserServices;

@Controller
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserServices userServices;

    @PostMapping("/signupPresident")
    public ResponseEntity<UserDTO> signupPresident(@RequestBody PresidentSignupRequest signupRequest) {
        return ResponseEntity.ok(userServices.signupPresidentService(signupRequest));
    }

    @PostMapping("/signupUser/{adminId}/{organizationId}")
    @PreAuthorize("@helperUser.isPresidentOrAdmin(#adminId, #organizationId)")
    public ResponseEntity<UserDTO> signupUser(@RequestBody UserSignupRequest signupRequest, @PathVariable UUID adminId,@PathVariable UUID organizationId) {
        return ResponseEntity.ok(userServices.signupUserService(signupRequest, organizationId));
    }

    @GetMapping("/administerW/{userId}")
    @PreAuthorize("@helperUser.isAdminSyntech(#userId)")
    public ResponseEntity<List<UserDTO>> getAllUsers(@PathVariable UUID userId) {
        return ResponseEntity.ok(userServices.getAllUsersServices());
    }

    @DeleteMapping("/destroyuser/{adminId}/{organizationId}")
    @PreAuthorize("@helperUser.isPresidentOrAdmin(#adminId, #organizationId)")
    public ResponseEntity<UserDTO> destroyUserById(@PathVariable UUID adminId, @PathVariable UUID organizationId, @RequestBody UserRequestUuid userRequest) {
        return ResponseEntity.ok(userServices.destroyUser(userRequest.userId()));
    }
}
