package com.morrystore.utils.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.morrystore.utils.Strings;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * md5 工具
 * @author morry
 * @since 0.4
 */
public class MD5 {

    /**
     * 获取一个文件的md5值(可处理大文件)
     * 
     * @return md5 value
     */
    public static String md5(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            return DigestUtils.md5Hex(in);
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
     * 求一个字符串的md5值
     * 
     * @param target 字符串
     * @return md5 value
     */
    public static String md5(String target) {
        if (Strings.isBlank(target)) {
            return null;
        }
        return DigestUtils.md5Hex(target);
    }

    /**
     * 比较文件的MD5值,验证是否被篡改
     * 
     * @param file      文件，不能为空
     * @param md5String md5值，不能为空
     * @return
     */
    public static boolean checkFileChanged(File file, String md5String) {
        String fmd5 = md5(file);
        if (Strings.isEmpty(fmd5) || Strings.isEmpty(md5String)) {
            return false;
        }
        return fmd5.equalsIgnoreCase(md5String);
    }
}