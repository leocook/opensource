package cn.wulinweb.hust.dao;

public interface CacheDao {

	/**
	 * 根据短链接去缓存中取长链接
	 * @param lUrl
	 * @return
	 */
	public abstract String getLongByShortUrl(String sUrl);

	/**
	 * 把<短地址,长地址>键值对缓存起来
	 * @param sUrl
	 * @param lUrl
	 */
	public abstract boolean setKeyValueToCache(String sUrl, String lUrl);

}