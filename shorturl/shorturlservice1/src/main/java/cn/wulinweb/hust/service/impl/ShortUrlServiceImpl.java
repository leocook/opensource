package cn.wulinweb.hust.service.impl;

import cn.wulinweb.hust.dao.impl.ShortUrlDaoImpl;

public class ShortUrlServiceImpl {
	private static ShortUrlDaoImpl dao = new ShortUrlDaoImpl();
	private static CacheServiceImpl cacheServiceImpl = new CacheServiceImpl();
	
	public String getLongUrlByShort(String sUrl){
		String res = null;
		
		//查看缓存中有没有，有的话取出来
		res = cacheServiceImpl.getLongByShortUrl(sUrl);
		if(res == null){
			//缓存中没有的话，从数据库中取出来，然后加入缓存
			res = dao.getLongUrlByShort(sUrl);
			//然后加入缓存
			cacheServiceImpl.setKeyValueToCache(sUrl, res);
		}
		
		return res;
	}
}
