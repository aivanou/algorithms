package org.scala.algorithms.dynamic

/**
  */
object KnapsakProblem {

  def main(args: Array[String]): Unit = {

  }

  def unboundKnapsack(w: Array[Int], price: Array[Int], maxW: Int): Unit = {
    val dp = Array.fill[Int](maxW + 1)(0)
    for (i <- 1 to maxW) {
      var mv = 0
      for (j <- 0 until w.length) {
        if (i >= w(j)) {
          mv = Math.max(mv, dp(i - w(j)) + price(j))
        }
      }
      dp(i) = mv
    }
  }

}
