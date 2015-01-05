package cn.wulinweb.hust.util;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;

public class DBUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(DBUtil.class);
	
	private static Mongo conn = null;
	
	private DBUtil() {
		// TODO Auto-generated constructor stub
	}

	static{
		init();
	}
	
	public static Mongo getMongo() {
		if(conn == null){
			init();
		}
		
		return conn;
	}
	
	private synchronized static void init(){
		try {
			conn = new Mongo(ConfigPool.MONGO_URL/*路径*/, ConfigPool.MONGO_PORT/*端口*/);
			
			MongoOptions opt = conn.getMongoOptions();
			opt.connectionsPerHost = ConfigPool.POOLSIZE;		// 连接数量
			opt.threadsAllowedToBlockForConnectionMultiplier = ConfigPool.BLOCKSIZE;		// 等待队列长度
			
		} catch (UnknownHostException e) {
			LOGGER.error(e.getMessage());
		} catch (MongoException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
}
