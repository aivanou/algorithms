
/**
 * https://www.hackerrank.com/challenges/counter-game
 */
object CounterGame {

  def main(args: Array[String]): Unit = {
    for (i <- 1 to readLine.toInt) {
      println(solve)
    }
  }

  def solve(): String = {
    def turn(number: Long, name: String): String = {
      if (number == 1L)
        return name
      else {
//        println(number)
        val newName = if (name == "Richard") "Louise" else "Richard"
        val (isPower, power) = isPowerOfTwo(number)
        if (isPower) {
          val newNumber = number - (power >>> 1)
          if (newNumber == 1L)
            return name
          return turn(newNumber, newName)
        }
        else {
          val newNumber = number & ~power
          if (newNumber == 1L)
            return name
          return turn(newNumber, newName)
        }
      }
    }
    val number = readLong
    return turn(number, "Louise")
  }

  def isPowerOfTwo(n: Long): (Boolean, Long) = {
    var maxPower: Long = 0L
    var isPower = false
    var isPowerSet = false
    var number: Long = n
    while (number != 0) {
      if ((number & 1L) == 1) {
        if (isPowerSet) {
          isPower = false
        }
        else {
          isPower = true
          isPowerSet = true
        }
      }
      if (maxPower == 0)
        maxPower = 1L
      else
        maxPower = maxPower << 1
      number = number >>> 1
    }
    return (isPower, maxPower)
  }

}
