package com.morrystore.utils.http;

import com.morrystore.utils.Strings;

public class URLDecoder {
	/**
	 * 对url进行编码
	 * 
	 * @param url
	 * @param encoding
	 * @return
	 */
	public static String encode(String url, String encoding) {
		if (Strings.isBlank(url)) {
			return url;
		}
		url = URLDecoder.encode(url, "utf8").replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("%2E", ".")
                .replaceAll("\\+", "%20").replaceAll("%3F", "?").replaceAll("%3D", "=").replaceAll("%26", "&");
		return url;
	}
}