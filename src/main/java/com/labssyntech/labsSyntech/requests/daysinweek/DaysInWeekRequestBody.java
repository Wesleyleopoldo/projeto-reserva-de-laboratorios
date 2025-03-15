package com.labssyntech.labsSyntech.requests.daysinweek;
import java.util.List;

// Requeste que recebe uma lista de enums no body...
public record DaysInWeekRequestBody(List<String> daysInTheWeek) {
}
