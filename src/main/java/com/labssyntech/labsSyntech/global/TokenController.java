package com.labssyntech.labsSyntech.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.labssyntech.labsSyntech.dto.LoginDTO;
import com.labssyntech.labsSyntech.requests.LoginRequest;
import com.labssyntech.labsSyntech.services.UserServices;

@RestController
public class TokenController {
    
    @Autowired
    private UserServices userServices;

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginRequest loginRequest) {
        
        LoginDTO login = userServices.loginService(loginRequest);

        return ResponseEntity.ok(login);
    }
}
