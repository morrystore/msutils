package com.morrystore.utils;


import com.morrystore.utils.common.Range;

import org.junit.Test;

public class RangeTest {
    
    @Test
    public void testRange() {
        for(int i : Range.init(1,5)) {
            System.out.println(i);
        }
        
    }
}