package com.morrystore.utils.http;


import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.morrystore.utils.common.Arrays;

import org.apache.http.Header;
import org.apache.http.StatusLine;

import lombok.Data;

@Data
public class HttpFullResponse {
    private byte[] buff;
    private Header[] headers;
    private StatusLine statusLine;

    /**
     * @param buff
     * @param headers
     */

    public HttpFullResponse(byte[] buff, Header[] headers) {
        this.buff = buff;
        this.headers = headers;
    }

    public HttpFullResponse() {
    }

    public boolean isEmpty() {
        return buff == null || buff.length == 0;
    }

    public Header getFirstHeader(String name) {
        return Lists.newArrayList(headers).stream().filter(v -> v.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<Header> getHeaders(String name) {
        return Lists.newArrayList(headers).stream().filter(v -> v.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    public Header getLastHeader(String name) {
        List<Header> hs = getHeaders(name);
        if(Arrays.isEmpty(hs)) {
            return null;
        } else {
            return hs.get(hs.size() - 1);
        }
    }

    public String getString(String charset) {
        if(isEmpty()) {
            return "";
        } else {
            try {
                return new String(buff, charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
			}
        }
    }
}