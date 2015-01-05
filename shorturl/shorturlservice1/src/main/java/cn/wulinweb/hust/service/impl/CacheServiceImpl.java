package cn.wulinweb.hust.service.impl;

import cn.wulinweb.hust.dao.impl.CacheDaoImpl;

public class CacheServiceImpl {
	private static CacheDaoImpl cacheDaoImpl = new CacheDaoImpl();
	
	/**
	 * 根据短链接去缓存中取长链接
	 * @param sUrl
	 * @return
	 */
	public String getLongByShortUrl(String sUrl) {
		String lUrl = null;
		lUrl = cacheDaoImpl.getLongByShortUrl(sUrl);
		return lUrl;
	}
	
	/**
	 * 把<短地址,长地址>键值对缓存起来
	 * @param sUrl
	 * @param lUrl
	 * @return
	 */
	public boolean setKeyValueToCache(String sUrl, String lUrl){
		boolean b = cacheDaoImpl.setKeyValueToCache(sUrl, lUrl);
		return b;
	}
}
