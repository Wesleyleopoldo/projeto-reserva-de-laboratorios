package com.labssyntech.labsSyntech.dto;

import java.util.UUID;

import com.labssyntech.labsSyntech.models.Organization;

public record UserDTO(
    UUID userId, 
    String userName, 
    String email,
    String password,
    boolean isPresident,
    Organization organization
) {
}
