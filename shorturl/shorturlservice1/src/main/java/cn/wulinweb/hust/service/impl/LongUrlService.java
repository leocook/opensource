package cn.wulinweb.hust.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import cn.wulinweb.hust.bean.LUrlCondition;
import cn.wulinweb.hust.dao.impl.ShortUrlDaoImpl;
import cn.wulinweb.hust.util.Convertor;

public class LongUrlService {
	private static ShortUrlDaoImpl dao = new ShortUrlDaoImpl();
	private static CacheServiceImpl cacheServiceImpl = new CacheServiceImpl();
	
	/**
	 * 拿到短地址
	 * @param condition
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
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
		cacheServiceImpl.setKeyValueToCache(sUrl, condition.getUrl());
		
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
