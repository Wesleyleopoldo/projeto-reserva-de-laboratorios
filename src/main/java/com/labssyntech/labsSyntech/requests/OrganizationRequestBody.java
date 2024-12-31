package com.labssyntech.labsSyntech.requests;

import java.util.UUID;

// Requeste que recebe um UUID da organização e o nome da organização no body...
public record OrganizationRequestBody (UUID organizationId, String name){
}
