package com.example.user.bpomproject.apihelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
    private static String formatDate(String date, String format) {
        String result = "";

        DateFormat old = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date oldDate = old.parse(date);
            DateFormat newFormat = new SimpleDateFormat(format);
            result = newFormat.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getDateMonth(String date){
        return formatDate(date, "MMM");
    }

    public static int getYear(String date){
        return Integer.parseInt(formatDate(date, "yyyy"));
    }

    public static int getMonth(String date){
        return Integer.parseInt(formatDate(date, "MM"));
    }

    public static int getDayOfMonth(String date){
        return Integer.parseInt(formatDate(date, "dd"));
    }

    public static String getDateMonthFull(String date) {
        return formatDate(date, "MMMM");
    }

    public static String getDateDayOfMonth(String date){
        return formatDate(date, "dd");
    }

    public static String getDateDay(String date){
        return formatDate(date, "EEEE");
    }

    public static String getDateLong(String date) {
        return formatDate(date, "dd-MMM-yyyy");
    }

    public static String getDateForDb(String date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String getDateForUser(String date) {
        return formatDate(date, "dd/MM/yyyy");
    }
}
