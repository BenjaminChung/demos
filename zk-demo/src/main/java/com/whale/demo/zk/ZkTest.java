package com.whale.demo.zk;

import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * Created by benjaminchung on 2017/4/3.
 */
public class ZkTest {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000, 500);
        System.out.println(zkClient.exists("/test"));
        zkClient.deleteRecursive("/test");
        zkClient.createPersistent("/test");
        zkClient.create("/test/child1","child node", CreateMode.EPHEMERAL);
        zkClient.writeData("/test/child1","test");
        List<String> children = zkClient.getChildren("/test");
        System.out.println(children);
        System.out.println(zkClient.readData("/test/child1"));
        //订阅模式
        IZkStateListener zkStateListener = new IZkStateListener() {
            public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
                // do nothing
            }

            public void handleNewSession() throws Exception {
            }
        };
        zkClient.subscribeStateChanges(zkStateListener);

    }
}
