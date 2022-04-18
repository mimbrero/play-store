package fp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/y h:m");

    private LocalDateTimeParser() {
        // Non instantiable static class
    }

    /**
     * Parses the given {@link LocalDateTime} as a date in {@code d/M/y} format and time in {@code h:m} format.
     *
     * @param date date to parse, in {@code d/M/y} format
     * @param time time to parse, in {@code h:m} format
     * @return a parsed {@link LocalDateTime}
     */
    public static LocalDateTime parse(String date, String time) {
        return parse(date + " " + time);
    }

    /**
     * Parses the given {@link LocalDateTime} as a date and time with format {@code d/M/y h:m}.
     *
     * @param line {@link LocalDateTime} to parse, in {@code d/M/y h:m} format
     * @return a parsed {@link LocalDateTime}
     */
    public static LocalDateTime parse(String line) {
        return LocalDateTime.parse(line, FORMATTER);
    }
}
