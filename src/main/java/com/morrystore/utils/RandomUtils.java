package com.morrystore.utils;

import java.util.Random;

/**
 * 随机工具
 */
public class RandomUtils {

    private static Random random = new Random(System.currentTimeMillis());
    private static final char[] chars = {'0','1','2','3','4','5','6','7','8','9','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static int randomInt() {
        return random.nextInt();
    }

    public static int randomInt(int bound) {
        return random.nextInt(bound);
    }

    public static long randomLong() {
        return random.nextLong();
    }

    /**
     * 随机字符串，包括数字和大小写字母
     * @param len 字符串长度
     * @return
     */
    public static String randomStr(int len) {
        if(len <= 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int index = -1;
        int max = chars.length;
        while(sb.length() < len) {
            index = randomInt(max);
            sb.append(chars[index]);
        }
        return sb.toString();
    }
}