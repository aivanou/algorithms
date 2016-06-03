package org.scala.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  */
object SparkTest1 extends App {

  val appName = "test"
  val master = "local"
  val conf = new SparkConf().setAppName(appName).setMaster(master)
  val sc = new SparkContext(conf)
  val data = Array(1, 2, 3, 4, 5)
  val distData = sc.parallelize(data)
}
