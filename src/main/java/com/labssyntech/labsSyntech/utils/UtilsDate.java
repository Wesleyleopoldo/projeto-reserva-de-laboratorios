package com.labssyntech.labsSyntech.utils;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class UtilsDate {

    public static String formaterDate(LocalDate date) {
        String dateString = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        // Obter o dia da semana
        String toUpperCaseFirstLetter = dateString.substring(0, 1).toUpperCase();
        String dayInTheWeek = toUpperCaseFirstLetter + dateString.substring(1);
        return dayInTheWeek;
    }
    
}
