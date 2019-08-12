package com.morrystore.utils.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.morrystore.utils.Strings;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * sha1 工具
 * @author morry
 * @since 0.4
 */
public class SHA {

    /**
     * 获取一个文件的sha1值(可处理大文件)
     * 
     * @return sha1 value
     */
    public static String sha1(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            return DigestUtils.sha1Hex(in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 求一个字符串的sha1值
     * 
     * @param target 字符串
     * @return sha1 value
     */
    public static String sha1(String target) {
        if (Strings.isBlank(target)) {
            return null;
        }
        return DigestUtils.sha1Hex(target);
    }

    /**
     * 比较文件的sha1值,验证是否被篡改
     * 
     * @param file      文件，不能为空
     * @param sha1String sha1值，不能为空
     * @return
     */
    public static boolean checkFileChanged(File file, String sha1String) {
        String sha1 = sha1(file);
        if (Strings.isEmpty(sha1) || Strings.isEmpty(sha1String)) {
            return false;
        }
        return sha1.equalsIgnoreCase(sha1String);
    }
}