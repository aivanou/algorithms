object Upvotes {

  def main(args: Array[String]): Unit = {
    val (n, k) = readLine.split(" ") match {
      case Array(x, y) => (x.toInt, y.toInt)
    }
    val values = readLine.split(" ").map(s => s.toInt).toList
    val seq = Array.fill[Int](n)(0)
    for (i <- 1 until seq.length) {
      if (values(i) > values(i - 1))
        seq.update(i, 2)
      else if (values(i) < values(i - 1))
        seq.update(i, 1)
    }
    val nonIncSum = Array.fill[Int](n + 1)(0)
    val nonDecSum = Array.fill[Int](n + 1)(0)
    var prevIncDecType = 0
    var currIncSum = 1
    var currDecSum = 1
    for (i <- 1 until seq.length) {
      val value = seq(i)
      if (value == 0) {
        currIncSum += 1
        currDecSum += 1
      }
      else if (value == 1) {
        currIncSum = 1
        currDecSum += 1
      } else if (value == 2) {
        currIncSum += 1
        currDecSum = 1
      }
      println(currIncSum, currDecSum)
      nonIncSum.update(i, (currDecSum + 1) * currDecSum / 2 - currDecSum)
      nonDecSum.update(i, (currIncSum + 1) * currIncSum / 2 - currIncSum)
    }
    println(seq.mkString(" "))
    println(nonIncSum.mkString("  "))
    println(nonDecSum.mkString("  "))
  }
}
