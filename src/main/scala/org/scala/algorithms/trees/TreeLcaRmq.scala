package org.scala.algorithms.trees

import org.java.algorithms.SegTree

import scala.collection.mutable

/**
  * http://www.geeksforgeeks.org/find-lca-in-binary-tree-using-rmq/
  */
object TreeLcaRmq extends App {

  val root = TreeUtils.fromList(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

  val (idArr, levelArr) = eulerTour(root, 10)
  println(idArr.mkString(" "))
  println(levelArr.mkString(" "))

  TreeUtils.levelOrderTraversal(root)

  val segmentTree = new SegTree(idArr)

  def eulerTour(root: Tree, nv: Int): (Array[Int], Array[Int]) = {
    val idArr = new Array[Int](nv * 2 - 1)
    val levelArr = new Array[Int](nv * 2 - 1)
    val visited = new Array[Boolean](nv + 1)
    val st = mutable.Stack[(Tree, Int)]()
    st.push((root, 1))
    var ind = 0
    while (!st.isEmpty) {
      val (node, ndLevel) = st.pop()
      idArr(ind) = node.data
      levelArr(ind) = ndLevel
      ind += 1
      visited(node.data) = true
      if (node.left != null && !visited(node.left.data)) {
        st.push((node, ndLevel))
        st.push((node.left, ndLevel + 1))
      } else if (node.right != null && !visited(node.right.data)) {
        st.push((node, ndLevel))
        st.push((node.right, ndLevel + 1))
      }
    }
    return (idArr, levelArr)
  }

  def getNextPowerOf2(n: Int): Int = {
    var t = n | n >> 1
    t = t | t >> 2
    t = t | t >> 4
    t = t | t >> 8
    return t + 1
  }
}
