package HakerEarth

import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag
import scala.util.Random

object Worksheet {

  def main(args: Array[String]): Unit = {
  }


  case class Vertex(v: Int, w: Int)

  // https://www.hackerearth.com/july-clash-15/algorithm/sabotage-1/
  def sabotage(): Unit = {
    val graph = scala.collection.mutable.Map[Int, ListBuffer[Vertex]]()
    def addVertex(v1: Int, v2: Int, w: Int) = {
      graph.get(v1) match {
        case Some(lst) => lst += Vertex(v2, w)
        case None => graph.put(v1, ListBuffer[Vertex](Vertex(v2, w)))
      }
    }
    val (n, m) = readPair
    for (i <- 1 to m) {
      val (v1, v2) = readPair
      addVertex(v1, v2, m - i)
      addVertex(v2, v1, m - i)
    }
  }

  def readPair(): (Int, Int) = {
    readLine.split(" ") match {
      case Array(x, y) => (x.toInt, y.toInt)
    }
  }




}
