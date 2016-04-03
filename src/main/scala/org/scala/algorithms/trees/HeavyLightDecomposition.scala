package org.scala.algorithms.trees

import scala.collection.mutable

/**
  */
object HeavyLightDecomposition extends App {


  class Node(_depth: Int, _size: Int) {
    val depth = _depth
    var size = _size
    var parent: Node = null
  }

  val size = 10
  val tree = new Array[Array[Int]](10)
  val edges = new Array[Int](10)
  val nodes = new Array[Int](10)
  val segArr = new Array[Int](10)

  val chains: mutable.HashMap[Int, Chain] = new mutable.HashMap[Int, Chain]()

  case class Chain(head: Int, parent: Int)

  def hld(tree: Array[Array[Int]], edges: Array[Int], nodes: Array[Node], root: Int): Unit = {
    val segTreeArray = new Array[Int](nodes.length)
    val chains = new mutable.HashMap[Int, Chain]()
    var ind = 0
    def split(nodeId: Int, chainHead: Chain): Unit = {
      if (nodeId == -1) return
      var heavyChildInd = 0
      for (child <- tree(nodeId)) {
        if (child != -1) {
          if (nodes(child).size > nodes(heavyChildInd).size) {
            heavyChildInd = child
          }
        }
      }
      segTreeArray(ind) = edges(tree(nodeId)(heavyChildInd))
      ind += 1
      chains(heavyChildInd) = chainHead
      split(heavyChildInd, chainHead)
      for (child <- tree(nodeId)) {
        if (child != -1 && child != heavyChildInd) {
          segTreeArray(ind) = edges(tree(nodeId)(child))
          ind += 1
          split(child, new Chain(child, nodeId))
        }
      }
    }
    split(root, new Chain(root, -1))
  }

  def dfs(root: Int, amount: Int, tree: Array[Array[Int]]): Array[Node] = {
    val nodes = new Array[Node](amount + 1)
    def visit(nodeId: Int, depth: Int): Int = {
      if (nodeId == -1) return 0
      val children = tree(nodeId)
      var sum = 0
      val node = new Node(depth, sum)
      for (child <- children) {
        if (child != -1) {
          sum += visit(child, depth + 1)
          nodes(child).parent = node
        }
      }
      node.size = sum
      return sum + 1
    }
    visit(root, 1)
    return nodes
  }
}