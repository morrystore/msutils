package com.morrystore.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 工具类
 */
public class Strings {

    /**
     * 字符串是否为null或空字符串
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 字符串是否为null或空字符串
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return isEmpty(str);
    }

    /**
     * 字符串是否不为null或空字符串
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 字符串是否不为null或空字符串
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return isNotEmpty(str);
    }

    /**
     * 判断字符串是否包含中文
     * @param str
     * @return
     */
    public static boolean containsChinese(String str) {

        if(isEmpty(str)) {
            return false;
        }

		Pattern p = Pattern.compile("[\\[\u4e00-\u9fa5\\s]+");
		Matcher m = p.matcher(str);
		return m.find();
	}
}