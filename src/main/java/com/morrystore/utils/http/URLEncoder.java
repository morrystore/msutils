package com.morrystore.utils.http;

import com.google.common.base.Strings;

import java.io.UnsupportedEncodingException;

public class URLEncoder {

    /**
	 * URL编码时默认字符集
	 */
    public static final String DEFAULT_ENCODEING_CHARSET = "utf8";

    /**
	 * 对url进行编码
	 * @param url
	 * @param encoding
	 * @return
	 */
	public static String encode(String url, String encoding) {
		if(Strings.isNullOrEmpty(url)) {
			return url;
		}
		try {
			url = java.net.URLEncoder.encode(url, "utf8").replaceAll("%3A", ":").replaceAll("%2F", "/")
					.replaceAll("%2E", ".").replaceAll("\\+", "%20").replaceAll("%3F", "?").replaceAll("%3D", "=")
					.replaceAll("%26", "&");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
    }
    
    public static String encode(String url) {
		return encode(url, DEFAULT_ENCODEING_CHARSET);
	}
}