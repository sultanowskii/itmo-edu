package ru.itmo.lab5.date;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Default ZonedDateTime format builder
 */
public class DateTimeFormatterBuilder {
    final static String pattern = "dd/MM/yyyy HH:mm:ss VV";

    /**
     * Get default ZonedDateTime formatter
     * @return Default ZonedDateTime formatter
     */
    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
    }

    /**
     * Get default ZonedDateTime formatter pattern
     * @return Default ZonedDateTime formatter pattern
     */
    public static String getDateTimePattern() {
        return pattern;
    }
}
