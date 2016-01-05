package com.votetest.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper {
    
    /**
     * Calculates start date 
     * @param date - date to be calculated
     * @return - either 11am today or 11am on the previous day 
     */
    public static Date getStartDate(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int toAdd = calendar.get(Calendar.HOUR_OF_DAY) < 11 ? -24 : 0;
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.HOUR, toAdd);
        
        return calendar.getTime();
    }
    
    /**
     * Adds 24h to start date
     * @param startDate
     * @return
     */
    public static Date getEndDate(Date startDate) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.HOUR, 24);
        
        return calendar.getTime();
    }
}
