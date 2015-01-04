package cn.wulinweb.hust.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ShortUrlUtil {
	public static String ACCESS_LOG = null;

	//mongo的地址
	public static String MONGO_URL = null;
	
	//mongo的端口
	public static int MONGO_PORT;

	//存放短地址和长地址对的数据库
	public static String DB_SHORT_TO_LONG;

	//存放短地址和长地址对的
	public static String COLL_SHORT_TO_LONG;
	
	static{
		Properties config = new Properties();
		InputStream in = ShortUrlUtil.class.getClassLoader().getResourceAsStream("mongo.properties");
		try {
			config.load(in);
			MONGO_URL = config.getProperty("mongourl");
			MONGO_PORT = Integer.parseInt(config.getProperty("mongoport"));
			DB_SHORT_TO_LONG = config.getProperty("dbShortToLong");
			COLL_SHORT_TO_LONG = config.getProperty("collShorToLong");
			ACCESS_LOG = config.getProperty("accessLog");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	
	
}
