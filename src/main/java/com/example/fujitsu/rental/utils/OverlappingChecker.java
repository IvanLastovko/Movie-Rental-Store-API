package com.example.fujitsu.rental.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class OverlappingChecker {

    public static boolean checkIfOverlap(String date1, String duration1, String date2, String duration2){
        DateTime time1_start = DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime(date1);
        DateTime time1_end = time1_start.plusMillis(Integer.parseInt(duration1)*86400000);
        DateTime time2_start = DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime(date2);
        DateTime time2_end = time2_start.plusMillis(Integer.parseInt(duration2)*86400000);

        return !(time1_end.isBefore(time2_start) || time2_end.isBefore(time1_start));
    }
}
