package org.scala.algorithms.lists

import org.scala.algorithms.lists.ListUtils._

/**
 * Given two sorted linked lists, merge them using O(1) additional space
 */
object MergeTwoLists {

  def main(args: Array[String]): Unit = {
    printlist(merge(fromList(List(6, 7, 8)), fromList(List(4, 5, 6, 7, 8, 9))))
  }

  def merge(l1: ListNode, l2: ListNode): ListNode = {
    var (cr1, cr2): (ListNode, ListNode) = (null, null)
    if (l1.v > l2.v) {
      cr1 = l2
      cr2 = l1
    }
    else {
      cr1 = l1
      cr2 = l2
    }
    var prev: ListNode = cr1
    cr1 = cr1.next
    while (cr1 != null && cr2 != null) {
      if (cr1.v < cr2.v) {
        prev = cr1
        cr1 = cr1.next
      }
      else {
        val cell = cr2
        val nextCr2 = cr2.next
        prev.next = cell
        cell.next = cr1
        cr2 = nextCr2
      }
    }
    while (cr1 != null) {
      prev = cr1
      cr1 = cr1.next
    }
    cr1 = prev
    while (cr2 != null) {
      cr1.next = cr2
      cr1 = cr1.next
      prev = cr2
      cr2 = cr2.next
    }
    if (l1.v > l2.v) l2 else l1
  }

}
