package com.morrystore.utils;


import org.junit.Assert;
import org.junit.Test;


public class DateUtilsTest {
    
    @Test
    public void test1() {
        int year = DateTimeUtils.year();
        Assert.assertEquals(2019, year);
        int month = DateTimeUtils.month();
        Assert.assertEquals(8, month);

        String str = DateTimeUtils.time2String('=');
        Assert.assertEquals("2019=08=09", str);
    }
}