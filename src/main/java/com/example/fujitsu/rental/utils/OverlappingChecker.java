package com.example.fujitsu.rental.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class OverlappingChecker {

    public static boolean checkIfOverlap(String date1, String duration1, String date2, String duration2){
        DateTime time1Start = DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime(date1);
        DateTime time1End = time1Start.plusMillis(Integer.parseInt(duration1)*86400000);
        DateTime time2Start = DateTimeFormat.forPattern("dd.MM.yyyy").parseDateTime(date2);
        DateTime time2End = time2Start.plusMillis(Integer.parseInt(duration2)*86400000);

        return !(time1End.isBefore(time2Start) || time2End.isBefore(time1Start));
    }
}
