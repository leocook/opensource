package cn.wulinweb.hadoop.ZooKeeper.configservice;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public class ConfigUpdaterTest {
	public static void main(String[] args) throws InterruptedException, KeeperException {
		while (true) {
			try {
				ConfigUpdater configUpdater = new ConfigUpdater("192.168.1.8");
				configUpdater.run();
			} catch (KeeperException.SessionExpiredException e) {
				// TODO: 重新创建、开始一个新的会话
			} catch (KeeperException e) {
				// TODO 尝试了多次，还是出错，只有退出了
				throw e;
			} catch (IOException e) {
				// TODO 创建zookeeper对象失败，无法连接到zk集群
				e.printStackTrace();
			} 
		}
	}
}
