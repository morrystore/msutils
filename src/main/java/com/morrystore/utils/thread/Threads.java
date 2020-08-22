package com.morrystore.utils.thread;

/**
 * @author hongshan.qian
 * @since 2020/8/22
 */
public class Threads {
    public static void sleep(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
