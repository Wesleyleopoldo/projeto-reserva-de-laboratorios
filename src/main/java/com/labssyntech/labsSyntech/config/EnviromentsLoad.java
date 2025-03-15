package com.labssyntech.labsSyntech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class EnviromentsLoad {
    
    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }
    
}
