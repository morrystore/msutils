package com.morrystore.utils;

import java.util.List;

/**
 * 集合数组工具
 * 
 * @author morry store
 */
public class ArrayUtils {

    /**
     * 判断集合是否为空
     * 
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断集合是否不为空
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }

    /**
     * 判断数组是否为空
     * 
     * @param <T>
     * @param array
     * @return
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断数组是否不为空
     * @param <T>
     * @param array
     * @return
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

}