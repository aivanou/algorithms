package org.scala.algorithms.strings

/**
  */
object AllPermutations extends App {
  val str = "1234567"
  print(str, 0, List(), Set())

  def print(str: String, pos: Int, chars: List[Char], used: Set[Char]): Unit = {
    if (pos == str.length) {
      println(chars.mkString(""))
      return
    }
    for (ch <- str.toCharArray) {
      if (!used.contains(ch)) {
        print(str, pos + 1, ch :: chars, used + ch)
      }
    }
  }
}
