package org.scala.algorithms

import scala.StringBuilder
import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.StdIn._

/**
  */
object LongestCompoundWord extends App {

  val words = List[String]("cat", "catdog", "dogcat1", "rat", "mouth", "moun", "mouncatrar", "mounratdogcat").sorted

  println(getMaxCompoundWord(words))

  def getMaxCompoundWord(words: List[String]): String = {
    val q = new mutable.Queue[(String, String)]()
    var maxWord = ""
    val trie = new Trie()
    for (word <- words) {
      trie.add(word)
      val foundWords = trie.getWordsOnPath(word)
      for (found <- foundWords) {
        val suffix = word.substring(found.length)
        if (suffix != 0) {
          q.enqueue((word, suffix))
        }
      }
    }
    while (!q.isEmpty) {
      val (currWord, currSuffix) = q.dequeue()
      if (currSuffix.isEmpty) {
        maxWord = if (currWord.length > maxWord.length) currWord else maxWord
      }
      for (foundSuffix <- trie.getWordsOnPath(currSuffix)) {
        if (foundSuffix.length > 0) {
          q.enqueue((currWord, currSuffix.substring(foundSuffix.length)))
        }
      }
    }
    return maxWord
  }
}

case class TrieNode(nodes: ListBuffer[TrieNode], v: Char, hasWord: Boolean)

class Trie {
  val root: TrieNode = new TrieNode(ListBuffer[TrieNode](), '-', false)

  def getWordsOnPath(path: String): List[String] = {
    def find(node: TrieNode, word: mutable.StringBuilder, pos: Int): List[String] = {
      if (pos >= path.length) return List[String]()
      for (next <- node.nodes) {
        if (path.charAt(pos) == next.v) {
          word.append(next.v)
          var lst = find(next, word, pos + 1)
          if (next.hasWord) {
            lst = word.toString() :: lst
          }
          word.deleteCharAt(word.length - 1)
          return lst
        }
      }
      return List[String]()
    }
    find(root, new StringBuilder(), 0)
  }

  def add(phrase: String): Unit = {
    def build(node: TrieNode, pos: Int): Unit = {
      if (pos >= phrase.length) return
      for (child <- node.nodes) {
        if (child.v == phrase.charAt(pos)) {
          build(child, pos + 1)
          return
        }
      }
      if (pos == phrase.length - 1) {
        node.nodes += new TrieNode(ListBuffer(), phrase.charAt(pos), true)
        return
      }
      val newNode = new TrieNode(ListBuffer(), phrase.charAt(pos), false)
      node.nodes += newNode
      build(newNode, pos + 1)
    }
    build(root, 0)
  }

  def getMatchingWords(prefix: String): List[String] = {
    val node = findNode(prefix)
    if (node == null) return List()
    return gatherWords(node, prefix)
  }

  def gatherWords(node: TrieNode, prefix: String): List[String] = {
    def build(node: TrieNode, word: StringBuilder): List[String] = {
      var lst = if (node.hasWord) List[String](prefix + word.toString()) else List[String]()
      for (next <- node.nodes) {
        word.append(next.v)
        lst = lst ::: build(next, word)
        word.deleteCharAt(word.length - 1)
      }
      return lst
    }
    build(node, new StringBuilder)
  }

  def findNode(prefix: String): TrieNode = {
    def find(node: TrieNode, pos: Int): TrieNode = {
      if (pos == prefix.length) return node
      for (next <- node.nodes) {
        if (next.v == prefix.charAt(pos))
          return find(next, pos + 1)
      }
      return null
    }
    find(root, 0)
  }

  def print(): Unit = {
    def print(node: TrieNode, d: Int): Unit = {
      println(s"value: ${node.v}, depth: $d word: ${node.hasWord}")
      for (next <- node.nodes)
        print(next, d + 1)
    }
    print(root, 1)
  }
}
