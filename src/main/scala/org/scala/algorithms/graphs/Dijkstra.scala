package org.scala.algorithms.graphs

import scala.collection.mutable

/**
  */
object Dijkstra {

  def main(args: Array[String]): Unit = {
    val processed = new Array[Int](10)
    val g = Graph(List(
      (1, 2, 1),
      (1, 3, 1),
      (2, 3, 1),
      (3, 4, 2),
      (2, 4, 1),
      (4, 5, 2)
    ))
    println(solve(g, 1, 5))

  }

  def comp(tp1: (Int, Int)) = -1 * tp1._2

  def solve(g: Graph, s: Int, e: Int): Int = {
    val queue = new mutable.PriorityQueue[(Int, Int)]()(Ordering.by(comp))
    val processed = new Array[Boolean](g.vertex.size + 1)
    val path = Array.fill[Int](g.vertex.size + 1)(Int.MaxValue - 2)
    val parents = new Array[Int](g.vertex.size + 1)
    queue.enqueue((s, 0))
    path(s) = 0
    while (!queue.isEmpty) {
      val (vNumber, costToV) = queue.dequeue()
      processed(vNumber) = true
      for (edge <- g.vertex(vNumber)) {
        if (path(edge.to) > costToV + edge.weight) {
          path(edge.to) = costToV + edge.weight
          parents(edge.to) = edge.from
        }
        if (!processed(edge.to)) {
          queue.enqueue((edge.to, path(edge.to)))
        }
      }
    }
    path(e)
  }

}
