package cn.wulinweb.hadoop.ZooKeeper.configservice;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

public class ConfigWatcher implements Watcher {

	private ActiveKeyValueStore store;
	
	
	public ConfigWatcher(String hosts) throws IOException, InterruptedException {
		store = new ActiveKeyValueStore();
		store.connection(hosts);
	}
	
	/**
	 * 显示配置
	 * @throws KeeperException 服务器发出错误信号或是服务器存在通信故障。该类现在共有22个子类，
	 * 分为3大类：<br/>
	 * 1、状态异常(如：BadVersionException、NoChildrenForEphemeralsException)；
	 * 2、可恢复的异常（如：ConnectionLossException）；
	 * 3、不可恢复的异常（如：SessionExpiredException、AuthFailedException）。
	 * 每个子类对应一种异常状态，且每个子类都对应一个关于错误类型的信息代码，可以通过code方法拿到。
	 * 处理该种异常有两种办法：<br/>
	 * 1、通过<b>检测错误代码</b>来决定应该采取何种补救措施；<br/>
	 * 2、通过<b>追捕等价的KeeperException异常</b>，然后再每段捕捉代码中执行相应的操作。
	 * @throws InterruptedException zookeeper操作被中断。<b>并不一定就是出现故障，只能表明相对应的操作被取消</b>。
	 */
	public void displayConfig() throws KeeperException, InterruptedException {
		String value = store.read(ConfigUpdater.PATH, this);
		System.out.printf("Read %s as %s\n",ConfigUpdater.PATH, value);
	}

	public void process(WatchedEvent event) {
		if(event.getType() == EventType.NodeDataChanged){
			try {
				displayConfig();
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				System.err.println("interrupted. Exiting. ");
				Thread.currentThread().interrupt();
			}
		}
	}
}
