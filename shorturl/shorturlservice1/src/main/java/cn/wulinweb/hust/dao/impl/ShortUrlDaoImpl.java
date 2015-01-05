package cn.wulinweb.hust.dao.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.wulinweb.hust.dao.ShortUrlDao;
import cn.wulinweb.hust.util.DBUtil;
import cn.wulinweb.hust.util.ConfigPool;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class ShortUrlDaoImpl implements ShortUrlDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShortUrlDaoImpl.class);
	
	private Mongo mongo = null;
	private DB dbShortToLong = null;
	private DBCollection collShortToLong = null;
	private DBCollection accessLog = null;

	public ShortUrlDaoImpl() {
		super();
		
		try {
			mongo = DBUtil.getMongo();
			
			dbShortToLong = mongo.getDB(ConfigPool.DB_SHORT_TO_LONG);
			
			dbShortToLong.slaveOk();
			collShortToLong = dbShortToLong.getCollection(ConfigPool.COLL_SHORT_TO_LONG);
			accessLog = dbShortToLong.getCollection(ConfigPool.ACCESS_LOG);
		}  catch (MongoException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.wulinweb.hust.dao.impl.ShortUrlDao#insertShortUrl(java.lang.String, java.lang.String)
	 */
	@Override
	public void insertShortUrl(String sUrl, String lUrl) {
		List<DBObject> list = new LinkedList<DBObject>();
		
		DBObject doc = new BasicDBObject();
		doc.put("sUrl", sUrl);
		doc.put("lUrl", lUrl);
		list.add(doc);
		
		collShortToLong.save(doc);
	}
	
	/* (non-Javadoc)
	 * @see cn.wulinweb.hust.dao.impl.ShortUrlDao#getLongUrlByShort(java.lang.String)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see cn.wulinweb.hust.dao.impl.ShortUrlDao#getShortByLongUrl(java.lang.String)
	 */
	@Override
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
		for(String name : mongo.getDatabaseNames()){
			System.out.println(name);
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.wulinweb.hust.dao.impl.ShortUrlDao#logging(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
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
