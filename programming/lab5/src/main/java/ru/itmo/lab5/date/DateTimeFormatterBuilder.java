package ru.itmo.lab5.date;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterBuilder {
    final static String pattern = "dd/MM/yyyy HH:mm:ss VV";

    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
    }

    public static String getDateTimePattern() {
        return pattern;
    }
}
