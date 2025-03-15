package com.labssyntech.labsSyntech.dto.users;

import java.util.UUID;

public record UserDTO(
    UUID userId, 
    String userName, 
    String email,
    boolean isPresident
) {
}
