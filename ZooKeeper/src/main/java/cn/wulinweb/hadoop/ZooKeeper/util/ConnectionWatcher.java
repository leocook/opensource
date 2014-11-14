package cn.wulinweb.hadoop.ZooKeeper.util;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连接的观察者，封装了zk的创建等
 * @author leo
 *
 */
public class ConnectionWatcher implements Watcher {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionWatcher.class);
	
	private static final int SESSION_TIMEOUT = 5000;
	
	protected ZooKeeper zk = null;
	private CountDownLatch countDownLatch = new CountDownLatch(1);

	public void process(WatchedEvent event) {
		KeeperState state = event.getState();
//		LOGGER.debug(state.toString());
		
//		System.out.println(state.toString());
		
		if(state == KeeperState.SyncConnected){
			countDownLatch.countDown();
		}else if (state == KeeperState.AuthFailed) {
			
		}else if (state == KeeperState.Disconnected) {
			
		}else if (state == KeeperState.ConnectedReadOnly) {
			
		}else if (state == KeeperState.SaslAuthenticated) {
			
		}else if (state == KeeperState.Expired) {
			
		}
	}
	
	/**
	 * 连接资源
	 * @param hosts
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void connection(String hosts) throws IOException, InterruptedException {
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
