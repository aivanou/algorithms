package org.scala.algorithms.arrays

import scala.annotation.tailrec

/**
 * Given a sorted array and en element,
 * find the smallest element in array that is greater than element X
 */
object NextLargeElement {

  def main(args: Array[String]): Unit = {
    println(nextLargerElement(Array(1, 2, 3, 4, 5, 5, 8), 6))
    println(indexEqEl(Array(1, 2, 3, 4, 5, 5, 8)))
  }

  def nextLargerElement(arr: Array[Int], el: Int): Int = {
    @tailrec def search(l: Int, r: Int): Int = {
      if (l > r) {
        return l
      }
      val mid = l + (r - l) / 2
      if (arr(mid) <= el) {
        return search(mid + 1, r)
      }
      else search(l, mid - 1)
    }
    return arr(search(0, arr.length - 1))
  }

  def indexEqEl(arr: Array[Int]): Int = {
    def search(l: Int, r: Int): Int = {
      if (l > r) {
        return -1
      }
      val mid = l + (r - l) / 2
      if (arr(mid) == mid) {
        return mid
      }
      else if (arr(mid) > mid) {
        return Math.max(search(l, mid - 1), search(arr(mid), r))
      }
      else {
        return Math.max(search(l, mid - 1), search(mid + 1, r))
      }
    }
    search(0, arr.length - 1)
  }

  /**
   * Given a sorted array, find an element
   **/
  def binarySearch(arr: Array[Int], x: Int): Int = {
    def search(l: Int, r: Int): Int = {
      if (l > r) {
        return -1
      }
      val mid = l + (r - l) / 2
      if (arr(mid) == x) {
        val prevIndex = search(l, mid - 1)
        if (prevIndex != -1)
          return prevIndex
        return mid
      }
      if (arr(mid) > x) {
        return search(l, mid - 1)
      }
      return search(mid + 1, r)
    }
    search(0, arr.length - 1)
  }
}
