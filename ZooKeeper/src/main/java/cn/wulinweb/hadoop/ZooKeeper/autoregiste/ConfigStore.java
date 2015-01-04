package cn.wulinweb.hadoop.ZooKeeper.autoregiste;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;

import cn.wulinweb.hadoop.ZooKeeper.util.ConnectionWatcher;

public class ConfigStore extends ConnectionWatcher {
	private static final Charset CHARSET = Charset.forName("UTF-8");
	private static final int MAXRETRIES = 10;
	private static final int RETRY_PERIOD_SECONDS = 2;
	
	public Map<String, String> read(String path, Watcher watcher) throws InterruptedException, KeeperException {
		int retries = 0;
		Map<String, String> conf = new HashMap<String, String>();
		
		while (true) {
			byte[] data = null;
			List<String> childs = null;
			String string = null;
			try {
				childs = zk.getChildren(path, watcher);
				for (String child : childs) {
					data = zk.getData(path+"/"+child, watcher, null);
					if(null != data){
						string = new String(data, CHARSET);
					}
					conf.put(child, string);
				}
				
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
		
		return conf; 
	}
}
