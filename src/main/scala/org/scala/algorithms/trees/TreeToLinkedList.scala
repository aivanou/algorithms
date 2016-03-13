package org.scala.algorithms.trees

import TreeUtils._
import Successor._

/**
 * Design an algorithm that takes as input a BST B
 * and returns a sorted doubly linked list on the same elements.
 * Your algorithm should not allocate any new nodes.
 * The original BST does not have to be preserved;
 * use its nodes as the nodes of the resulting list
 */
object TreeToLinkedList {

  def main(args: Array[String]): Unit = {

  }

  def toList(root: Tree): Tree = {
    def build(node: Tree): Unit = {
      if (node.right == null) {
        val successor = Successor.successor(root, node)
        if (successor != null) {
          node.right = successor
          successor.left = node
        }
      }
      else {
        build(node.left)
        build(node.right)
      }
    }
    build(root)
    return root
  }
}
