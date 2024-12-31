package com.labssyntech.labsSyntech.requests;
import java.util.List;

import com.labssyntech.labsSyntech.utils.DaysInTheWeekEnum;

public record DaysInWeekRequestBody(List<DaysInTheWeekEnum> daysInTheWeekEnums) {
}
