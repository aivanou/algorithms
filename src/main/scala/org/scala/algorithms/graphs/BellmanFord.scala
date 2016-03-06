package org.scala.algorithms.graphs

/**
  */
class BellmanFord {
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
  }

  def solve(g: Graph, s: Int): Unit = {
    val processed = new Array[Boolean](g.vertex.size + 1)
    val cost = Array.fill[Int](g.vertex.size + 1)(Int.MaxValue - 2)
    val parents = new Array[Int](g.vertex.size + 1)
    cost(s) = 0
    for (v <- g.vertex.keys) {
      for (edge <- g.vertex(v)) {
        relax(v, edge.to, edge.weight, cost, parents)
      }
    }
  }

  def relax(v: Int, u: Int, w: Int, cost: Array[Int], parents: Array[Int]): Unit = {
    if (cost(v) > cost(u) + w) {
      cost(v) = cost(u) + w
      parents(v) = parents(u)
    }
  }
}
