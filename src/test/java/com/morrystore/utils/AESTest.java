package com.morrystore.utils;



import java.util.Map;

import com.google.common.collect.Maps;
import com.morrystore.utils.common.Jsons;
import com.morrystore.utils.encrypt.AES;

import org.junit.Test;

public class AESTest {
    
    @Test
    public void test() {
        try {
            String key = "1234567890123456";
            String str = "{}";
            Map<String,Object> params = Maps.newHashMap();
            params.put("post-data", "we");
            params.put("page-size", "10");
            str = Jsons.toJson(params);

            String enString = AES.encrypt(str, key);
            System.out.println("source string : " + str);
            System.out.println("encrypt string：" + enString);
    
            String deString = AES.decrypt(enString, key);
            System.out.println("decrypt string：" + deString);

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}