package org.scala.algorithms.graphs

import scala.collection.mutable.ListBuffer

/**
  */
case class Edge(from: Int, to: Int, weight: Int)

case class Graph(vertex: collection.mutable.Map[Int, ListBuffer[Edge]])

object Graph {

  def apply(data: List[(Int, Int, Int)]): Graph = {
    val vertex = collection.mutable.Map[Int, ListBuffer[Edge]]()
    for (d <- data) {
      if (!vertex.contains(d._1)) {
        vertex(d._1) = ListBuffer[Edge]()
      }
      if (!vertex.contains(d._2)) {
        vertex(d._2) = ListBuffer[Edge]()
      }
      vertex(d._1) += Edge(d._1, d._2, d._3)
    }
    return Graph(vertex)
  }

}
