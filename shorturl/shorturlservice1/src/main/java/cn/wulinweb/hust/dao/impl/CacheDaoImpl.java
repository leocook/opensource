package cn.wulinweb.hust.dao.impl;

import java.util.Date;

import cn.wulinweb.hust.dao.CacheDao;
import cn.wulinweb.hust.util.ConfigPool;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class CacheDaoImpl implements CacheDao {
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
	
	/* (non-Javadoc)
	 * @see cn.wulinweb.hust.dao.impl.CacheDao#getLongByShortUrl(java.lang.String)
	 */
	@Override
	public String getLongByShortUrl(String sUrl) {
		String res = null;
		
		Object ob = mc.get(sUrl);
		if(ob != null){
			res = (String) ob;
		}
		
		return res;
	}
	
	/* (non-Javadoc)
	 * @see cn.wulinweb.hust.dao.impl.CacheDao#setKeyValueToCache(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean setKeyValueToCache(String sUrl, String lUrl){
		boolean b = mc.set(sUrl, lUrl,new Date(System.currentTimeMillis() + ConfigPool.MEMCACHE_CACHE_TIME));
		
		return b;
	}
}
