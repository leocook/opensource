package cn.wulinweb.hust.service.impl;

import cn.wulinweb.hust.dao.CacheDao;
import cn.wulinweb.hust.dao.impl.CacheDaoImpl;
import cn.wulinweb.hust.service.CacheService;

public class CacheServiceImpl implements CacheService {
	private static CacheDao cacheDao = new CacheDaoImpl();
	
	/* (non-Javadoc)
	 * @see cn.wulinweb.hust.service.impl.CacheService#getLongByShortUrl(java.lang.String)
	 */
	@Override
	public String getLongByShortUrl(String sUrl) {
		String lUrl = null;
		lUrl = cacheDao.getLongByShortUrl(sUrl);
		return lUrl;
	}
	
	/* (non-Javadoc)
	 * @see cn.wulinweb.hust.service.impl.CacheService#setKeyValueToCache(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean setKeyValueToCache(String sUrl, String lUrl){
		boolean b = cacheDao.setKeyValueToCache(sUrl, lUrl);
		return b;
	}
}
