package cn.wulinweb.hadoop.ZooKeeper.listgroup;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ListGroupTest {
	private static final String HOSTS = "192.168.1.137";
	private static final String groupName = "zoo";
	
	private ListGroup listGroup = null;
	
	@Before
	public void init() throws IOException, InterruptedException {
		listGroup = new ListGroup();
		listGroup.connection(HOSTS);
	}
	
	@Test
	public void testList() throws IOException, InterruptedException, KeeperException {
		listGroup.list(groupName);
	}
	
	@After
	public void destroy() throws InterruptedException {
		if(null != listGroup){
			try {
				listGroup.close();
			} catch (InterruptedException e) {
				throw e;
			}finally{
				listGroup = null;
				System.gc();
			}
		}
	}
	
}
