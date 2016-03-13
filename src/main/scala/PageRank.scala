import java.io.{FileInputStream, BufferedInputStream}
import java.util.zip.GZIPInputStream

import scala.collection.mutable.ListBuffer

object PageRank {

  class Vertex {
    var inDegree = 0
    var outDegree = 0
    var to = ListBuffer[Int]()
  }

  class Graph(lines: Iterator[String]) {
    val vertexList = scala.collection.mutable.Map[Int, Vertex]()

    var max = 0
    parse()

    final def parse(): Unit = {
      for (line <- lines) {
        if (!line.startsWith("#")) {
          val values = line.split("[ \t]+").map(_.toInt)
          val (from, to) = (values(0), values(1))
          if (max < from || max < to)
            max = Math.max(from, to)
          if (!vertexList.contains(from))
            vertexList(from) = new Vertex()
          if (!vertexList.contains(to))
            vertexList(to) = new Vertex()
          vertexList(from).to += to
          vertexList(from).outDegree += 1
          vertexList(to).inDegree += 1
        }
      }
    }


    def print(): Unit = {
      for (vertex <- vertexList.keys) {
        println(vertex)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val file = "/Users/aivanou/study/data/web-Google.txt.gz"
    val graph = new Graph(scala.io.Source.fromInputStream(new GZIPInputStream(new BufferedInputStream(new FileInputStream(file)))).getLines())
    //    graph.print()
    pageRank(graph, 0.8, 0.000001);
  }

  def pageRank(g: Graph, beta: Double, error: Double): Unit = {
    val size = g.vertexList.size
    println(size)
    var r = Array.fill[Double](g.max + 1)(1.0D / g.max + 1)
    var rNext = Array.fill[Double](g.max + 1)(0)
    var conv: Double = 0.0D
    var iteration = 1
    do {
      println(s"iteration: $iteration")
      iteration += 1
      for (vertex <- g.vertexList.keys) {
        for (to <- g.vertexList(vertex).to) {
          val t = g.vertexList(vertex).to.length
          rNext(to) += beta * r(vertex) / t
        }
        if (g.vertexList(vertex).inDegree == 0)
          rNext(vertex) = 0
      }
      val sum = rNext.sum
      for (i <- 0 until rNext.length) {
        rNext(i) += (1 - sum) / size
      }
      conv = convergence(r, rNext)
      println(s"convergence: $conv")
      r = rNext
      rNext = Array.fill[Double](g.max + 1)(0)
    }
    while (conv > error)
  }

  def convergence(r: Array[Double], rNext: Array[Double]): Double = {
    var res: Double = 0
    for (i <- 0 until r.length) {
      res += (Math.abs(r(i) - rNext(i)))
    }
    return res
  }
}
