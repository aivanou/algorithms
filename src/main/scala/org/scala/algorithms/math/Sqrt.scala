package org.scala.algorithms.math

import scala.annotation.tailrec

/**
 * Square root of number
 */
object Sqrt {

  def main(args: Array[String]): Unit = {
    println(sqrt(2, 0.01D))
  }

  def sqrt(x: Double, error: Double): Double = {
    @tailrec def compute(v: Double): Double = {
      if (Math.abs(v * v - x) > error) {
        if (v * v > x)
          compute(v - v / 2)
        else compute(v + v / 2)
      }
      else return v

    }
    compute(x / 2)
  }

}
