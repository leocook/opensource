package cn.wulinweb.hust.dao.impl;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import cn.wulinweb.hust.util.ConfigPool;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBConnector;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class MongoInJavaTest {
	public void test1() throws UnknownHostException {
		List<ServerAddress> list = new LinkedList<ServerAddress>();
		list.add(new ServerAddress("10.0.1.44", 27101));
		list.add(new ServerAddress("10.0.1.44", 27102));
		list.add(new ServerAddress("10.0.1.44", 27103));
		
		Mongo mongo = new Mongo(list);
		
		DB db = mongo.getDB(ConfigPool.DB_SHORT_TO_LONG);
		
		DBCollection collection = db.getCollection(ConfigPool.COLL_SHORT_TO_LONG);
		
		
	}
}
