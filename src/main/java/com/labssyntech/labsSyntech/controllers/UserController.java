package com.labssyntech.labsSyntech.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.labssyntech.labsSyntech.dto.UserDTO;
import com.labssyntech.labsSyntech.requests.SignupRequest;
import com.labssyntech.labsSyntech.services.UserServices;

@Controller
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserServices userServices;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        String createUser = userServices.signupService(signupRequest);
        return ResponseEntity.ok(createUser);
    }

    @GetMapping("/administerW/{userId}")
    @PreAuthorize("@utilsDependences.validationUser(#userId)")
    public ResponseEntity<List<UserDTO>> getAllUsers(@PathVariable UUID userId) {
        return ResponseEntity.ok(userServices.getAllUsersServices());
    }
}
