package org.scala.algorithms.trees

import scala.annotation.tailrec
import scala.collection.mutable

/**
  */
object CoveredUncoveredSum extends App {

  val tree =
    new Node(8,
      new Node(3,
        new Node(1, null, null),
        new Node(6,
          new Node(4, null, null),
          new Node(7 + 32, null, null)
        )
      ),
      new Node(10,
        null,
        new Node(14,
          new Node(13,
            null,
            null),
          null)
      )
    )

  println(coveredUncovered(tree))

  def coveredUncovered(root: Node): Boolean = {
    var totalSum = 0
    var uncoveredSum = 0
    @tailrec def traverse(currentLevel: mutable.Queue[(Node, Boolean)]): Unit = {
      if (currentLevel.isEmpty) return
      val nextLevel = mutable.Queue[(Node, Boolean)]()
      while (!currentLevel.isEmpty) {
        val (node, isUncovered) = currentLevel.dequeue()
        totalSum += node.v
        if (isUncovered) {
          uncoveredSum += node.v
          println(node.v)
        }
        if (!isUncovered) {
          if (node.left != null) nextLevel.enqueue((node.left, false))
          if (node.right != null) nextLevel.enqueue((node.right, false))
        } else {
          if (node.left != null && node.right != null) {
            nextLevel.enqueue((node.left, true))
            nextLevel.enqueue((node.right, false))
          } else if (node.left != null) {
            nextLevel.enqueue((node.left, true))
          } else if (node.right != null) {
            nextLevel.enqueue((node.right, true))
          }
        }
      }
      traverse(nextLevel)
    }
    traverse(mutable.Queue[(Node, Boolean)]((root, true)))
    return totalSum == uncoveredSum * 2
  }

  case class Node(v: Int, left: Node, right: Node)

}
