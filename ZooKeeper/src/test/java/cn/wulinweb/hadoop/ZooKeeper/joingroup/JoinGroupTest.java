package cn.wulinweb.hadoop.ZooKeeper.joingroup;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.wulinweb.hadoop.ZooKeeper.joingroup.JoinGroup;

public class JoinGroupTest {
//	private static final String HOSTS = "192.168.1.137";
	private static final String HOSTS = "192.168.253.190";
	private static final String groupName = "zoo";
	private static final String memberName = "zoo4";
	
	private JoinGroup joinGroup = null;
	
	@Before
	public void init() throws IOException, InterruptedException {
		joinGroup = new JoinGroup();
		joinGroup.connection(HOSTS);
	}
	
	@Test
	public void testJoin() throws IOException, InterruptedException, KeeperException {
		joinGroup.join(groupName, memberName);
		
//		Thread.sleep(Long.MAX_VALUE);
	}
	
	@After
	public void destroy() throws InterruptedException {
		if(null != joinGroup){
			try {
				joinGroup.close();
			} catch (InterruptedException e) {
				throw e;
			}finally{
				joinGroup = null;
				System.gc();
			}
		}
	}
	
}
