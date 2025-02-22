package com.labssyntech.labsSyntech.requests;

public record UserSignupRequest(
    String userName, 
    String email,
    String password, 
    boolean isPresident
    ) {
    
}
