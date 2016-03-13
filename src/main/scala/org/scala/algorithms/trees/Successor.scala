package org.scala.algorithms.trees

import TreeUtils._

/**
 * Find the successor in a bst of a tree node
 */
object Successor {

  def main(args: Array[String]): Unit = {
    val root = fromList(List(1, 2, 3, 4, 5, 6, 7, 8))
//    println(successor(root, search(root, 3)).data)
    preOrderTraversal(root, 0)
  }

  def successor(root: Tree, node: Tree): Tree = {
    if (node.right == null) {
      var curr = node
      while (!isLeftChild(curr)) {
        if (curr == null)
          return null
        curr = curr.parent
      }
      return curr.parent
    } else {
      var curr = node.right
      while (curr.left != null) {
        curr = curr.left
      }
      return curr
    }
    return node
  }

}
