package org.scala.algorithms.trees

/**
  */
object TrimBST extends App {

  val tree = fromSortedList(List[Int](1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18))
  tree.trim(14, 32)
  tree.print()

  private[TrimBST] class TNode(_v: Int, _left: TNode, _right: TNode) {
    var v = _v
    var left = _left
    var right = _right
  }

  def fromSortedList(arr: List[Int]): BST = {
    val tree = new BST()
    def add(l: Int, r: Int): Unit = {
      if (l > r) return
      val mid = l + (r - l) / 2
      tree.add(arr(mid))
      add(mid + 1, r)
      add(l, mid - 1)
    }
    add(0, arr.length - 1)
    return tree
  }

  private[TrimBST] class BST {
    var root: TNode = null

    def trim(l: Int, r: Int): Unit = {
      def findNewRoot(node: TNode): TNode = {
        if (node.v < l) {
          return findNewRoot(node.right)
        } else if (node.v > r) {
          return findNewRoot(node.left)
        }
        return node
      }
      def process(node: TNode): Unit = {
        if (node == null || node.v < l || node.v > r) return
        if (node.left != null) {
          if (node.left.v < l) {
            var child = node.left.right
            while (child != null && child.v < l) {
              child = child.right
            }
            node.left = child
          }
        }
        if (node.right != null) {
          if (node.right.v > r) {
            var child = node.right.left
            while (child != null && child.v > r) {
              child = child.left
            }
            node.right = child
          }
        }
        process(node.left)
        process(node.right)
      }
      root = findNewRoot(root)
      process(root)
    }

    def print(): Unit = {
      def process(node: TNode): Unit = {
        if (node == null) return
        process(node.left)
        println(node.v)
        process(node.right)
      }
      process(root)
    }


    def add(v: Int): Unit = {
      if (root == null) {
        root = new TNode(v, null, null)
        return
      }
      var (curr: TNode, prev: TNode) = (root, root)
      while (curr != null) {
        prev = curr
        if (v > curr.v) {
          curr = curr.right
        } else if (v < curr.v) {
          curr = curr.left
        }
      }
      if (v > prev.v) {
        prev.right = new TNode(v, null, null)
      } else prev.left = new TNode(v, null, null)
    }
  }

}
