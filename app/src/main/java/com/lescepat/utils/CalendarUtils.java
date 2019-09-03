package com.lescepat.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtils
{
    public static Calendar parseDate (String date, String format) throws ParseException
    {
        Calendar         lCalendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        lCalendar.setTime(formatter.parse(date));
        return lCalendar;
    }

    // get month name. Indonesian locale
    public static String getMonthName (int month)
    {
        String months[] = {"JAN", "FEB", "MAR", "APR", "MEI", "JUN", "JUL", "AUG", "SEPT", "OKT", "NOV", "DES"};
        return months[month];
    }

    // get day name. Indonesian locale
    public static String getDayName (int dayOfWeek)
    {
        String days[] = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
        return days[dayOfWeek];
    }
}
