package com.yanalexn.expense_limit_api.service;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeConverter {

    public static OffsetDateTime stringToOffsetDateTime(String string) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");
        return ZonedDateTime.parse(string, format).toOffsetDateTime();
    }

    public static String offsetDateTimeToString(OffsetDateTime datetime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");
        return fmt.format(datetime);
    }
}
