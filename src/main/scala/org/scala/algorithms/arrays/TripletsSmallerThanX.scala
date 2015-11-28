package org.scala.algorithms.arrays

/**
 * Count triplets with sum smaller than a given value
 */
object TripletsSmallerThanX {

  def main(args: Array[String]): Unit = {
    println(countTriplets(Array(4, -1, 4, 5, 6, 3, -1, 2, 94, 5, 2, 1), 10))
  }

  def countTriplets(arr: Array[Int], x: Int): Int = {
    val sortedArr = arr.sorted
    var count = 0
    for (i <- 0 until arr.length - 2) {
      val sum = x - sortedArr(i)
      var (l, r) = (i + 1, arr.length - 1)
      while (l < r) {
        if (sortedArr(i) + sortedArr(l) + sortedArr(r) > x) {
          r -= 1
        }
        else {
          count += r - l
          l += 1
        }
      }
    }
    return count
  }

}
