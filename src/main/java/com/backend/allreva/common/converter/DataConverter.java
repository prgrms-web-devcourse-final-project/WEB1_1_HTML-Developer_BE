package com.backend.allreva.common.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataConverter {
    public static LocalDate convertToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return LocalDate.parse(date, formatter);
    }

    //2024.10.08(금) -> localDate
    public static LocalDate convertToLocalDateFromDateWithDay(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd(EEE)", Locale.KOREAN);
        return LocalDate.parse(date, formatter);
    }

    //localDate -> 2024.10.08(금)
    public static String convertToDateWithDayFromLocalDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd(EEE)", Locale.KOREAN);
        return date.format(formatter);
    }
}
