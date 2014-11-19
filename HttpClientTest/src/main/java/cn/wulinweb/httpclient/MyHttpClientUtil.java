package cn.wulinweb.httpclient;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class MyHttpClientUtil {
	public static GetMethod sendGetMessage(String url){
		GetMethod getMethod = null;
		try {
			//创建GetMethod实例
			getMethod = new GetMethod(url);
			
//			getMethod.

			//添加恢复策略
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getMethod;
	}
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static PostMethod sendPostMessage(String url,NameValuePair[] params) {
		PostMethod postMethod = null;
		try {
			//创建一个PostMethod实例
			postMethod = new PostMethod(url);
			
			//将表单值填入postmethod
			postMethod.setRequestBody(params);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postMethod;
	}
}
