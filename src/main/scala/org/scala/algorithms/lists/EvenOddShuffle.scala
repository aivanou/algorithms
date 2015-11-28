package org.scala.algorithms.lists

import ListUtils._

/**
 * Given a singly linked list shuffle nodes so the nodes that are on the even place come first
 * and then all node that have an odd index
 */
object EvenOddShuffle {

  def main(args: Array[String]): Unit = {
    printlist(evenOddList(fromList(List(1, 2, 3, 4, 5, 6))))
  }

  def evenOddList(l: ListNode): ListNode = {
    def len(n: ListNode): Int = n match {
      case null => 0
      case v => 1 + len(v.next)
    }
    var lstLen = len(l)
    def change(ptr: ListNode, nPairs: Int): Unit = {
      var prev = ptr
      var curr = ptr.next
      for (i <- 1 to nPairs) {
        val p1 = curr
        val p2 = curr.next
        p1.next = p2.next
        prev.next = p2
        p2.next = p1
        prev = p1
        curr = p1.next
      }
    }
    def process(ptr: ListNode, len: Int): Unit = {
      if (ptr == null || len <= 0) {
        return
      }
      change(ptr, len / 2)
      process(ptr.next, len - 2)
    }
    lstLen -= 1
    process(l, lstLen)
    return l
  }

}
