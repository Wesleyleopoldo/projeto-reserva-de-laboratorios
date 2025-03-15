package com.labssyntech.labsSyntech.requests.organization;

import java.util.UUID;

// Requeste que recebe um UUID da organização no body...
public record OrganizationRequestUuid(UUID organizationId) {
}
