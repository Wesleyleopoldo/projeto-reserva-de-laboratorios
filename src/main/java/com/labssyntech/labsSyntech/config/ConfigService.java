package com.labssyntech.labsSyntech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class ConfigService {
    
    private Dotenv dotenv;

    @Autowired
    public ConfigService(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    public String getUserName() {
        return dotenv.get("USER_NAME");
    }

    public String getUserId() {
        return dotenv.get("USER_ID");
    }
    
    public String getUserEmail() {
        return dotenv.get("USER_EMAIL");
    }

    public String getPassword() {
        return dotenv.get("USER_PASSWORD");
    }

    public String getId() {
        return dotenv.get("ORGANIZATION_ID");
    }

    public String getOrganizationName() {
        return dotenv.get("ORGANIZATION_NAME");
    }
}
