package com.labssyntech.labsSyntech.requests.users;

public record UserSignupRequest(
    String userName, 
    String email,
    String password, 
    boolean isPresident
    ) {
    
}
