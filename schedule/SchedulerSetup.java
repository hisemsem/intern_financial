//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.skylark.lon.core.schedule;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class SchedulerSetup {
    public static final String SCHEDULER_TYPE = "";
    public static final String SCHEDULER_PURPOSE = "";
    public static final boolean IS_RUNNING = true;
    private static String targetMethod = "";
    private static String targetClass = "";
    private static String schedulerLanguageType = "kr";
    private static String schedulerStartDate = "";
    private static String schedulerEndDate = "";
    private static String schedulerStartCount = "1";
    private static String schedulerEndCount = "1000";
    private static String schedulerCycleY = "A";
    private static String schedulerCycleM = "M";
    private static String schedulerCycleD = "D";
    private static String schedulerCycleQ = "Q";

    public SchedulerSetup() {
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public static String getSchedulerStartDate() {
        return schedulerStartDate;
    }

    public static String getSchedulerEndDate() {
        return schedulerEndDate;
    }

    public static String getSchedulerStartCount() {
        return schedulerStartCount;
    }

    public static String getSchedulerEndCount() {
        return schedulerEndCount;
    }

    public static String getSchedulerLanguageType() {
        return schedulerLanguageType;
    }

    public static String getSchedulerCycleY() {
        return schedulerCycleY;
    }

    public static String getSchedulerCycleM() {
        return schedulerCycleM;
    }

    public static String getSchedulerCycleD() {
        return schedulerCycleD;
    }

    public static String getSchedulerCycleQ() {
        return schedulerCycleQ;
    }

    public static void setTargetMethod(String method) {
        targetMethod = method;
    }

    public static void setTargetClass(String clazz) {
        targetClass = clazz;
    }

    public static void setSchedulerStartCount(String schedulerStartCount) {
        SchedulerSetup.schedulerStartCount = schedulerStartCount;
    }

    public static void setSchedulerEndCount(String schedulerEndCount) {
        SchedulerSetup.schedulerEndCount = schedulerEndCount;
    }

    public static void setSchedulerLanguageType(String schedulerLanguageType) {
        SchedulerSetup.schedulerLanguageType = schedulerLanguageType;
    }

    public static void setSchedulerCycleY(String schedulerCycleY) {
        SchedulerSetup.schedulerCycleY = schedulerCycleY;
        setDatesBasedOnCycle(schedulerCycleY);
    }

    public static void setSchedulerCycleM(String schedulerCycleM) {
        SchedulerSetup.schedulerCycleM = schedulerCycleM;
        setDatesBasedOnCycle(schedulerCycleM);
    }

    public static void setSchedulerCycleD(String schedulerCycleD) {
        SchedulerSetup.schedulerCycleD = schedulerCycleD;
        setDatesBasedOnCycle(schedulerCycleD);
    }

    public static void setSchedulerCycleQ(String schedulerCycleQ) {
        SchedulerSetup.schedulerCycleQ = schedulerCycleQ;
        setDatesBasedOnCycle(schedulerCycleQ);
    }

    public static void setDatesBasedOnCycle(String cycle) {
        LocalDate now = LocalDate.now();
        switch (cycle) {
            case "A":
                schedulerStartDate = now.minusYears(1L).format(DateTimeFormatter.ofPattern("yyyy"));
                schedulerEndDate = now.format(DateTimeFormatter.ofPattern("yyyy"));
                return;
            case "D":
                schedulerStartDate = now.minusDays(1L).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                schedulerEndDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                return;
            case "M":
                schedulerStartDate = now.minusMonths(1L).format(DateTimeFormatter.ofPattern("yyyyMM"));
                schedulerEndDate = now.minusMonths(1L).format(DateTimeFormatter.ofPattern("yyyyMM"));
                return;
            case "Q":
                int currentQuarter = (now.getMonthValue() - 1) / 3 + 1;
                int lastYearQuarter = (now.minusYears(1L).getMonthValue() - 1) / 3 + 1;
                schedulerStartDate = now.minusYears(1L).format(DateTimeFormatter.ofPattern("yyyy")) + "Q" + lastYearQuarter;
                schedulerEndDate = now.format(DateTimeFormatter.ofPattern("yyyy")) + "Q" + currentQuarter;
                return;
        }

        schedulerStartDate = "";
        schedulerEndDate = "";
    }
}
