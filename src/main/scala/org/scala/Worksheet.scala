package org.scala

import scala.collection.mutable

/**
  */
object Worksheet extends App {


  val memo = mutable.HashMap[(Int, Boolean, Byte), Long]()
  memo.put((1, true, 0), 2l)
  memo.put((1, true, 1), 2l)
  memo.put((1, true, 2), 1l)
  memo.put((1, false, 0), 3l)
  memo.put((1, false, 1), 3l)
  memo.put((1, false, 2), 2l)
  var ops = 0;

  def getCount(n: Int, containsB: Boolean, endC: Byte): Long = {
    memo.get((n, containsB, endC)) match {
      case Some(x) => return x
      case None => {
        ops += 1
        var ans = 0l
        if (!containsB)
          ans += getCount(n - 1, true, 0)
        if (endC < 2)
          ans += getCount(n - 1, containsB, (endC + 1).toByte)
        ans += getCount(n - 1, containsB, 0)
        memo.put((n, containsB, endC), ans)
        ans
      }
    }
  }

  println(getCount(20, false, 0))
  println(ops)
}
