name := "Algorithms"

mainClass in (Compile, packageBin) := Some("org.scala.spark.SparkTest1")

mainClass in (Compile, run) := Some("org.scala.spark.SparkTest1")

version := "1.0"

scalaVersion := "2.11.6"

scalacOptions += "-target:jvm-1.8"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.2" % "test"

libraryDependencies += "io.reactivex" % "rxjava" % "1.0.10"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "1.6.1"

libraryDependencies += "com.netflix.hystrix" % "hystrix-core" % "1.5.2"