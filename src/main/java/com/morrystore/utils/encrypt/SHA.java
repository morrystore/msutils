package com.morrystore.utils.encrypt;

import com.google.common.base.Strings;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
        if (Strings.isNullOrEmpty(target)) {
            return null;
        }
        return DigestUtils.sha1Hex(target);
    }

}