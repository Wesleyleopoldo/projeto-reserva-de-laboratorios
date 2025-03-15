package com.labssyntech.labsSyntech.utils;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.labssyntech.labsSyntech.exception.InvalidArgumentException;

public class UtilsDate {

    public static String formatterDate(LocalDate date) {
        String dateString = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        return formatterDayString(dateString);
    }
    
    public static List<String> validationDaysInWeek(List<String> daysInTheWeeks) {
        List<String> validyDays = Arrays.asList(
            "Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira",
            "Quinta-feira", "Sexta-feira", "Sábado"
        );

        List<String> itsDayExists = new ArrayList<>(validyDays);

        List<String> validedDay = new ArrayList<>();

        for (String day : daysInTheWeeks) {
            if(!isFormatted(day)){
                validedDay.add(formatterDayString(day));
            } else validedDay.add(day);
        }

        itsDayExists.retainAll(validedDay);

        if(itsDayExists.size() < validedDay.size()) {
            throw new InvalidArgumentException("Argumento Inválido");
        }

        return validedDay; 
    }

    public static boolean isFormatted(String word) {
        return word.substring(0, 1).equals(word.substring(0, 1)
        .toUpperCase()) && word.substring(1).equals(word.substring(1).toLowerCase());
    }

    public static String formatterDayString(String day) {
        return day
            .substring(0, 1)
            .toUpperCase()
            .concat(
                day
                .substring(1)
                .toLowerCase()
            );
    }
}