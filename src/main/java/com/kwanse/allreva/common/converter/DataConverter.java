package com.kwanse.allreva.common.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataConverter {
    public static LocalDate convertToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return LocalDate.parse(date, formatter);
    }
}
