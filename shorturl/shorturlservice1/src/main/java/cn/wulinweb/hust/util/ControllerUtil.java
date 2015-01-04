package cn.wulinweb.hust.util;

import java.io.IOException;





import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.wulinweb.hust.bean.LUrlCondition;

public class ControllerUtil {
	
	/**
	 * 根据json拿到传入的各个参数
	 * @param json
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static LUrlCondition getCondition(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		LUrlCondition condition = objectMapper.readValue(json, LUrlCondition.class);
		
		return condition;
	}
	
	/**
	 * 把将要返回的结果组成json串
	 * @param code
	 * @param sUrl
	 * @return
	 */
	public static String getJson(int code, String sUrl) {
		StringBuffer string = new StringBuffer("{\"code\":#,\"sUrl\":\"#\"}");
		string.replace(string.indexOf("#"), string.indexOf("#")+1, Integer.toString(code));
		if(sUrl != null){
			string.replace(string.indexOf("#"), string.indexOf("#")+1, sUrl);
		}
		
		return string.toString();
	}
}
