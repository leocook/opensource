package cn.wulinweb.hust.dao.impl;

import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.wulinweb.hust.util.ShortUrlUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class ShortUrlDaoImpl {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShortUrlDaoImpl.class);
	
	private Mongo conn = null;
	private DB dbShortToLong = null;
	private DBCollection collShortToLong = null;
	private DBCollection accessLog = null;

	public ShortUrlDaoImpl() {
		super();
		
		try {
			conn = new Mongo(ShortUrlUtil.MONGO_URL, ShortUrlUtil.MONGO_PORT);
			dbShortToLong = conn.getDB(ShortUrlUtil.DB_SHORT_TO_LONG);
			
			dbShortToLong.slaveOk();
			collShortToLong = dbShortToLong.getCollection(ShortUrlUtil.COLL_SHORT_TO_LONG);
			accessLog = dbShortToLong.getCollection(ShortUrlUtil.ACCESS_LOG);
		} catch (UnknownHostException e) {
			LOGGER.error(e.getMessage());
		} catch (MongoException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	/**
	 * 把短地址和长地址存入mongo
	 * @param sUrl
	 * @param lUrl
	 */
	public void insertShortUrl(String sUrl, String lUrl) {
		List<DBObject> list = new LinkedList<DBObject>();
		
		DBObject doc = new BasicDBObject();
		doc.put("sUrl", sUrl);
		doc.put("lUrl", lUrl);
		list.add(doc);
		
		collShortToLong.save(doc);
	}
	
	/**
	 * 根据短地址查找出长地址
	 * @param sUrl
	 * @return
	 */
	public String getLongUrlByShort(String sUrl) {
		String string = null;
		DBObject dbObject = new BasicDBObject("sUrl", sUrl);
		
		DBCursor cursor = collShortToLong.find(dbObject);
		if (cursor.hasNext()) {
			BasicDBObject object =  (BasicDBObject) cursor.next();
			string = object.getString("lUrl");
		}
		
		return string;
	}
	
	/**
	 * 根据长地址查找出短地址
	 * @param lUrl
	 * @return
	 */
	public String getShortByLongUrl(String lUrl) {
		String string = null;
		DBObject dbObject = new BasicDBObject("lUrl", lUrl);
		
		DBCursor cursor = collShortToLong.find(dbObject);
		if (cursor.hasNext()) {
			BasicDBObject object =  (BasicDBObject) cursor.next();
			string = object.getString("sUrl");
		}
		
		return string;
	}
	
	/**
	 * 拿到所以的短链接和长链接
	 * @return
	 */
	protected Map<String, String> getAll() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			DBCursor cursor = collShortToLong.find();
			DBObject dbObject = null;
			while(cursor.hasNext()){
				dbObject = cursor.next();
				LOGGER.debug(dbObject.toString());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		
		
		return map;
	}
	
	protected void getAllDatabase() {
		for(String name:conn.getDatabaseNames()){
			System.out.println(name);
		}
	}
	
	/**
	 * 记录日志
	 * @param date
	 * @param ip
	 * @param token
	 * @param url
	 */
	public void logging(String date, String ip, String token, String url) {
		List<DBObject> list = new LinkedList<DBObject>();
		
		DBObject doc = new BasicDBObject();
		doc.put("date", date);
		doc.put("ip", ip);
		doc.put("token", token);
		doc.put("url", url);
		list.add(doc);
		
		accessLog.save(doc);
	}
}
