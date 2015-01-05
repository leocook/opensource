package cn.wulinweb.hust.service.impl;

import cn.wulinweb.hust.dao.ShortUrlDao;
import cn.wulinweb.hust.dao.impl.ShortUrlDaoImpl;
import cn.wulinweb.hust.service.CacheService;
import cn.wulinweb.hust.service.ShortUrlService;

public class ShortUrlServiceImpl implements ShortUrlService {
	private static ShortUrlDao dao = new ShortUrlDaoImpl();
	private static CacheService cacheService = new CacheServiceImpl();
	
	/* (non-Javadoc)
	 * @see cn.wulinweb.hust.service.impl.ShortUrlService#getLongUrlByShort(java.lang.String)
	 */
	@Override
	public String getLongUrlByShort(String sUrl){
		String res = null;
		
		//查看缓存中有没有，有的话取出来
		res = cacheService.getLongByShortUrl(sUrl);
		if(res == null){
			//缓存中没有的话，从数据库中取出来，然后加入缓存
			res = dao.getLongUrlByShort(sUrl);
			//然后加入缓存
			cacheService.setKeyValueToCache(sUrl, res);
		}
		
		return res;
	}
}
