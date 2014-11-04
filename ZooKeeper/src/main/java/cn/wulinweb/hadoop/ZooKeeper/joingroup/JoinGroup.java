package cn.wulinweb.hadoop.ZooKeeper.joingroup;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

import cn.wulinweb.hadoop.ZooKeeper.util.ConnectionWatcher;

public class JoinGroup extends ConnectionWatcher {
	public void join(String groupName, String memberName) throws KeeperException, InterruptedException {
		String path = "/" + groupName + "/" + memberName;
		String createPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE/*所有用户可以读写*/, CreateMode.EPHEMERAL/*短暂的*/);
		System.out.println("Create " + createPath);
	}
}
