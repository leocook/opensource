package cn.wulinweb.hust.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import cn.wulinweb.hust.bean.LUrlCondition;
import cn.wulinweb.hust.dao.ShortUrlDao;
import cn.wulinweb.hust.dao.impl.ShortUrlDaoImpl;
import cn.wulinweb.hust.service.CacheService;
import cn.wulinweb.hust.service.LongUrlService;
import cn.wulinweb.hust.util.Convertor;

public class LongUrlServiceImpl implements LongUrlService {
	private static ShortUrlDao dao = new ShortUrlDaoImpl();
	private static CacheService cacheService = new CacheServiceImpl();
	
	/* (non-Javadoc)
	 * @see cn.wulinweb.hust.service.impl.LongUrlService#getShortUrl(cn.wulinweb.hust.bean.LUrlCondition)
	 */
	@Override
	public String getShortUrl(LUrlCondition condition) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//记录访问日志
		this.logging(condition);
		
		//TODO:查看缓存中有没有这个长地址，没有的话再来查看数据库
		
		//查看库中有没有这个地址
		//有的话
		String sUrl = dao.getShortByLongUrl(condition.getUrl());
		
		//没有的话,生成短地址，并入库
		if(sUrl == null){
			//生成短地址
			List<String> list = Convertor.longToShort(condition.getUrl());
			
			sUrl = list.get(1);
			dao.insertShortUrl(sUrl, condition.getUrl());
		}
		cacheService.setKeyValueToCache(sUrl, condition.getUrl());
		
		return sUrl;
	}
	
	/**
	 * 记录日志
	 *  @param condition
	 */
	private void logging(LUrlCondition condition) {
		dao.logging(condition.getDate(),condition.getIp(),condition.getToken(),condition.getUrl());
	}
}
