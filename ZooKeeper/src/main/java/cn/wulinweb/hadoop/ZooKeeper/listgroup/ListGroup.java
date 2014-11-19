package cn.wulinweb.hadoop.ZooKeeper.listgroup;

import java.util.List;

import org.apache.zookeeper.KeeperException;

import cn.wulinweb.hadoop.ZooKeeper.util.ConnectionWatcher;

public class ListGroup extends ConnectionWatcher {
	public void list(String groupName) {
		String path ="/" + groupName;
		zk.register(this);
		try {
//			zk.delete(path+"/"+"zoo2", -1);
			
			List<String> children = zk.getChildren(path, this);
			if(children.isEmpty()){
				System.out.printf("No members in group %s.\n",  groupName);
				System.exit(1);
			}
			
			for(String child : children){
				System.out.println(child);
			}
			
			
//			if(zk.exists(path+"/"+"zoo3", this) != null){
//				zk.setData(path+"/"+"zoo3", "aaaaaaa".getBytes(), -1);
//				System.out.println("xxxsss");
//			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
