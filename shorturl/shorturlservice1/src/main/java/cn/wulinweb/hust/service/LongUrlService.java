package cn.wulinweb.hust.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import cn.wulinweb.hust.bean.LUrlCondition;

public interface LongUrlService {

	/**
	 * 拿到短地址
	 * @param condition
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public abstract String getShortUrl(LUrlCondition condition)
			throws NoSuchAlgorithmException, UnsupportedEncodingException;

}