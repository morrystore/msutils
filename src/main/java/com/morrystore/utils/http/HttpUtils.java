package com.morrystore.utils.http;

import com.google.common.collect.Lists;
import com.morrystore.utils.thread.Threads;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * HTTP 工具
 * 
 * @author qianhongshan
 *
 */
public class HttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	//############################### 工具配置 - 开始 ######################################
	public static final int SOCKET_TIMEOUT = 10000;
	public static final int CONNECT_TIMEOUT = 10000;
	public static final int CONNECTION_REQUEST_TIMEOUT = 10000;
	//最大连接数
	public static final int MAX_CONNECTION = 200;
	public static final int DEFAULT_MAX_PER_ROUTE = 200;
	//################################ 工具配置 - 结束 ######################################

	private static List<Header> headers = Lists.newArrayList();
	//默认的user-agent
	private static Header defaultUserAgentHeader = new BasicHeader("User-Agent",
	"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.108 Safari/537.36"); 


	private static PoolingHttpClientConnectionManager poolConnManager = null;
	private static CloseableHttpClient httpClient = null;
	private static RequestConfig requestConfig = null;

	static {
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory())
					.register("https", new SSLConnectionSocketFactory(builder.build()))
					.build();
			poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			poolConnManager.setMaxTotal(MAX_CONNECTION);
			poolConnManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
			requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
					.setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build();
			httpClient = getConnection();

			runMonitor();
		} catch (Exception e) {
			e.printStackTrace();
		}

		headers.add(defaultUserAgentHeader);
	}

	public static HttpFullResponse post(String address, Map<String,Object> params, String charset) {
		address = URLEncoder.encode(address.trim());
		HttpPost httpPost = new HttpPost(address);
		headers.stream().forEach(header -> httpPost.addHeader(header));

		HttpFullResponse result =  new HttpFullResponse();

		if(params != null && !params.isEmpty()) {
			List<NameValuePair> list = Lists.newArrayList();
			params.entrySet().stream().forEach(item -> list.add(new BasicNameValuePair(item.getKey(), String.valueOf(item.getValue()) ) ) );
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				logger.error("Invalid charset name -> {}", charset);
			}
		}

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result.setBuff( EntityUtils.toByteArray(entity));
			}
			result.setHeaders(response.getAllHeaders());
			result.setStatusLine(response.getStatusLine());
		} catch (Exception e) {
			logger.error("Error when get url [{}]: {}", address, e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 向远程主机发送HTTP的GET请求
	 * @param address  请求地址
	 * @param proxy    使用代理
	 * @param customHeaders 头部信息
	 * @return 返回内容
	 */
	public static HttpFullResponse get(String address, HttpProxy proxy, Header... customHeaders) {
		HttpFullResponse result =  new HttpFullResponse();

		address = URLEncoder.encode(address.trim());
		HttpGet httpGet = new HttpGet(address);
		if(customHeaders == null || customHeaders.length == 0) {
			headers.stream().forEach(header -> httpGet.addHeader(header));
		} else {
			List<Header> hs =  Lists.newArrayList(customHeaders);
			hs.stream().forEach(header -> httpGet.addHeader(header));
		}

		if(proxy != null && proxy.getProxy() != null) {
			httpGet.setConfig(RequestConfig.custom()
					.setProxy(proxy.getProxy())
					.build());
		}

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result.setBuff( EntityUtils.toByteArray(entity));
			}
			result.setHeaders(response.getAllHeaders());
			result.setStatusLine(response.getStatusLine());
		} catch (Exception e) {
			logger.error("Error when get url [{}]: {}", address, e.getMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}


	/**
	 * 获取包括 Response Header 在内的所有信息
	 * @param address
	 * @param customHeaders
	 * @return
	 */
	public static HttpFullResponse get(String address, Header... customHeaders) {
		return get(address, null, customHeaders);
	}


	private static void runMonitor() {
		Thread thread = new Thread(() -> {
			while(true) {
				Threads.sleep(5000);

				poolConnManager.closeExpiredConnections();
				// 选择关闭 空闲30秒的链接
				poolConnManager.closeIdleConnections(30, TimeUnit.SECONDS);
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	private static CloseableHttpClient getConnection() {

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolConnManager)
				.setDefaultRequestConfig(requestConfig).setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
				.build();

		if (poolConnManager != null && poolConnManager.getTotalStats() != null) {
			System.out.println("now client pool " + poolConnManager.getTotalStats().toString());
		}

		return httpClient;
	}

	private static void printStats() {
		if (poolConnManager != null && poolConnManager.getTotalStats() != null) {
			System.out.println(">>> HTTP client pool " + poolConnManager.getTotalStats().toString());
		}
	}
}