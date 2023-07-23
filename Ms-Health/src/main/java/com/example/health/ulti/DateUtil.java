package com.example.health.ulti;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final SimpleDateFormat simpleDateFormatDDMM = new SimpleDateFormat("dd/MM");
    public static final SimpleDateFormat simpleDateFormatDDMMYY = new SimpleDateFormat("ddMMyy");
    public static final SimpleDateFormat simpleDateFormatMMYY = new SimpleDateFormat("MM/yyyy");

    public static Date getMondayThisWeek() {
        LocalDate monday = LocalDate.now();
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        return Date.from(monday.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getSundayThisWeek() {
        LocalDate sunday = LocalDate.now();
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }
        return Date.from(sunday.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
