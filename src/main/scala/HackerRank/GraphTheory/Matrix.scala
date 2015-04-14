import scala.collection.mutable._


/**
 * @author Aliaksandr Ivanou tierex02@gmail.com
 *         https://www.hackerrank.com/challenges/matrix
 */

object Matrix {

  def main(args: Array[String]): Unit = {
    val (n, k) = readLine.split(" ") match {
      case Array(a, b) => (a.toInt, b.toInt)
    }
    val graph = Array.fill[ListBuffer[(Int, Int)]](n)(ListBuffer[(Int, Int)]())
    for (i <- 0 until n - 1) {
      val (u, v, cost) = readTripple
      graph(u) += ((v, cost))
      graph(v) += ((u, cost))
    }
    val machines = HashSet[Int]()
    for (i <- 0 until k) {
      machines += readLine.toInt
    }
    solve(graph, machines)
  }

  def solve(graph: Array[ListBuffer[(Int, Int)]], machines: HashSet[Int]): Unit = {
    val visited = Array.fill[Byte](graph.length)(0)
    var minCost = 0
    var removedEdges = HashSet[(Int, Int)]()
    val parents = Array.fill[(Int, Int)](graph.length)((0, 0))
    val minCostToVertex = Array.fill[(Int, Int, Int)](graph.length)(Int.MaxValue, 0, 0)

    def dfs(vertex: Int, cm: Int, visited: Array[Byte], parents: Array[(Int, Int)], minCostToVertex: Array[(Int, Int, Int)]): Unit = {
      visited.update(vertex, 1)
      if (machines.contains(vertex) && cm != vertex) {
        val (cst, v1, v2) = minCostToVertex(vertex)
        var mcst = cst
        if (mcst > parents(vertex)._2) {
          mcst = parents(vertex)._2
        }
        if (!(removedEdges.contains(v1, v2) || removedEdges.contains((v2, v2)))) {
          removedEdges += ((v1, v2))
          minCost += cst
        }
      }
      for (pair <- graph(vertex)) {
        val (u, cost) = pair
        if (visited(u) == 0 && !(removedEdges.contains((u, vertex)) || removedEdges.contains((vertex, u)))) {
          parents.update(u, (vertex, cost))
          if (minCostToVertex(vertex)._1 == Int.MaxValue || minCostToVertex(vertex)._1 > cost)
            minCostToVertex.update(u, (cost, vertex, u))
          else {
            minCostToVertex.update(u, minCostToVertex(vertex))
          }
          dfs(u, cm, visited, parents, minCostToVertex)
        }
      }
      visited.update(vertex, 0)
      parents.update(vertex, (0, 0))
      minCostToVertex.update(vertex, (Int.MaxValue, 0, 0))
    }

    for (machineVertex <- machines) {
      dfs(machineVertex, machineVertex, visited, parents, minCostToVertex)
    }
    println(minCost)
  }

  def findMinEdgeInPath(parents: Array[(Int, Int)], initial: Int, end: Int): (Int, (Int, Int)) = {
    def findMinEdge(currentVertex: Int, currMinCost: Int, edge: (Int, Int)): (Int, (Int, Int)) = {
      if (currentVertex == initial)
        return (currMinCost, edge)
      val (parent, cost) = parents(currentVertex)
      if (currMinCost > cost)
        return findMinEdge(parent, cost, (currentVertex, parent))
      return findMinEdge(parent, currMinCost, edge)
    }
    findMinEdge(end, Int.MaxValue, (-1, -1))
  }

  def readTripple: (Int, Int, Int) = {
    readLine.split(" ") match {
      case Array(a, b, c) => (a.toInt, b.toInt, c.toInt)
    }
  }

}
