package org.scala.algorithms.arrays

/**
 * Reorder an array according to given indexes
 */
object ReordeArrayAccordingToIndexes {

  def main(arr: Array[String]): Unit = {
    val (arr, ind) = (Array(1, 2, 3, 412, 5, 6, 7, 8), Array(3, 2, 1, 4, 7, 5, 0, 6))
    println(reorder(arr, ind).mkString(" "))
  }

  def reorder(arr: Array[Int], ind: Array[Int]): Array[Int] = {
    for (i <- 0 until arr.length) {
      while (ind(i) != i) {
        println(arr.mkString(" "))
        val (tempV, tempInd) = (arr(ind(i)), ind(ind(i)))
        arr(ind(i)) = arr(i)
        ind(ind(i)) = ind(i)
        arr(i) = tempV
        ind(i) = tempInd
      }
      println(arr.mkString(" "))
    }
    return arr;
  }
}
