package cn.wulinweb.hust.service.impl;

import cn.wulinweb.hust.dao.impl.ShortUrlDaoImpl;

public class ShortUrlServiceImpl {
	private static ShortUrlDaoImpl dao = new ShortUrlDaoImpl();
	
	public String getLongUrlByShort(String sUrl){
		
		return dao.getLongUrlByShort(sUrl);
	}
}
