package org.scala.algorithms.lists

/**
  */
object ListUtils {
  def printlist(l: ListNode): Unit = {
    l match {
      case null => println()
      case nd => print(nd.v + " "); printlist(nd.next)
    }
  }

  def fromList(l: List[Int]): ListNode =
    l match {
      case Nil => null
      case nd :: tail => new ListNode(nd, fromList(tail))
    }

}

class ListNode(v1: Int, next1: ListNode) {
  var v: Int = v1
  var next: ListNode = next1
}
