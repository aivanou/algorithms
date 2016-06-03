package org.scala

/**
  */
object Worksheet extends App {


  def fib(max: Int): Long = {
    val arr = new Array[Long](max + 1)
    arr(1) = 1
    arr(2) = 1
    for (ind <- 3 until max) {
      arr(ind) = arr(ind - 1) + arr(ind - 2)
    }
    println(arr.mkString(" "))
    return 1
  }

  def findMaxPrime(st: Long): Long = {
    var num = st
    var max = 1L
    def findPrime(number: Long): Long = {
      for (ind <- 2L until num) {
        if (num % ind == 0) {
          return ind
        }
      }
      return 1
    }

    while (num != 1) {
      val prime = findPrime(num)
      println(prime)
      num = num / prime
    }
    return 1L
  }

  findMaxPrime(600851475143L)
}
