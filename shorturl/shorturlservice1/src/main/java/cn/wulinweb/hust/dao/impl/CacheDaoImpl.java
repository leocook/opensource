package cn.wulinweb.hust.dao.impl;

import java.util.Date;

import cn.wulinweb.hust.util.ConfigPool;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class CacheDaoImpl {
	private static MemCachedClient mc = null;

	static{
		SockIOPool sockIOPool = SockIOPool.getInstance();
		sockIOPool.setServers(ConfigPool.MEMCACHE_SERVERS);
		sockIOPool.setFailover(ConfigPool.MEMCACHE_FAILOVER);
		sockIOPool.setInitConn(ConfigPool.MEMCACHE_INITCONN);
		sockIOPool.setMaxConn(ConfigPool.MEMCACHE_MAXCONN);
		sockIOPool.setMaintSleep(ConfigPool.MEMCACHE_MAINTSLEEP);
		sockIOPool.setNagle(ConfigPool.MEMCACHE_NAGLE);
		sockIOPool.setSocketTO(ConfigPool.MEMCACHE_SOCKETTO);
		sockIOPool.setAliveCheck(ConfigPool.MEMCACHE_ALIVECHECK);
		sockIOPool.initialize();
		
		mc = new MemCachedClient();
	}
	
	/**
	 * 根据短链接去缓存中取长链接
	 * @param lUrl
	 * @return
	 */
	public String getLongByShortUrl(String sUrl) {
		String res = null;
		
		Object ob = mc.get(sUrl);
		if(ob != null){
			res = (String) ob;
		}
		
		return res;
	}
	
	/**
	 * 把<短地址,长地址>键值对缓存起来
	 * @param sUrl
	 * @param lUrl
	 */
	public boolean setKeyValueToCache(String sUrl, String lUrl){
		boolean b = mc.set(sUrl, lUrl,new Date(System.currentTimeMillis() + ConfigPool.MEMCACHE_CACHE_TIME));
		
		return b;
	}
}
