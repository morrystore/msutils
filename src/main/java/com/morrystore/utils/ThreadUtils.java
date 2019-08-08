package com.morrystore.utils;

public class ThreadUtils {

    /**
     * 当前线程休眠
     * @param millis 毫秒数
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}