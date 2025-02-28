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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.labssyntech.labsSyntech.dto.UserDTO;
import com.labssyntech.labsSyntech.requests.PresidentSignupRequest;
import com.labssyntech.labsSyntech.requests.UserRequestBody;
import com.labssyntech.labsSyntech.requests.UserRequestUuid;
import com.labssyntech.labsSyntech.requests.UserSignupRequest;
import com.labssyntech.labsSyntech.services.UserServices;

@Controller
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserServices userServices;

    // Endpoint para cadastro de presidente
    @PostMapping("/signupPresident")
    public ResponseEntity<UserDTO> signupPresident(@RequestBody PresidentSignupRequest signupRequest) {
        return ResponseEntity.ok(userServices.signupPresidentService(signupRequest));
    }

    // Endpoint para cadastro de usuário por um administrador ou presidente
    @PostMapping("/signupUser/{adminId}/{organizationId}")
    @PreAuthorize("@helperUser.isPresidentOrAdmin(#adminId, #organizationId)")
    public ResponseEntity<UserDTO> signupUser(@RequestBody UserSignupRequest signupRequest, @PathVariable UUID adminId, @PathVariable UUID organizationId) {
        return ResponseEntity.ok(userServices.signupUserService(signupRequest, organizationId));
    }

    // Endpoint para obter todos os usuários, acessível apenas por administradores
    @GetMapping("/administerW/{userId}")
    @PreAuthorize("@helperUser.isAdminSyntech(#userId)")
    public ResponseEntity<List<UserDTO>> getAllUsers(@PathVariable UUID userId) {
        return ResponseEntity.ok(userServices.getAllUsersServices());
    }

    // Endpoint para deletar um usuário por um administrador ou presidente
    @DeleteMapping("/destroyuser/{adminId}/{organizationId}")
    @PreAuthorize("@helperUser.isPresidentOrAdmin(#adminId, #organizationId)")
    public ResponseEntity<UserDTO> destroyUserById(@PathVariable UUID adminId, @PathVariable UUID organizationId, @RequestBody UserRequestUuid userRequest) {
        return ResponseEntity.ok(userServices.destroyUser(userRequest.userId()));
    }

    // Endpoint para atualizar o nome de usuário
    @PutMapping("/updateusername/{userId}/{organizationId}")
    @PreAuthorize("@helperUser.verifyUserAndOrganization(#userId, #organizationId, #userRequestBody.email)")
    public ResponseEntity<UserDTO> updateUserName(@PathVariable UUID userId, @PathVariable UUID organizationId, @RequestBody UserRequestBody userRequestBody) {
        return ResponseEntity.ok(userServices.updateUserName(userRequestBody.email(), userRequestBody.userName()));
    }

    // Endpoint para atualizar se o usuário é administrador
    @PutMapping("/updateispresident/{userId}/{organizationId}")
    @PreAuthorize("@helperUser.isPresidentOrAdmin(#userId, #organizationId)")
    public ResponseEntity<UserDTO> updateIsAdmin(@PathVariable UUID userId, @PathVariable UUID organizationId, @RequestBody UserRequestBody userRequest) {
        return ResponseEntity.ok(userServices.updateIsAdmin(userRequest.userId(), userRequest.isAdmin()));
    }

    // Endpoint para atualizar a senha do usuário
    @PutMapping("/updateemail/{userId}/{organizationId}")
    @PreAuthorize("@helperUser.verifyUserAndOrganization(#userId, #organizationId, #userRequestBody.email)")
    public ResponseEntity<UserDTO> updatePassword(@PathVariable UUID userId, @PathVariable UUID organizationId, @RequestBody UserRequestBody userRequestBody) {
        return ResponseEntity.ok(userServices.updatePassword(userRequestBody.email(), userRequestBody.Password()));
    }

    // Endpoint para atualizar o email do usuário
    @PutMapping("updatepassword/{userId}/{organizationId}")
    @PreAuthorize("@helperUser.verifyUserAndOrganization(#userId, #organizationId, #userRequestBody.email)")
    public ResponseEntity<UserDTO> updateUserEmail(@PathVariable UUID userId, @PathVariable UUID organizationId, @RequestBody UserRequestBody userRequestBody) {
        return ResponseEntity.ok(userServices.updateUserEmail(userRequestBody.email(), userRequestBody.newEmail()));
    }

    // Endpoint para atualizar a organização do usuário
    @PutMapping("/updateorganization/{userId}/{organizationId}")
    @PreAuthorize("@helperUser.isPresidentOrAdmin(#userId, #organizationId)")
    public ResponseEntity<UserDTO> updateUserOrganization(@PathVariable UUID userId, @PathVariable UUID organizationId, @RequestBody UserRequestBody userRequest) {
        return ResponseEntity.ok(userServices.updateUserOrganization(userRequest.userId(), userRequest.organizationId()));
    }   

}