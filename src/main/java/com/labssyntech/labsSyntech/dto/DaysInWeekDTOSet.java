package com.labssyntech.labsSyntech.dto;

import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.utils.DaysInTheWeekEnum;

public record DaysInWeekDTOSet(Long daysInWeekId, DaysInTheWeekEnum dayInWeek, Organization organization) {
}
