package com.votetest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.votetest.utils.DateHelper;

public class DateHelperTest {

    @Test
    public void getStartDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = sdf.parse("21/12/2015 12:00:00");
        Date startDate = DateHelper.getStartDate(d);
        Date endDate = DateHelper.getEndDate(startDate);
        
        Assert.assertEquals(sdf.parse("21/12/2015 11:00:00"), startDate);
        Assert.assertEquals(sdf.parse("22/12/2015 11:00:00"), endDate);
        
        d = sdf.parse("21/12/2015 11:00:01");
        startDate = DateHelper.getStartDate(d);
        endDate = DateHelper.getEndDate(startDate);
        Assert.assertEquals(sdf.parse("21/12/2015 11:00:00"), startDate);
        Assert.assertEquals(sdf.parse("22/12/2015 11:00:00"), endDate);

        d = sdf.parse("21/12/2015 10:00:01");
        startDate = DateHelper.getStartDate(d);
        endDate = DateHelper.getEndDate(startDate);
        Assert.assertEquals(sdf.parse("20/12/2015 11:00:00"), startDate);
        Assert.assertEquals(sdf.parse("21/12/2015 11:00:00"), endDate);
    }
}
