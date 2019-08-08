package com.morrystore.utils;

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
}