package cn.wulinweb.httpclient.util;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.wulinweb.httpclient.MyHttpClientUtil;

public class MyHttpClientUtilTest {
	private static HttpClient httpClient;
	
	@BeforeClass
	public static void init() {
		httpClient = new HttpClient();
	}
	
	@AfterClass
	public static void destroy() {
		httpClient = null;
		System.gc();
	}
	
	@Test
	public void sendGetMessageTest() {
		String url = "http://www.baidu.com";
//		String url = "http://localhost:8080/HttpClientWebTest/HttpClientWebTestServlet";
		
		GetMethod getMethod = MyHttpClientUtil.sendGetMessage(url);
		
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			
			if(HttpStatus.SC_OK == statusCode){
				System.out.println("网页内容如下："+getMethod.getResponseBodyAsString());
			}else {
				System.out.println("出错啦！");
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(getMethod!=null){
				getMethod.releaseConnection();
			}
		}
	}
	@Test
	public void postLoginTest() {
		String url = "http://localhost:8080/HttpClientWebTest/HttpClientWebTestServlet";
//		String url = "http://www.xqhuadou.com/userAction_log_ajax";
		
		NameValuePair[] params = {new NameValuePair("name", "aaa"), 
				new NameValuePair("password", "bbb")};
		
		PostMethod postMethod = MyHttpClientUtil.sendPostMessage(url,params);
		
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			
			if(HttpStatus.SC_MOVED_TEMPORARILY == statusCode || HttpStatus.SC_MOVED_PERMANENTLY == statusCode){
				Header locationHeader = postMethod.getResponseHeader("location");
				String location = null;
				
				if(null != locationHeader){
					location = locationHeader.getValue();
					System.out.println("登陆成功！将要跳转到：" + location);
				}else {
					System.out.println("没有可跳转路径");
					System.out.println(postMethod.getResponseBodyAsString());
				}
				
			}else if(HttpStatus.SC_OK==statusCode){
				System.out.println("没有跳转！");
				System.out.println(postMethod.getResponseBodyAsString());
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(postMethod!=null){
				postMethod.releaseConnection();
			}
		}
	}
}
