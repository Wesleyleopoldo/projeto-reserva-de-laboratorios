package com.labssyntech.labsSyntech.dto;

import java.util.UUID;

import com.labssyntech.labsSyntech.utils.DaysInTheWeekEnum;

public record DaysInWeekDTO(Long daysInWeekId, DaysInTheWeekEnum dayInWeek, UUID organizationId) {
}
