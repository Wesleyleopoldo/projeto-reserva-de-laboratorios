package com.labssyntech.labsSyntech.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.labssyntech.labsSyntech.dto.users.LoginDTO;
import com.labssyntech.labsSyntech.requests.users.LoginRequest;
import com.labssyntech.labsSyntech.services.UserServices;

@Controller
public class TokenController {
    
    @Autowired
    private UserServices userServices;

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginRequest loginRequest) {
        
        LoginDTO login = userServices.loginService(loginRequest);

        return ResponseEntity.ok(login);
    }
}
