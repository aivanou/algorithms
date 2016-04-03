package org.scala.algorithms

import scala.collection.mutable
import scala.io.StdIn._
import scala.math._

/**
  */
object CartesianTree {

  def main(args: Array[String]) {
    val n = readInt()
    val (startX, startY) = readTuple()
    def compute(num: Int, prevX: Int, prevY: Int): Double = {
      if (num == 0) return calc(prevX, prevY, startX, startY)
      val (curX, curY) = readTuple()
      return calc(prevX, prevY, curX, curY) + compute(num - 1, curX, curY)
    }
    def calc(x1: Int, y1: Int, x2: Int, y2: Int): Double = sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
    println(compute(n - 1, startX, startY))
  }
  def readTuple(): (Int, Int) = readLine().split(" ").toList match {
    case List(s1: String, s2: String) => (s1.toInt, s2.toInt)
  }


  //  val root = build(List(2, 4, 3, 1, 6, 7, 8, 9, 1, 7))
  //  levelOrderTraversal(root)

  def levelOrderTraversal(node: Node): Unit = {
    var q = mutable.Queue[Node]()
    var nq = mutable.Queue[Node]()
    q.enqueue(node)
    while (!q.isEmpty) {
      val nd = q.dequeue()
      if (nd.left != null) nq.enqueue(nd.left)
      if (nd.right != null) nq.enqueue(nd.right)
      print(nd.data + " ")
      if (q.isEmpty) {
        println()
        q = nq
        nq = new mutable.Queue[Node]()
      }
    }
  }


  def build(lst: List[Int]): Node = {
    val stack = mutable.Stack[Node]()
    stack.push(new Node(null, null, null, lst(0)))
    var ind = 1
    while (ind < lst.size) {
      if (stack.top.data > lst(ind)) {
        val node = new Node(null, null, null, lst(ind))
        var prev: Node = null
        while (!stack.isEmpty && stack.top.data > lst(ind)) {
          prev = stack.pop()
        }
        if (stack.isEmpty) {
          node.left = prev
          stack.push(node)
        } else {
          stack.top.right = node
          node.left = prev
          stack.push(node)
        }
      } else {
        val node = new Node(null, null, stack.top, lst(ind))
        stack.top.right = node
        stack.push(node)
      }
      ind += 1
    }
    var node = stack.pop()
    while (!stack.isEmpty)
      node = stack.pop()
    return node
  }

  class Node(_left: Node, _right: Node, _parent: Node, _data: Int) {
    var (left, right, parent, data) = (_left, _right, _parent, _data)
  }

}
