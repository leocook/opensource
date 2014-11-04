package cn.wulinweb.hadoop.ZooKeeper.listgroup;

import java.util.List;

import org.apache.zookeeper.KeeperException;

import cn.wulinweb.hadoop.ZooKeeper.util.ConnectionWatcher;

public class ListGroup extends ConnectionWatcher {
	public void list(String groupName) {
		String path ="/" + groupName;
		try {
			List<String> children = zk.getChildren(path, false);
			if(children.isEmpty()){
				System.out.printf("No members in group %s.\n",  groupName);
				System.exit(1);
			}
			
			for(String child : children){
				System.out.println(child);
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
