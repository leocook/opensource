package cn.wulinweb.hadoop.ZooKeeper.autoregiste;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * 自动注册类
 * @author leo
 */
public class AutoRegister implements Watcher {
	private CountDownLatch countDownLatch = new CountDownLatch(1);
	private static final int SESSION_TIMEOUT = 2000;
	
	protected ZooKeeper zk = null;
	private String hosts = null;
	
	public void process(WatchedEvent event) {
		KeeperState state = event.getState();
		
		if(state == KeeperState.SyncConnected){
			countDownLatch.countDown();
		}
	}
	
	/**
	 * 连接资源
	 * @param hosts
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void connection(String hosts) throws IOException, InterruptedException {
		this.hosts = hosts;
		zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
		countDownLatch.await();
	}
	
	/**
	 * 释放资源
	 * @throws InterruptedException
	 */
	public void close() throws InterruptedException {
		if (null != zk) {
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
