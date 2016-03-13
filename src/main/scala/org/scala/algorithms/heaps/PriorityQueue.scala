package org.scala.algorithms.heaps

import scala.collection.mutable

/**
  */
object PriorityQueue {

  def main(args: Array[String]): Unit = {
    val q = new PriorityQueue()
    val arr = Array[Int](4, 56, 8, 1, 45, 7, 5, 2, 1, 54, 6, 88, 1)
    arr foreach (el => q.add(el))
    q.levelPrint()
    println(q.get())
    println(q.get())
    println(q.get())
    println(q.get())
    println(q.get())
    println(q.get())
  }

  class PriorityQueue {
    protected var array: Array[Int] = new Array[Int](256)
    private[this] var qLength = 0

    def add(el: Int): Unit = {
      qLength += 1
      array(qLength) = el
      updateNodes(qLength)
    }

    private[this] def updateNodes(pos: Int): Unit = {
      if (pos <= 1) return
      val pr = parent(pos)
      if (array(pr) < array(pos)) {
        swap(array, pr, pos)
        updateNodes(pr)
      }
    }

    def inorder(): Unit = {
      def inorderPrint(pos: Int): Unit = {
        if (pos > qLength) return
        inorderPrint(left(pos))
        println(array(pos))
        inorderPrint(right(pos))
      }
      inorderPrint(1)
    }

    def levelPrint(): Unit = {
      if (qLength == 0)
        return
      val q = mutable.Queue[(Int, Int)]((1, 1))
      var prevPosLevel = 1
      def printNode(): Unit = q.isEmpty match {
        case false => {
          val (pos, level) = q.dequeue()
          if (pos >= qLength) {
            printNode()
            return
          }
          if (level != prevPosLevel) {
            println()
            prevPosLevel = level
          }
          print(s"${array(pos)} ")
          q.enqueue((left(pos), level + 1))
          q.enqueue((right(pos), level + 1))
          printNode()
        }
        case true => return
      }
      printNode()
      println()
    }

    def get(): Int = {
      if (qLength == 0) return -1
      val el = array(1)
      array(1) = array(qLength)
      qLength -= 1
      rebuild(1)
      return el
    }

    private[this] def rebuild(pos: Int): Unit = {
      if (pos > qLength) return
      val l = left(pos)
      val r = right(pos)
      var vPos = pos
      if (l <= qLength && array(l) > array(vPos)) {
        vPos = l
      }
      if (r <= qLength && array(r) > array(vPos)) {
        vPos = r
      }
      if (vPos != pos) {
        swap(array, vPos, pos)
        rebuild(vPos)
      }
    }

    def parent(v: Int) = v / 2

    def left(v: Int) = v * 2

    def right(v: Int) = v * 2 + 1
  }

  def swap[T](arr: Array[T], i1: Int, i2: Int): Unit = {
    val t = arr(i1)
    arr(i1) = arr(i2)
    arr(i2) = t
  }
}