import scala.collection.mutable._

/**
 * https://www.hackerrank.com/challenges/sansa-and-xor
 */

object SansaAndXor {

  def main(args: Array[String]): Unit = {
    (1 to readLine.toInt).foreach {
      case _ => println(solve)
    }
  }

  def solve: Long = {
    val n = readLine.toInt
    var ans: Long = -1
    val arr = readLine.split(" ").map {
      case s => {
        if (ans == -1)
          ans = s.toInt
        else ans ^= s.toInt
        s.toInt
      }
    }
    val totalOccur = Array.fill[Int](n)(0)
    totalOccur(0) = n
    if (n % 2 == 0)
      ans = 0
    else ans = arr(0)
    for (i <- 1 until n) {
      totalOccur(i) = totalOccur(i - 1) + (n - i)
      val v = totalOccur(i) - (i + 1) * i / 2
      if (v % 2 != 0)
        ans ^= arr(i)
    }
    return ans
  }

}
