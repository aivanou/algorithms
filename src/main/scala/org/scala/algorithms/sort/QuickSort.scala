package org.scala.algorithms.sort

/**
  */
object QuickSort {

  def main(args: Array[String]): Unit = {
    println(quickSort(Array(3, 4, 19, 3, 66, 4, 2, 6, 75, 3, 1, 4, 6, 8, 4, 34, 6)).mkString(" "))
  }

  def quickSort(arr: Array[Int]): Array[Int] = {
    def swap(i: Int, j: Int): Unit = {
      val t = arr(i)
      arr(i) = arr(j)
      arr(j) = t
    }
    def pivot(l: Int, r: Int): Int = {
      val pivotEl = arr(r)
      var j = l
      for (i <- l to r) {
        if (arr(i) < pivotEl) {
          swap(j, i)
          j += 1
        }
      }
      swap(j, r)
      return j
    }
    def sort(l: Int, r: Int): Unit = {
      if (l >= r)
        return
      val mid = pivot(l, r)
      sort(l, mid - 1)
      sort(mid + 1, r)
    }
    sort(0, arr.length - 1)
    return arr
  }
}
