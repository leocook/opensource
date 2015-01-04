package cn.wulinweb.hadoop.ZooKeeper.autoregiste;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.KeeperException.SessionExpiredException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;

import cn.wulinweb.hadoop.ZooKeeper.util.ConnectionWatcher;

/**
 * 自动发现更新的注册信息
 * @author leo
 *
 */
public class AutoDiscovery implements Runnable   {
	private String hosts = null;
	private String path = null;
	public ConfigWatcher watcher = null;
	
	public static  Map<String, String> conf = new HashMap<String, String>();
	
	public AutoDiscovery(String hosts,String path) {
		this.hosts = hosts;
		this.path = path;
		this.init();
	}
	
	private void init() {
		try {
			watcher = new ConfigWatcher(hosts,path, conf);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		} 
	}

	public void run() {
		while(true){
			try {
				watcher.getConfig();
				break;
			} catch (KeeperException.SessionExpiredException e) {
				// TODO： 重新创建、开始一个新的会话
				this.init();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (KeeperException e) {
				e.printStackTrace();
			}
		}
			
		
		while(true){
			try {
				TimeUnit.SECONDS.sleep(1);
			}  catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
