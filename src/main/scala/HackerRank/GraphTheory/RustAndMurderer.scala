import scala.collection.mutable._

/**
 * @author Aliaksandr Ivanou tierex02@gmail.com
 * https://www.hackerrank.com/challenges/rust-murderer
 */

object RustAndMurderer {

//  def main(args: Array[String]): Unit = {
//    (1 to readLine.toInt).foreach(_ => solve)
//  }

  def solve(): Unit = {
    val (n, m) = readPair
    val graph = Array.fill[ListBuffer[Int]](n + 1)(ListBuffer[Int]())
    for (i <- 1 to m) {
      val (u, v) = readPair
      graph(u) += v
      graph(v) += u
    }
    bfs(graph, n, readLine.toInt)
  }

  def bfs(graph: Array[ListBuffer[Int]], n: Int, sv: Int): Unit = {
    val visited = Array.fill[Boolean](n + 1)(false)
    val queue = Queue[Int]()
    val dists = Array.fill[Int](n + 1)(0)
    queue.enqueue(sv)
    var complementVertices = HashSet[Int]()
    var visitedVertices = HashSet[Int]()
    for (i <- 1 to n) {
      if (i != sv)
        complementVertices += i
    }
    while (!queue.isEmpty) {
      val v = queue.dequeue()
      visited.update(v, true)
      for (u <- graph(v)) {
        if (!visited(u)) {
          complementVertices.remove(u)
          visitedVertices += u
        }
      }
      for (u <- complementVertices) {
        visited.update(u, true)
        dists.update(u, dists(v) + 1)
        queue.enqueue(u)
      }
      complementVertices = visitedVertices.clone()
      visitedVertices.clear()
    }
    for (i <- 1 to n) {
      if (i != sv)
        print(dists(i) + " ")
    }
    println()
  }

  def readPair: (Int, Int) = {
    readLine.split(" ") match {
      case Array(v1: String, v2: String) => (v1.toInt, v2.toInt)
    }
  }
}
