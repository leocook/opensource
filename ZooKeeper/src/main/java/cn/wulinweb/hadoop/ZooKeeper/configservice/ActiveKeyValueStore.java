package cn.wulinweb.hadoop.ZooKeeper.configservice;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import cn.wulinweb.hadoop.ZooKeeper.util.ConnectionWatcher;

public class ActiveKeyValueStore extends ConnectionWatcher {
	private static final Charset CHARSET = Charset.forName("UTF-8");
	private static final int MAXRETRIES = 10;
	private static final int RETRY_PERIOD_SECONDS = 2;
	
	public void write(String path, String value) throws KeeperException, InterruptedException {
		int retries = 0;
		
		while (true) {
			try {
				Stat stat = zk.exists(path, false);
				if(stat == null){
					zk.create(path, value.getBytes(CHARSET), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}else {
					zk.setData(path, value.getBytes(CHARSET), -1);
				}
				break;
			} catch(KeeperException.SessionExpiredException e){
				//TODO 此处会话过期，抛出异常，由上层调用来重新创建zookeeper对象
				throw e;
			}catch(KeeperException.AuthFailedException e){
				//TODO 此处身份验证时，抛出异常，由上层来终止程序运行
				throw e;
			}catch (KeeperException e) {
				//检查有没有超出尝试的次数
				if(retries == MAXRETRIES){
					throw e;
				}
				retries++;
				TimeUnit.SECONDS.sleep(RETRY_PERIOD_SECONDS);
			}
		}
	}
	
	public String read(String path, Watcher watcher) throws InterruptedException, KeeperException {
		int retries = 0;
		
		String string = null;
		while (true) {
			byte[] data = null;
			try {
				data = zk.getData(path, watcher, null/*stat*/);
				string = new String(data, CHARSET);
				break;
			} catch (KeeperException e) {
				//检查有没有超出尝试的次数
				if(retries == MAXRETRIES){
					throw e;
				}
				retries++;
				TimeUnit.SECONDS.sleep(RETRY_PERIOD_SECONDS);
			} 
		}
		
		return string; 
	}
}
