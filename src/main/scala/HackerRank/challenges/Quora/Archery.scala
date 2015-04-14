/**
 * Aliaksandr Ivanou tierex02@gmail.com
 * http://www.quora.com/about/challenges#archery
 *
 *
 */

import scala.collection.mutable._

object Archery {

  case class Point(x: Long, y: Long)

  case class Line(p1: Point, p2: Point)

  case class Circle(radius: Int)


  implicit var circleOrd = new Ordering[Circle] {
    override def compare(x: Circle, y: Circle): Int = {
      return x.radius.compareTo(y.radius)
    }
  }


  def main(args: Array[String]): Unit = {
    readLine.toInt
    val hs = HashSet[Int]()
    readLine.split(" ").foreach {
      case s => hs += s.toInt
    }
    val circles = hs.map(r => Circle(r)).toArray.sorted
    val lines = readLines(readLine.toInt)
    val nestedCircles = buildNestedCirclesMap(circles)
    var qPoints = 0L
    for (line <- lines) {
      val lpoint = findHyp(line.p1, Math.ceil)
      val rpoint = findHyp(line.p2, Math.floor)
//      println(circles(0), circles(circles.length - 1), lpoint, rpoint)
//      println(findFirstGreaterCircle(lpoint, circles), findFirstLesserCircle(rpoint, circles), line)
      (findFirstGreaterCircle(lpoint, circles), findFirstLesserCircle(rpoint, circles)) match {
        case (l: Circle, r: Circle) => {
          (nestedCircles.get(l.radius), nestedCircles.get(r.radius)) match {
            case (Some(low: Int), Some(high: Int)) => qPoints += (high - low + 1)
            case (_, _) => {}
          }
        }
        case (_, _) => {}
      }
    }
    println(qPoints)
  }

  def findHyp(point: Point, operation: Double => Double): Int = {
    return operation(Math.sqrt(point.x * point.x + point.y * point.y)).toInt
  }

  def findFirstGreaterCircle(hyp: Int, circles: Array[Circle]): Circle = {
    def binarySearch(l: Int, r: Int): Circle = {
      if (l >= r)
        return null
      val mid = (l + r) / 2
      val circle = circles(mid)
      if (circles(mid - 1).radius < hyp && circle.radius >= hyp)
        return circle
      else if (circle.radius < hyp && circles(mid + 1).radius >= hyp)
        return circles(mid + 1)
      else {
        if (circle.radius > hyp)
          return binarySearch(l, mid)
        else {
          return binarySearch(mid, r)
        }
      }
    }
    if (circles(0).radius >= hyp)
      return circles(0)
    else if (circles(circles.length - 1).radius == hyp)
      return circles(circles.length - 1)
    else if (circles(circles.length - 1).radius < hyp)
      return null
    return binarySearch(0, circles.length)
  }

  def findFirstLesserCircle(hyp: Int, circles: Array[Circle]): Circle = {
    def binarySearch(l: Int, r: Int): Circle = {
      if (l >= r)
        return null
      val mid = (l + r) / 2
      val circle = circles(mid)
      if (circles(mid - 1).radius <= hyp && circle.radius > hyp)
        return circles(mid - 1)
      else if (circle.radius <= hyp && circles(mid + 1).radius > hyp)
        return circle
      else {
        if (circle.radius > hyp)
          return binarySearch(l, mid)
        else {
          return binarySearch(mid, r)
        }
      }
    }
    if (circles(0).radius > hyp)
      return null
    else if (circles(0).radius == hyp)
      return circles(0)
    else if (circles(circles.length - 1).radius <= hyp)
      return circles(circles.length - 1)
    return binarySearch(0, circles.length)
  }

  def buildNestedCirclesMap(circles: Array[Circle]): HashMap[Int, Int] = {
    val it = circles.iterator
    var ncircles = 0
    def recursiveMap(nestedCircles: HashMap[Int, Int], prevCircleRadius: Int): HashMap[Int, Int] = {
      if (it.isEmpty)
        return nestedCircles
      val currCircle = it.next
      if (currCircle.radius == prevCircleRadius)
        return recursiveMap(nestedCircles, currCircle.radius)
      ncircles += 1
      nestedCircles.put(currCircle.radius, ncircles)
      return recursiveMap(nestedCircles, currCircle.radius)
    }
    return recursiveMap(HashMap[Int, Int](), -1)
  }

  def readLines(n: Int): ListBuffer[Line] = {
    val lb = ListBuffer[Line]()
    for (i <- 0 until n) {
      val line = readLine.split(" ") match {
        case Array(x1, y1, x2, y2) => {
          val p1 = Point(x1.toLong, y1.toLong)
          val p2 = Point(x2.toLong, y2.toLong)
          if (p1.x * p1.x + p1.y * p1.y < p2.x * p2.x + p2.y * p2.y)
            Line(p1, p2)
          else Line(p2, p1)
        }
      }
      lb += line
    }
    return lb
  }

}
