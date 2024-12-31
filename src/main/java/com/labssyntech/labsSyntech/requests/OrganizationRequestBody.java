package com.labssyntech.labsSyntech.requests;

import java.util.UUID;

public record OrganizationRequestBody (UUID organizationId, String name){
}
