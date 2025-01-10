package com.labssyntech.labsSyntech.requests;

import java.util.UUID;

public record SignupRequest(
    String userName, 
    String email,
    String password, 
    boolean isPresident,
    UUID organizationId,
    String organizationName
)
{
}
