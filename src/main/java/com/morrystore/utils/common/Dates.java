package com.morrystore.utils.common;

import com.google.common.base.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hongshan.qian
 * @since 2020/8/22
 */
public class Dates {

    /**
     * 解析日期
     * @param dateStr 日期字符串
     * @param formatStr 格式化字符串
     * @return 日期
     */
    public static Date parse(String dateStr, String formatStr) {
        if(Strings.isNullOrEmpty(dateStr) || Strings.isNullOrEmpty(formatStr)) {
            return null;
        } else {
            try {
                return new SimpleDateFormat(formatStr).parse(dateStr);
            } catch (ParseException e) {
                return null;
            }
        }
    }
}
