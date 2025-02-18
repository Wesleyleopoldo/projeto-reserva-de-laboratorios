package com.labssyntech.labsSyntech.requests;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

// Requeste que recebe um UUID da organização e o nome da organização no body...
public record OrganizationRequestBody (UUID userId, UUID organizationId, @JsonAlias("oganizationName") String name){
}
