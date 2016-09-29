package org.scala.zookeeper

import org.apache.zookeeper.ZooKeeper
import scala.collection.JavaConversions._

/**
  */
object ZookeeperHello {

  def main(args: Array[String]): Unit = {
    val zkHost = "localhost:2181"
    val zpath = "/"
    val zk = new ZooKeeper(zkHost, 2000, null)
    val ch = zk.getChildren(zpath, false).toList
    for (c <- ch) {
      println(c)
    }
  }
}
