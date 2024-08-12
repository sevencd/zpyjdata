package com.kj.zpyj.data.util;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author csz
 * @since 2023/11/24
 */
public class DateUtil {
    /**
     * 获取时间戳
     * @return
     */
    public static String getRfc3339Time() {
        // 获取当前时间的 Instant
        Instant now = Instant.now();

        // 将 Instant 转换为 ZonedDateTime，使用东八区时区
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.of("Asia/Shanghai"));

        // 创建一个 DateTimeFormatter，用于输出特定格式的时间字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        // 使用 DateTimeFormatter 格式化 ZonedDateTime
        String formattedDateTime = zonedDateTime.format(formatter);
        return formattedDateTime;
    }

    //将时间戳转换为String类型的时间
    public static String getTime(long timestamp) {
        // 将时间戳转换为Instant对象
        Instant instant = Instant.ofEpochSecond(timestamp);
        // 使用系统默认时区将Instant转换为ZonedDateTime
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        // 使用DateTimeFormatter格式化ZonedDateTime为String类型的时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return zonedDateTime.format(formatter);
    }

    public static long getTimestamp(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取时间戳
     * @param time
     * @return 以秒为单位返回的时间戳
     */
    public static long getTimestamp(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 秒级时间戳
     * @param date
     * @return
     */
    public static long getTimestamp(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将字符串日期转换为 LocalDateTime 对象
        LocalDate dateTime = LocalDate.parse(date, formatter);
        // 将LocalDate转换为时间戳
        ZoneId zoneId = ZoneId.systemDefault(); // 可以使用其他的ZoneId，比如ZoneId.of("UTC")
        Instant instant = dateTime.atStartOfDay(zoneId).toInstant();
        return instant.getEpochSecond();
    }

    public static LocalDate getLocalDate(long timestamp) {
        // 将时间戳转换为Instant对象
        Instant instant = Instant.ofEpochSecond(timestamp);
        // 使用+8时区将Instant转换为ZonedDateTime
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Asia/Shanghai"));
        // 从ZonedDateTime提取LocalDate
        return zonedDateTime.toLocalDate();
    }
    public static LocalDateTime getLocalDateTime(long timestamp) {
        // 将时间戳转换为Instant对象
        Instant instant = Instant.ofEpochSecond(timestamp);
        // 使用+8时区将Instant转换为ZonedDateTime
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Asia/Shanghai"));
        // 从ZonedDateTime提取LocalDate
        return zonedDateTime.toLocalDateTime();
    }
    public static LocalDateTime getUTCTime(String utcTime) {
        return LocalDateTime.parse(utcTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    //获取当前时间字符串
    public static String getNowDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    //获取明天时间字符串

    public static String getTomorrowDate() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return tomorrow.format(formatter);
    }

    public static String getLastMonthStr() {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 获取上一个月的年月对象
        YearMonth lastMonth = YearMonth.from(currentDate.minusMonths(1));

        // 分别获取上个月的年份和月份
//        int lastYear = lastMonth.getYear();
//        int lastMonthValue = lastMonth.getMonthValue();

        // 或者格式化为 "yyyy-MM" 格式
        return DateTimeFormatter.ofPattern("yyyy-MM").format(lastMonth);
    }

    public static YearMonth getLastMonth() {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 获取上一个月的年月对象
        return YearMonth.from(currentDate.minusMonths(1));
    }

    public static String getMonthStr(int monthGap) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 获取上一个月的年月对象
        return YearMonth.from(currentDate.minusMonths(monthGap)).toString();
    }
    public static List<String> get12MonthStr() {
        List<String> monthStrList = new ArrayList<>();
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 12; i++) {
            YearMonth yearMonth = YearMonth.from(currentDate.minusMonths(i));
            monthStrList.add(yearMonth.toString());
        }
        return monthStrList;
    }
    public static Date getStartOfCurrentMonth() {
        LocalDate firstDayOfCurrentMonth = LocalDate.now().withDayOfMonth(1);
        return Date.from(firstDayOfCurrentMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getEndOfCurrentMonth() {
        LocalDate lastDayOfCurrentMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        return Date.from(lastDayOfCurrentMonth.atStartOfDay(ZoneId.systemDefault()).plusHours(23).plusMinutes(59).plusSeconds(59).toInstant());
    }

    public static LocalDateTime getStartOfLastMonth() {
        return LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public static LocalDateTime getEndOfLastMonth() {
        return LocalDateTime.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }

    public static String getCurrentMonthStr() {
        // 获取当前日期
        YearMonth yearMonth = YearMonth.now();
        return yearMonth.toString();
    }
    public static YearMonth getCurrentMonth() {
        // 获取当前日期
        YearMonth yearMonth = YearMonth.now();
        return yearMonth;
    }
    /**
     * 将字符串格式的年月转换为YearMonth对象
     *
     * @param yearMonthStr 字符串格式的年月，如 "2023-10"
     * @return YearMonth对象
     */
    public static YearMonth convertToYearMonth(String yearMonthStr) {
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        // 将字符串转换为YearMonth对象
        return YearMonth.parse(yearMonthStr, formatter);
    }
    /**
     * 检查年月是否正确,年月格式为yyyy-MM
     * 如果不正确，抛出BizException
     *
     * @param yearMonth
     */
}
