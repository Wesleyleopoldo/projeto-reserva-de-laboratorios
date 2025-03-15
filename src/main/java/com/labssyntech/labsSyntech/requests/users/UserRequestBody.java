package com.labssyntech.labsSyntech.requests.users;

import java.util.UUID;

public record UserRequestBody(UUID userId, String email, String newEmail, String userName, String Password, boolean isAdmin, UUID organizationId) {
}
