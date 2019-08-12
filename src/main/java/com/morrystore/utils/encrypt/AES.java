package com.morrystore.utils.encrypt;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES {
   /**
     * 密钥长度
     */
    public static final int DEFAULT_KEY_LENGTH = 16;

    /**
     * 加密
     * @param content
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String key) throws Exception {
        if (key == null || key.length() != DEFAULT_KEY_LENGTH) {
            throw new IllegalArgumentException("Encrypt key length must equal " + DEFAULT_KEY_LENGTH);
        }
        byte[] raw = key.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));

        return new Base64().encodeToString(encrypted);
    }

    /**
     * 解密
     * @param content
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String key) throws Exception {
        // 判断Key是否正确
        if (key == null || key.length() != DEFAULT_KEY_LENGTH) {
            throw new IllegalArgumentException("Decrypt key length must equal " + DEFAULT_KEY_LENGTH);
        }
        byte[] raw = key.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] encrypted1 = new Base64().decode(content);

        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original, "utf-8");
        return originalString;
    }
}