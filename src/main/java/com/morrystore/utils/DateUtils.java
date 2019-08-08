package com.morrystore.utils;

import java.sql.Timestamp;

public class DateUtils {

	/**
	 * 获取当前时间
	 * @return
	 */
    public static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}
}