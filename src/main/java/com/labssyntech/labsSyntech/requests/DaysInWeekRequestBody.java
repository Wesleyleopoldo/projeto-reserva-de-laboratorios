package com.labssyntech.labsSyntech.requests;
import java.util.List;

// Requeste que recebe uma lista de enums no body...
public record DaysInWeekRequestBody(List<String> daysInTheWeek) {
}
