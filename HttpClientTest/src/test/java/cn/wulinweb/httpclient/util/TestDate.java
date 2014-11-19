package cn.wulinweb.httpclient.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TestDate {
	
	@Test
	public void testData() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println(format.format(new Date(1413009436063L)));
	}
}
