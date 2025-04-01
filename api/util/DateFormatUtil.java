//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.skylark.lon.api.util;

import java.util.regex.Pattern;

public class DateFormatUtil {
    private static final Pattern YEAR_PATTERN = Pattern.compile("^\\d{4}$");
    private static final Pattern MONTH_PATTERN = Pattern.compile("^\\d{6}$");
    private static final Pattern QUARTER_PATTERN = Pattern.compile("^\\d{4}Q[1-4]$");
    private static final Pattern FULL_DATE_PATTERN = Pattern.compile("^\\d{4}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$");

    public DateFormatUtil() {
    }

    public static boolean isYear(String date) {
        System.out.println("909090");
        return YEAR_PATTERN.matcher(date).matches() || isMonth(date) || isQuarter(date) || isFullDate(date);
    }

    public static boolean isMonth(String date) {
        return MONTH_PATTERN.matcher(date).matches() && !QUARTER_PATTERN.matcher(date).matches() || FULL_DATE_PATTERN.matcher(date).matches();
    }

    public static boolean isQuarter(String date) {
        return QUARTER_PATTERN.matcher(date).matches();
    }

    public static boolean isFullDate(String date) {
        return FULL_DATE_PATTERN.matcher(date).matches();
    }

    public static String getYear(String date) {
        if (isYear(date)) {
            return date.substring(0, 4);
        } else {
            throw new IllegalArgumentException("Invalid year format");
        }
    }

    public static String getMonth(String date) {
        if (isMonth(date)) {
            System.out.println("123123123123");
            return date.substring(4, 6);
        } else {
            throw new IllegalArgumentException("Invalid month format");
        }
    }

    public static String getQuarter(String date) {
        if (isQuarter(date)) {
            return date.substring(4);
        } else {
            throw new IllegalArgumentException("Invalid quarter format");
        }
    }

    public static String getFullDate(String date) {
        if (isFullDate(date)) {
            return date;
        } else {
            throw new IllegalArgumentException("Invalid full date format");
        }
    }
}
