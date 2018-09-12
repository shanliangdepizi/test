package com.jt.common.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.sun.javafx.fxml.builder.URLBuilder;

@Service
public class HttpClientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientService.class);

	@Autowired(required = false)
	private CloseableHttpClient httpClient;

	@Autowired(required = false)
	private RequestConfig requestConfig;

	/**
	 * 
	 * 1.编辑get请求 参数的定义 url 请求数据 get请求数据怎么动态拼接
	 */
	public String get(String url, Map<String, String> params, String charset) {
		String result = null;
		// 判断字符集编码是否为空
		if (StringUtils.isEmpty(charset)) {
			charset = "utf-8";
		}

		// 2.判断参数是否为空， 不为空 拼接url参数
		try {
			if (params != null) {
				// 定义
				URIBuilder builder = new URIBuilder(url);
				/*
				 * for (Map.Entry<String, String> entry : params.entrySet()) {
				 * String paramStr = }
				 */
				for (Map.Entry<String, String> entry : params.entrySet()) {

					builder.addParameter(entry.getKey(), entry.getValue());
				}
				url = builder.build().toString();
			}
			// 定义请求对象
			HttpGet httpGet = new HttpGet(url);
			// 定义连接时长
			httpGet.setConfig(requestConfig);
			// 发送请求
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			// 判断响应结果是否正确
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity(), charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 为了满足客户的需要
	 */
	public String get(String url) {
		return get(url, null, null);
	}

	public String get(String url, Map<String, String> params) {
		return get(url, params, null);
	}

	/**
	 * 实现post请求
	 */
	public String post(String url, Map<String,String> params, String charset) {

		if (charset == null) {
			charset = "UTF-8";
		}
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		
		if (params != null) {
			List<NameValuePair> parameters = new ArrayList<>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				BasicNameValuePair nameValue = new BasicNameValuePair(entry.getKey(), entry.getValue());
				parameters.add(nameValue);
			}
			try {
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, charset);
				httpPost.setEntity(formEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String result = null;
		try {
			CloseableHttpResponse response = httpClient.execute(httpPost);

			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), charset);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		return result;
	}

	public String post(String url) {
		return post(url, null, null);
	}

	public String post(String url, Map<String, String> params) {
		return post(url, params, null);
	}
}
