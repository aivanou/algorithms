package org.scala.algorithms.graphs

import java.util
import java.util.Scanner


import scala.collection.mutable.ListBuffer

object MinSpanningTree {

  def kruskal(g: Graph): ListBuffer[Edge] = {
    val union = (1 to g.vertex.size).toArray
    def updateUnion(ind: Int, v: Int): Unit = {
      if (union(ind) == ind) {
        union(ind) = v
        return
      }
      updateUnion(union(ind), v)
      union(ind) = v
    }
    val mst = ListBuffer[Edge]()
    val edges = ListBuffer[Edge]()
    for (v <- g.vertex.keys) {
      for (e <- g.vertex(v)) {
        edges += e
      }
    }
    edges.sortBy(_.weight)
    for (edge <- edges) {
      if (union(edge.from) != union(edge.to)) {
        mst += edge
        updateUnion(edge.to, edge.from)
      }
    }
    return mst
  }

  def prim(): Unit = {
    val s = new Scanner(System.in);
    s.next
  }

}
