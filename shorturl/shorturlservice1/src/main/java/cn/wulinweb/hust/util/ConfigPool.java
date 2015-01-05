package cn.wulinweb.hust.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPool {
	
	public static String ACCESS_LOG = null;

	//mongo的地址
	public static String MONGO_URL = null;
	
	//mongo的端口
	public static int MONGO_PORT;

	//存放短地址和长地址对的数据库
	public static String DB_SHORT_TO_LONG;

	//存放短地址和长地址对的
	public static String COLL_SHORT_TO_LONG;

	// 连接数量
	public static int POOLSIZE;

	// 等待队列长度
	public static int BLOCKSIZE;
	
	//memcache的服务器列表
	public static String[] MEMCACHE_SERVERS = null;

	//是否故障转移
	public static boolean MEMCACHE_FAILOVER;

	//初始化的连接数
	public static int MEMCACHE_INITCONN;

	//最大连接数
	public static int MEMCACHE_MAXCONN;

	//维护进程的休眠时间
	public static long MEMCACHE_MAINTSLEEP;

	//是否使用NAGLE算法
	public static boolean MEMCACHE_NAGLE;

	//连接超时的时间
	public static int MEMCACHE_SOCKETTO;

	//检查连接池
	public static boolean MEMCACHE_ALIVECHECK;
	
	//检查连接池
	public static long MEMCACHE_CACHE_TIME;
	
	static{
		Properties config = new Properties();
		InputStream in = ConfigPool.class.getClassLoader().getResourceAsStream("mongo.properties");
		try {
			config.load(in);
			MONGO_URL = config.getProperty("mongourl");
			MONGO_PORT = Integer.parseInt(config.getProperty("mongoport"));
			DB_SHORT_TO_LONG = config.getProperty("dbShortToLong");
			COLL_SHORT_TO_LONG = config.getProperty("collShorToLong");
			ACCESS_LOG = config.getProperty("accessLog");
			POOLSIZE = Integer.parseInt(config.getProperty("poolsize"));
			BLOCKSIZE = Integer.parseInt(config.getProperty("blocksize"));
			
			String servers = config.getProperty("memcache_servers");
			MEMCACHE_SERVERS = servers.split(",");
			
			MEMCACHE_FAILOVER = Boolean.parseBoolean(config.getProperty("memcache_failover"));
			MEMCACHE_INITCONN = Integer.parseInt(config.getProperty("memcache_initconn"));
			MEMCACHE_MAXCONN = Integer.parseInt(config.getProperty("memcache_maxconn"));
			MEMCACHE_MAINTSLEEP = Long.parseLong(config.getProperty("memcache_maintsleep"));
			MEMCACHE_NAGLE = Boolean.parseBoolean(config.getProperty("memcache_nagle"));
			MEMCACHE_SOCKETTO = Integer.parseInt(config.getProperty("memcache_socketto"));
			MEMCACHE_ALIVECHECK = Boolean.parseBoolean(config.getProperty("memcache_alivecheck"));
			MEMCACHE_CACHE_TIME = Long.parseLong(config.getProperty("memcache_cache_time"));
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
