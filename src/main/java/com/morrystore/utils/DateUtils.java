package com.morrystore.utils;

import java.sql.Timestamp;

public class DateUtils {
    public static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}
}