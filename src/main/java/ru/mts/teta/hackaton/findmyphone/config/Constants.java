package ru.mts.teta.hackaton.findmyphone.config;

import java.time.LocalDateTime;

public class Constants {
    public static final LocalDateTime minTime = LocalDateTime.of(0, 1, 1, 0, 0, 0, 1);
    public static final LocalDateTime maxTime = LocalDateTime.of(9999, 12, 31, 23, 59, 59, 59);
    public static final String timeFormat = "yyyy-MM-dd'T'HH:mm:ss";
}
