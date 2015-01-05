package cn.wulinweb.hust.dao;

public interface ShortUrlDao {

	/**
	 * 把短地址和长地址存入mongo
	 * @param sUrl
	 * @param lUrl
	 */
	public abstract void insertShortUrl(String sUrl, String lUrl);

	/**
	 * 根据短地址查找出长地址
	 * @param sUrl
	 * @return
	 */
	public abstract String getLongUrlByShort(String sUrl);

	/**
	 * 根据长地址查找出短地址
	 * @param lUrl
	 * @return
	 */
	public abstract String getShortByLongUrl(String lUrl);

	/**
	 * 记录日志
	 * @param date
	 * @param ip
	 * @param token
	 * @param url
	 */
	public abstract void logging(String date, String ip, String token,
			String url);

}