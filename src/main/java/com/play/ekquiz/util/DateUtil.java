package com.play.ekquiz.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    /**
     * 오늘날짜
     * return : yyyy-mm-dd
     */
    public static String getToDay () throws Exception {
        LocalDate currentDate = LocalDate.now();
        return currentDate.toString();
    }

    /**
     * 현재시간
     * return : 16:35:38.919
     */
    public static String getNowTime () throws Exception {
        LocalTime currentTime = LocalTime.now();
        return currentTime.toString();
    }

    /**
     * 오늘날짜시간 pattern return
     * pattern ex : yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDateTime (String pattern) throws Exception {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        String returnDateTime = currentDateTime.format(dateTimeFormatter);
        return returnDateTime;
    }

    /**
     * day 일 전
     * return : yyyy-mm-dd
     */
    public static String getMinusDay (long day) throws Exception {
        LocalDate currentDate = LocalDate.now().minusDays(day);
        return currentDate.toString();
    }

    /**
     * day 일 후
     * return : yyyy-mm-dd
     */
    public static String getPlusDay (long day) throws Exception {
        LocalDate currentDate = LocalDate.now().plusDays(day);
        return currentDate.toString();
    }

    /**
     * 시간비교
     * 현재시간이 비교하려는 시간 이전이면 true
     */
    public static boolean isBeforeTime (int hour, int minute, int second) throws Exception {
        LocalTime nowTime = LocalTime.now();
        LocalTime baseTime = LocalTime.of(hour,minute,second);
        return nowTime.isBefore(baseTime);
    }

    /**
     * 시간비교
     * 현재시간이 비교하려는 시간 이후이면 true
     */
    public static boolean isAfterTime (int hour, int minute, int second) throws Exception {
        LocalTime nowTime = LocalTime.now();
        LocalTime baseTime = LocalTime.of(hour,minute,second);
        return nowTime.isAfter(baseTime);
    }
}
