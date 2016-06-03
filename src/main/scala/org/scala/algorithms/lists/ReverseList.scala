package org.scala.algorithms.lists

import ListUtils._

/**
  * Reverse list
  */
object ReverseList {

  def main(args: Array[String]): Unit = {
    //    printlist(reverseList(fromList(List(1, 2, 3, 4, 5, 6))))
    //    printlist(reverseList(fromList(List())))
    val l1 = fromList(List(1, 4, 7, 9, 10, 14, 20))
    val l2 = fromList(List(-1, 2, 5, 8, 15, 22, 30, 41))
    printlist(merge(l1, l2))
  }

  def merge(l1: ListNode, l2: ListNode): ListNode = {
    val dummy = new ListNode(0, null)
    var (i1, i2) = (l1, l2)
    var tail = dummy
    while (true) {
      if (i1 == null) {
        tail.next = i2
        return dummy.next
      }
      else if (i2 == null) {
        tail.next = i1
        return dummy.next
      }
      if (i1.v < i2.v) {
        tail.next = i1
        i1 = i1.next
        tail = tail.next
      } else {
        tail.next = i2
        i2 = i2.next
        tail = tail.next
      }
    }
    return l1
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
