package cn.wulinweb.hadoop.ZooKeeper.creategroup;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.wulinweb.hadoop.ZooKeeper.creategroup.CreateGroup;

public class CreateGroupTest {
	private static String hosts = "192.168.1.137";
	private static String groupName = "zoo";
	
	private CreateGroup createGroup = null;
	
	/**
	 * init
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 * @throws IOException 
	 */
	@Before
	public void init() throws KeeperException, InterruptedException, IOException {
		createGroup = new CreateGroup();
		createGroup.connect(hosts);
	}
	
	@Test
	public void testCreateGroup() throws KeeperException, InterruptedException {
		createGroup.create(groupName);
	}
	
	/**
	 * 销毁资源
	 */
	@After
	public void destroy() {
		try {
			createGroup.close();
			createGroup = null;
			System.gc();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
