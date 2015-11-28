package org.scala.algorithms.lists

import ListUtils._

/**
 * Reverse list
 */
object ReverseList {

  def main(args: Array[String]): Unit = {
    printlist(reverseList(fromList(List(1, 2, 3, 4, 5, 6))))
    printlist(reverseList(fromList(List())))
  }

  def reverseList(l: ListNode): ListNode = {
    if (l == null) {
      return null
    }
    var (prev, curr) = (l, l.next)
    prev.next = null
    while (curr != null) {
      val next = curr.next
      curr.next = prev
      prev = curr
      curr = next
    }
    return prev
  }

}
