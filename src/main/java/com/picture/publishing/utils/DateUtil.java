package com.picture.publishing.utils;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtil {

    private DateUtil() { }

    public static Date getCurrentDate() {
        return new Date(Timestamp.valueOf(getCairoZonedDatetime().toLocalDateTime()).getTime());
    }

    private static ZonedDateTime getCairoZonedDatetime() { return ZonedDateTime.now(ZoneId.of("Africa/Cairo")); }

}
