package com.morrystore.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

	public static final char DEFAULT_TIME_DELIMETER = ':';
	public static final char DEFAULT_DATE_DELIMETER = '-';

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 当前年份
	 * 
	 * @return
	 */
	public static int year() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 当前月份
	 * 
	 * @return
	 */
	public static int month() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}

	/**
	 * 当前几号
	 * 
	 * @return
	 */
	public static int day() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 当前小时
	 * 
	 * @return
	 */
	public static int hour() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 当前分钟
	 * 
	 * @return
	 */
	public static int minute() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}

	/**
	 * 当前秒
	 * 
	 * @return
	 */
	public static int second() {
		return Calendar.getInstance().get(Calendar.SECOND);
	}

	public static String date2String(char delimeter) {
		String pattern = "yyyy-MM-dd".replaceAll("-", String.valueOf(delimeter));
		return new SimpleDateFormat(pattern).format(new Date());
	}

	public static String date2String() {
		return time2String(DEFAULT_DATE_DELIMETER);
	}

	public static String time2String(char delimeter) {
		String pattern = "HH:mm:ss".replaceAll(":", String.valueOf(delimeter));
		return new SimpleDateFormat(pattern).format(new Date());
	}

	public static String time2String() {
		return time2String(DEFAULT_TIME_DELIMETER);
	}

	public static String asString(char dateDelimeter, char timeDelimeter) {
		StringBuffer pattern = new StringBuffer();
		pattern.append("yyyy").append(dateDelimeter).append("MM").append(dateDelimeter).append("dd")
			.append(" HH").append(timeDelimeter).append("mm").append(timeDelimeter).append("ss");
		return new SimpleDateFormat(pattern.toString()).format(new Date());
	}

	public static String asString() {
		return asString(DEFAULT_DATE_DELIMETER, DEFAULT_TIME_DELIMETER);
	}

	/**
	 * 两个日期相减得到的天数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getDaysBetween(Date beginDate, Date endDate) {
		if (beginDate == null || endDate == null) {
			throw new IllegalArgumentException("beginDate and endDate must not be null.");
		}
		long diff = getMillisBetween(beginDate, endDate) / (1000 * 60 * 60 * 24);
		int days = new Long(diff).intValue();
		return days;
	}

	/**
	 * 两个日期相减得到的毫秒数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long getMillisBetween(Date beginDate, Date endDate) {
		if (beginDate == null || endDate == null) {
			throw new IllegalArgumentException("beginDate and endDate must not be null.");
		}
		long date1ms = beginDate.getTime();
		long date2ms = endDate.getTime();
		return date2ms - date1ms;
	}

	/**
	 * 获取两个日期中的最大日期
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Date max(Date beginDate, Date endDate) {
		if (beginDate == null) {
			return endDate;
		}
		if (endDate == null) {
			return beginDate;
		}
		if (beginDate.after(endDate)) {
			return beginDate;
		}
		return endDate;
	}

	/**
	 * 获取两个日期中的最小日期
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Date min(Date beginDate, Date endDate) {
		if (beginDate == null) {
			return endDate;
		}
		if (endDate == null) {
			return beginDate;
		}
		if (beginDate.after(endDate)) {
			return endDate;
		}
		return beginDate;
	}

}