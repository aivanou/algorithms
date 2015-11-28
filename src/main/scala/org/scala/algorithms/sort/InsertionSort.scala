package org.scala.algorithms.sort

/**
  */
object InsertionSort {
  def main(args: Array[String]): Unit = {
    println(insertionSort(Array(3, 2, 6, 7, 2, 11, 3, 4, -1, 54, 65, 7, 5)).mkString(" "))
  }

  def insertionSort(arr: Array[Int]): Array[Int] = {
    def swap(i: Int, j: Int): Unit = {
      val t = arr(i)
      arr(i) = arr(j)
      arr(j) = t
    }
    for (i <- 1 until arr.length) {
      var k = i
      for (j <- (0 to i - 1).reverse) {
        if (arr(k) < arr(j)) {
          swap(k, j)
          k -= 1
        }
      }
    }
    return arr
  }
}
