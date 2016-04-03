package org.scala.algorithms.trees

import scala.annotation.tailrec
import scala.collection.mutable

object TreeUtils {

  def isLeftChild(node: Tree): Boolean = {
    if (node == null || node.parent == null) return false
    return node.parent.left == node
  }

  def isRightChild(node: Tree): Boolean = {
    if (node == null || node.parent == null) return false
    return node.parent.right == node
  }

  def fromList(lst: List[Int]): Tree = {
    def build(l: Int, r: Int): Tree = {
      if (l > r)
        return null
      if (l == r) {
        return new Tree(null, null, null, lst(l))
      }
      val mid = l + (r - l) / 2
      val left = build(l, mid - 1)
      val right = build(mid + 1, r)
      val node = new Tree(left, right, null, lst(mid))
      if (left != null)
        left.parent = node
      if (right != null)
        right.parent = node
      return node
    }
    return build(0, lst.size - 1)
  }

  def insert(root: Tree, v: Int): Tree = {
    def search(node: Tree, parent: Tree): Unit = {
      if (node == null) {
        val newNode = new Tree(null, null, parent, v)
        if (parent.data > v) {
          parent.left = newNode
        }
        else parent.right = newNode
        return
      }
      if (node.data > v) {
        search(node.left, node)
      }
      else {
        search(node.right, node)
      }
    }
    if (root == null) {
      return new Tree(null, null, null, v)
    }
    search(root, null)
    return root
  }

  def search(root: Tree, v: Int): Tree = {
    if (root == null) return null
    if (root.data == v) return root
    else if (root.data > v) return search(root.left, v)
    else return search(root.right, v)
  }

  def levelOrderTraversal(node: Tree): Unit = {
    val q = mutable.Queue[(Tree, Int)]()
    q.enqueue((node, 0))
    var clevel = 0
    while (!q.isEmpty) {
      val (nd, level) = q.dequeue()
      if (clevel != level) {
        clevel = level
        println()
      }
      if (nd != null) {
        print(s"${nd.data} ")
        q.enqueue((nd.left, level + 1))
        q.enqueue((nd.right, level + 1))
      }
    }
    println()
  }

  def inOrderTraversal(node: Tree, depth: Int): Unit = {
    if (node == null)
      return
    inOrderTraversal(node.left, depth + 1)
    println(node.data + "  " + depth)
    inOrderTraversal(node.right, depth + 1)
  }


  def preOrderTraversal(node: Tree, depth: Int): Unit = {
    if (node == null)
      return
    println(node.data + "  " + depth)
    preOrderTraversal(node.left, depth + 1)
    preOrderTraversal(node.right, depth + 1)
  }

  def allTreesFromInOrder(arr: Array[Int]): Unit = {
    def process(rt: Int, l: Int, r: Int): Unit = {
      print(arr(rt) + " ")
      for (i <- l until rt) {
        process(i, l, rt)
      }
      for (i <- rt + 1 to r) {
        process(i, rt + 1, r)
      }
      if (rt == r) {
        println()
      }
    }
    for (i <- 0 until arr.length) {
      process(i, 0, arr.length - 1)
    }
  }

  def checkBSTSatisfaction(root: Tree): Unit = {
    def process(node: Tree): Boolean = {
      if (node == null)
        return true
      else if (node.data < node.left.data || node.data > node.right.data)
        return false
      process(node.left) && process(node.right)
    }
    process(root)
  }

  def searchInMinFirst(root: Tree, v: Int): Boolean = {
    def process(node: Tree): Boolean = {
      if (node == null) false
      else if (node.data == v) true
      else if (node.right.data > v)
        process(node.left)
      else process(node.right)
    }
    process(root)
  }

  def bstFromSortedArray(arr: Array[Int]): Tree = {
    def process(l: Int, r: Int): Tree = {
      if (l > r) return null
      val mid = (l + r) / 2
      return new Tree(process(l, mid - 1), process(mid + 1, r), null, arr(mid))
    }
    return process(0, arr.length - 1)
  }


  def main(args: Array[String]): Unit = {
    //    val a = Array(1, 2, 3, 4)
    //    allTreesFromInOrder(a)
    //    bstFromSortedArray(a)
  }


}

class Tree(l: Tree, r: Tree, p: Tree, d: Int) {
  var data = d
  var parent = p
  var left = l
  var right = r
}
