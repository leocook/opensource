package cn.wulinweb.hadoop.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class CreateGroup implements Watcher{
	private static final int SESSION_TIMEOUT = 1000;//会话延时
	
	private ZooKeeper zk = null;
	private CountDownLatch countDownLatch = new CountDownLatch(1);
	

	public void process(WatchedEvent event) {
		if(event.getState() == KeeperState.SyncConnected){
			countDownLatch.countDown();
		}
	}
	
	/**
	 * 创建zk对象
	 * 当客户端连接上zookeeper时会执行process(event)里的countDownLatch.countDown()，计数器的值变为0，则countDownLatch.await()方法返回。
	 * @param hosts
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void connect(String hosts) throws IOException, InterruptedException {
		zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
		countDownLatch.await();
	}
	
	/**
	 * 创建group
	 * 
	 * @param groupName 组名
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void create(String groupName) throws KeeperException, InterruptedException {
		String path = "/" + groupName;
		String createPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE/*允许任何客户端对该znode进行读写*/, CreateMode.PERSISTENT/*持久化的znode*/);
		System.out.println("Created " + createPath);
	}
	
	/**
	 * 关闭zk
	 * @throws InterruptedException
	 */
	public void close() throws InterruptedException {
		if(zk != null){
			try {
				zk.close();
			} catch (InterruptedException e) {
				throw e;
			}finally{
				zk = null;
				System.gc();
			}
		}
	}
	
}
