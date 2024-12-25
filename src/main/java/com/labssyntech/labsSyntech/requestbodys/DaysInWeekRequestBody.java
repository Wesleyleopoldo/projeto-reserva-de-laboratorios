package com.labssyntech.labsSyntech.requestbodys;
import java.util.List;
import java.util.UUID;

import com.labssyntech.labsSyntech.utils.DaysInTheWeekEnum;

public record DaysInWeekRequestBody(List<DaysInTheWeekEnum> daysInTheWeekEnums, UUID organizationId) {
    
}
