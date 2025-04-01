package com.skylark.lon.api.util;

import java.util.regex.Pattern;

public class DateFormatUtil {
	// 정규 표현식 패턴
    private static final Pattern YEAR_PATTERN = Pattern.compile("^\\d{4}$");
    private static final Pattern MONTH_PATTERN = Pattern.compile("^\\d{6}$");
    private static final Pattern QUARTER_PATTERN = Pattern.compile("^\\d{4}Q[1-4]$");
    private static final Pattern FULL_DATE_PATTERN = Pattern.compile("^\\d{4}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$");

    // 입력된 문자열이 연도 형식인지 확인
    public static boolean isYear(String date) {
    	System.out.println("909090");
    	 return YEAR_PATTERN.matcher(date).matches() || isMonth(date) || isQuarter(date) || isFullDate(date);
    }

    // 입력된 문자열이 월 형식인지 확인
    public static boolean isMonth(String date) {
        return MONTH_PATTERN.matcher(date).matches() && !QUARTER_PATTERN.matcher(date).matches()
            || FULL_DATE_PATTERN.matcher(date).matches();
    }

    // 입력된 문자열이 분기 형 식인지 확인
    public static boolean isQuarter(String date) {
        return QUARTER_PATTERN.matcher(date).matches();
    }

    // 입력된 문자열이 전체 날짜 형식인지 확인
    public static boolean isFullDate(String date) {
        return FULL_DATE_PATTERN.matcher(date).matches();
    }

    // 연도를 반환하는 메서드
    public static String getYear(String date) {
        if (isYear(date)) {
            return date.substring(0, 4); // 앞의 4자리 연도만 반환
        }
        throw new IllegalArgumentException("Invalid year format");
    }

    // 월을 반환하는 메서드
    public static String getMonth(String date) {
    	 if (isMonth(date)) {
    		 System.out.println("123123123123");
    	        return date.substring(4, 6); 
    	 }
    	    throw new IllegalArgumentException("Invalid month format");
    }

    // 분기를 반환하는 메서드
    public static String getQuarter(String date) {
        if (isQuarter(date)) {
            return date.substring(4);
        }
        throw new IllegalArgumentException("Invalid quarter format");
    }

    // 전체 날짜를 반환하는 메서드
    public static String getFullDate(String date) {
        if (isFullDate(date)) {
            return date;
        }
        throw new IllegalArgumentException("Invalid full date format");
    }
   
}
