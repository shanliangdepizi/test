package com.jt.httpClient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {

	@Test
	public void test() throws ClientProtocolException, IOException{
		//请求地址
		String url = "http://www.tmooc.cn/course/100011.shtml";
		//请求方式
		HttpGet httpGet = new HttpGet(url);
		//创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//获取响应结果
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		//判断响应状态
		if(httpResponse.getStatusLine().getStatusCode() == 200){
			String data = 
					EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			System.out.println(data);
		}
	}
}
