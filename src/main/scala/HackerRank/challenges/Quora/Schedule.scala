/**
 * Aliaksandr Ivanou tierex02@gmail.com
 * https://www.hackerrank.com/contests/quora-haqathon/challenges/schedule
 */

import scala.collection.mutable._

object Schedule {

  case class Test(time: Int, prob: Double, id: Int)

  implicit val testOrd = new Ordering[Test]() {
    override def compare(x: Test, y: Test): Int = {
      (x.time / (1 - x.prob)).compareTo(y.time / (1 - y.prob))
    }
  }

  def main(args: Array[String]): Unit = {
    val lb = ListBuffer[Test]()
    (1 to readLine.toInt).foreach {
      case i => {
        readLine.split(" ") match {
          case Array(t, p) => lb += Test(t.toInt, p.toDouble, i)
        }
      }
    }
    val tests = lb.sorted
    var prob: Double = 0.0
    var cp: Double = 1.0
    for (t <- tests) {
      prob += t.time * cp
      cp *= t.prob
    }
    println(f"$prob%1.9f")

  }
}
