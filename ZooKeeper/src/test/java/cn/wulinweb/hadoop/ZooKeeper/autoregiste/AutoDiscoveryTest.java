package cn.wulinweb.hadoop.ZooKeeper.autoregiste;

import java.util.concurrent.TimeUnit;

public class AutoDiscoveryTest {
	private static final String HOSTS = "192.168.1.137";
	private static final String PATH = "/config";
	
	public static void main(String[] args) throws InterruptedException {
		AutoDiscovery autoDiscovery = new AutoDiscovery(HOSTS, PATH);
		autoDiscovery.run();
		
		TimeUnit.SECONDS.sleep(1);
	}
}
