package org.java.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class HelloZookeeper {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String zhost = "localhost:2181";
        String zpath = "/";
        ZooKeeper zk = new ZooKeeper(zhost, 2000, null);
        List<String> zooChildren = zk.getChildren(zpath, false);

    }
}
