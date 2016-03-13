package org.scala.algorithms

import scala.annotation.tailrec
import scala.collection.mutable

/**
  */
object HuffmanCodes extends App {

  println(buildHuffmanCodes("nsa;utnarsuytharsulthrstnawyunftayowufntyawoufntoawyufthawlf;tdh"))

  def buildHuffmanCodes(text: String): List[(Char, String)] = {
    getCodes(buildTree(countFreqs(text)))
  }

  def countFreqs(text: String): List[(Char, Int)] = {
    val dict = mutable.Map[Char, Int]()
    for (ch <- text) {
      dict.get(ch) match {
        case None => dict.put(ch, 1)
        case Some(t) => dict.put(ch, t)
      }
    }
    return dict.toList
  }

  def buildTree(codes: List[(Char, Int)]): Cell = {
    val queue = mutable.PriorityQueue[(Cell, Int)]()
    codes foreach { case (ch, freq) => queue.enqueue((Leaf(ch), freq)) }
    @tailrec def build(): (Cell, Int) = queue.size match {
      case 1 => queue.dequeue()
      case _ => {
        val left = queue.dequeue()
        val right = queue.dequeue()
        left._1.bit = 0
        right._1.bit = 1
        queue.enqueue((Node(left._1, right._1), left._2 + right._2))
        build()
      }
    }
    return build()._1
  }

  def getCodes(cell: Cell): List[(Char, String)] = {
    val stack = mutable.Stack[Byte]()
    def traverse(cell: Cell): List[(Char, String)] = cell match {
      case n: Node => {
        stack.push(n.bit)
        val lst = traverse(n.left) ::: traverse(n.right)
        stack.pop()
        return lst
      }
      case l: Leaf => {
        stack.push(l.bit)
        val lst = List((l.ch, stack.foldLeft("")((str, b: Byte) => str + b).reverse.substring(1)))
        stack.pop()
        return lst
      }
    }
    traverse(cell)
  }

  implicit def ordered(t: (Cell, Int)): Ordered[(Cell, Int)] = new Ordered[(Cell, Int)] {
    override def compare(that: (Cell, Int)): Int = -Integer.compare(t._2, that._2)
  }

  abstract class Cell {
    var bit: Byte = 0
  }

  case class Node(left: Cell, right: Cell) extends Cell

  case class Leaf(ch: Char) extends Cell

}
