package com.labssyntech.labsSyntech.requests;
import java.util.List;

import com.labssyntech.labsSyntech.utils.DaysInTheWeekEnum;

// Requeste que recebe uma lista de enums no body...
public record DaysInWeekRequestBody(List<DaysInTheWeekEnum> daysInTheWeekEnums) {
}
