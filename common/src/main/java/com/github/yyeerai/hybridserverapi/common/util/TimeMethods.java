package com.github.yyeerai.hybridserverapi.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeMethods {

    /**
     * 计算两个日期之间的天数
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 日期2 - 日期1
     */
    public static long calculateDaysBetween(LocalDate date1, LocalDate date2) {
        return ChronoUnit.DAYS.between(date1, date2);
    }

    /**
     * 判断日期2是否是日期1的下一天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return true: 日期2是日期1的下一天
     */
    public static boolean isNextDay(LocalDate date1, LocalDate date2) {
        return date2.equals(date1.plusDays(1));
    }

    /**
     * 判断时间戳2是否是时间戳1的下一天
     *
     * @param time1 时间戳1
     * @param time2 时间戳2
     * @return true: 时间戳2是时间戳1的下一天
     */
    public static boolean isNextDay(long time1, long time2) {
        return isNextDay(timestampToLocalDate(time1), timestampToLocalDate(time2));
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return true: 两个日期是同一天
     */
    public static boolean isSameDay(LocalDate date1, LocalDate date2) {
        return date1.isEqual(date2);
    }

    /**
     * 判断两个时间戳是否是同一天
     *
     * @param time1 时间戳1
     * @param time2 时间戳2
     * @return true: 两个时间戳是同一天
     */
    public static boolean isSameDay(long time1, long time2) {
        return timestampToLocalDate(time1).isEqual(timestampToLocalDate(time2));
    }

    /**
     * 将时间戳转换为LocalDate
     *
     * @param timestamp 时间戳
     * @return LocalDate
     */
    public static LocalDate timestampToLocalDate(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 将时间格式化字符串转换为时间戳
     *
     * @param time   时间格式化字符串
     * @param format 时间格式
     * @return 时间戳
     */
    public static long formattedStringToTimestamp(String time, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}