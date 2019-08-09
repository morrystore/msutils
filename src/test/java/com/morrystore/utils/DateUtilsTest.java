package com.morrystore.utils;


import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateUtilsTest {
    
    @Test
    public void testRange() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DAY_OF_MONTH, -1);
        int d = DateTimeUtils.getDaysBetween(ca.getTime(), new Date());
        System.out.println(d);
    }
}