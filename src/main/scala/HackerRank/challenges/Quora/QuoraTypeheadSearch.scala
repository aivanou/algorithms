import collection.mutable._

object QuoraTypeheadSearch{

  implicit val searchObjectsOrd = new Ordering[SearchObject] {
    override def compare(x: SearchObject, y: SearchObject): Int = {
      if (x.score.equals(y.score)) {
        return -1 * x.insertTime.compareTo(y.insertTime)
      }
      return -1 * x.score.compareTo(y.score)
    }
  }

  val radixTree = new RadixTree()
  val idRecords = scala.collection.mutable.HashMap[String, SearchObject]()

  def executeQuery(query: Query): Unit = {
    if (query.amount == 0)
      println
    else {
      var stime = System.nanoTime()
      val res = radixTree.search(query.terms, query.amount)
      radixTree.updateMap("query:query", stime)
      stime = System.nanoTime()
      res.toList.sorted.slice(0, query.amount).foreach(s => print(s.id + " "))
      radixTree.updateMap("query:sort", stime)
      println()
    }
  }

  def executeWQuery(wquery: WQuery): Unit = {
    implicit val searchObjectsWOrd = new Ordering[SearchObject] {
      override def compare(x: SearchObject, y: SearchObject): Int = {
        val a = -1 * ((tryGet(x), tryGet(y)) match {
          case (Some(xScore), Some(yScore)) =>
            (x.score * xScore).compareTo(y.score * yScore)
          case (Some(xScore), None) =>
            (x.score * xScore).compareTo(y.score)
          case (None, Some(yScore)) =>
            x.score.compareTo(y.score * yScore)
          case (None, None) => x.score.compareTo(y.score)
        })
        if (a == 0)
          return -1 * x.insertTime.compareTo(y.insertTime)
        return a
      }

      def tryGet(obj: SearchObject): Option[Float] = {
        wquery.boosting.get(obj.id) match {
          case None => return wquery.boosting.get(obj.otype)
          case t => return t
        }
      }
    }
//    println(wquery.boosting.mkString("  "))
    radixTree.search(wquery.terms, wquery.amount).toList.sorted(searchObjectsWOrd).slice(0, wquery.amount).foreach(s => print(s.id + " "))
    println()
  }

  def executeDelete(del: Del): Unit = {
    idRecords.get(del.id) match {
      case Some(obj) => {
        radixTree.delete(obj)
        idRecords.remove(del.id)
      }
      case _ => {}
    }
  }

  def executeInsert(add: Add, time: Int): Unit = {
    val sobj = new SearchObject(add.id, add.otype, add.score, add.terms, time)
    idRecords.put(sobj.id, sobj)
    radixTree.insert(sobj)
  }

  def parse(line: String): Command = {

    def parseBoosting(parts: Array[String], startIndex: Int): HashMap[String, Float] = {
      val boostingAmount = parts(startIndex).toInt
      val boostingMap = HashMap[String, Float]()
      (1 to boostingAmount).foreach {
        i => {
          val boosting = parts(startIndex + i).split(':')
          boostingMap.put(boosting(0), boosting(1).toFloat)
        }
      }
      return boostingMap
    }

    def parseText(parts: Array[String], fromIndex: Int): Array[String] = {
      return parts.slice(fromIndex, parts.length)
    }

    val tp = System.nanoTime()
    val parts = line.toLowerCase.split(' ')
    radixTree.updateMap("solution:parseLine", tp)
    parts(0) match {
      case "add" => {
        val t = System.nanoTime()
        val body = parseText(parts, 4)
        radixTree.updateMap("solution:parse:add", t)
        return Add(parts(1), parts(2), parts(3).toFloat, body)
      }
      case "del" => {
        return Del(parts(1))
      }
      case "query" => {
        val t = System.nanoTime()
        val searchText = parseText(parts, 2)
        radixTree.updateMap("solution:parse:query", t)
        return Query(parts(1).toInt, searchText)
      }
      case "wquery" => {
        val t = System.nanoTime()
        val boosting = parseBoosting(parts, 2)
        val searchText = parseText(parts, boosting.size + 3)
        radixTree.updateMap("solution:parse:wquery", t)
        return WQuery(parts(1).toInt, searchText, boosting)
      }
      case _ =>
        return Undefined(line)
    }
  }

  trait Command

  case class Add(otype: String, id: String, score: Float, terms: Array[String]) extends Command

  case class Del(id: String) extends Command

  case class Query(amount: Int, terms: Array[String]) extends Command

  case class WQuery(amount: Int, terms: Array[String], boosting: HashMap[String, Float]) extends Command

  case class Undefined(text: String) extends Command

  case class SearchObject(id: String, otype: String, score: Float, terms: Array[String], insertTime: Int) {
    override lazy val hashCode: Int = scala.util.hashing.MurmurHash3.stringHash(this.id)

    override def equals(obj: scala.Any): Boolean =
      if (obj.isInstanceOf[SearchObject]) {
        return this.id.equals(obj.asInstanceOf[SearchObject].id)
      }
      else return false

    override def toString: String = return id + "  " + terms
  }

  class RadixTree {

    val root = new TreeNode("", ArrayBuffer[TreeNode](), new Leaf(scala.collection.mutable.Set.empty[SearchObject]))

    def delete(searchObject: SearchObject): Unit = {
      searchObject.terms.foreach(word => delete(root, searchObject, word))
    }

    def delete(node: TreeNode, searchObject: SearchObject, term: String): Unit = {
      node.leaf.searchObjects.remove(searchObject)
      if (term.isEmpty) {
        return
      }
      else {
        findChildWithCommonPrefix(node, term, 0, node.children.length) match {
          case (Some(child), _, prefixSize) => {
            delete(child, searchObject, term.substring(prefixSize))
          }
          case _ => {}
        }
      }
    }

    def search(str: Array[String], amount: Int): scala.collection.mutable.Set[SearchObject] = {
      val st = System.nanoTime()
      val r = str.foldLeft[scala.collection.mutable.Set[SearchObject]](null) {
        (set, word) => {
          if (set == null)
            search(word)
          else {
            val newSet = search(word)
            if (newSet.isEmpty || amount == 0)
              return Set[SearchObject]()
            else {
              set & newSet
            }
          }
        }
      }
      updateMap("search:search", st)
      return r
    }

    def search(str: String): scala.collection.mutable.Set[SearchObject] = {
      val r = search(root, str)
      return r
    }

    def search(node: TreeNode, termToSearch: String): scala.collection.mutable.Set[SearchObject] = {
      if (termToSearch.isEmpty) {
        return node.leaf.searchObjects
      }
      else {
        findChildWithCommonPrefix(node, termToSearch, 0, node.children.length) match {
          case (Some(child), _, prefixSize) => {
            return search(child, termToSearch.substring(prefixSize))
          }
          case _ => {
            return scala.collection.mutable.HashSet[SearchObject]()
          }
        }
      }
    }

    def insert(searchObject: SearchObject): Unit = {
      searchObject.terms.foreach(w => insert(w, searchObject))
    }

    def insert(word: String, item: SearchObject): Unit = {
      insert(root, word, item)
    }

    var insertTimeMap = new scala.collection.mutable.HashMap[String, Long]()

    def updateMap(key: String, time: Long): Unit = {
      insertTimeMap.get(key) match {
        case Some(v) => insertTimeMap.put(key, (System.nanoTime() - time) + v)
        case None => insertTimeMap.put(key, System.nanoTime() - time)
      }
    }

    def insert(node: TreeNode, term: String, item: SearchObject): Unit = {
      val stime0 = System.nanoTime()
      if (!node.eq(root))
        node.leaf.searchObjects += item
      updateMap("insert:leaf", stime0)
      if (term.isEmpty) {
        return
      }
      val stime = System.nanoTime()
      findChildWithCommonPrefix(node, term, 0, node.children.length) match {
        case (Some(child), _, prefixSize) => {
          val restChildTerm = child.term.substring(prefixSize)
          val restTerm = term.substring(prefixSize)
          updateMap("insert:findCommonPrefix", stime)
          if (restChildTerm.isEmpty) {
            insert(child, restTerm, item)
          }
          else {
            val stime1 = System.nanoTime()
            val prefix = term.substring(0, prefixSize)
            val newNode = new TreeNode(restChildTerm, child.children, child.leaf)
            child.term = prefix
            child.children = ArrayBuffer[TreeNode](newNode)
            child.leaf = new Leaf(child.leaf.searchObjects.clone())
            updateMap("insert:splitNodne", stime1)
            insert(child, restTerm, item)
          }
        }
        case (None, index, _) => {
          updateMap("insert:findCommonPrefix", stime)
          val stime2 = System.nanoTime()
          //          new java.util.HashSet[Int]()
          val newNode = new TreeNode(term, ArrayBuffer[TreeNode](), new Leaf(scala.collection.mutable.HashSet[SearchObject](item)))
          node.children.insert(index, newNode)
          updateMap("insert:newNode", stime2)

        }
      }
    }

    def findChildWithCommonPrefix(node: TreeNode, word: String, l: Int, r: Int): (Option[TreeNode], Int, Int) = {
      if (l >= r)
        return (None, l, -1)
      val mid = (l + r) / 2
      val child = node.children(mid)
      if (word.charAt(0) == child.term.charAt(0)) {
        val prefixSize = commonPrefix(child.term, word, 0, Math.min(child.term.length, word.length))
        return (Some(child), l, prefixSize)
      }
      else if (child.term.charAt(0) > word.charAt(0)) {
        return findChildWithCommonPrefix(node, word, l, mid)
      }
      else return findChildWithCommonPrefix(node, word, mid + 1, r)
    }

    def commonPrefix(word1: String, word2: String, ind: Int, len: Int): Int = {
      if (ind == len || word1.charAt(ind) != word2.charAt(ind))
        return ind
      else return commonPrefix(word1, word2, ind + 1, len)
    }

    def printTree(): Unit = {
      printTree(root, 1)
    }

    def printTree(node: Node, depth: Int): Unit = {
      (1 to depth).foreach(_ => print(" "))
      node match {
        case l: Leaf => {
          println(" leaf: objects: " + l.searchObjects.size)
        }
        case n: TreeNode => {
          println(" node: " + n.term + "  on depth:  " + depth)
          printTree(n.leaf, depth + 1)
          for (child <- n.children)
            printTree(child, depth + 1)
        }
      }
    }

    abstract class Node(_term: String) {
      var term = _term


      def canEqual(other: Any): Boolean = other.isInstanceOf[Node]

      override def equals(other: Any): Boolean = other match {
        case that: Node =>
          (that canEqual this) &&
            term == that.term
        case _ => false
      }

      override lazy val hashCode: Int = scala.util.hashing.MurmurHash3.stringHash(this.term)
    }

    class Leaf(_searchObjects: Set[SearchObject]) extends Node("") {
      var searchObjects = _searchObjects

      override def toString: String = s"Leaf($term)"
    }

    class TreeNode(_term: String, _children: ArrayBuffer[TreeNode], _leaf: Leaf) extends Node(_term) {
      var children = _children;
      var leaf = _leaf


      override def toString = s"TreeNode($term)"

    }

  }

  def main(args: Array[String]): Unit = {
//    val st = System.nanoTime()
//    fromFile("input09.txt")
//    println((System.nanoTime() - st) / 1000000000)
    fromCmd()
  }


  def updateTimeMap(timeMap: HashMap[String, Long], key: String, time: Long): Unit = {
    timeMap.get(key) match {
      case Some(v) => timeMap.put(key, time + v)
      case None => timeMap.put(key, time)
    }
  }

  val timeMap = scala.collection.mutable.HashMap[String, Long]()
  var queries = 1
  var time = 1

  def fromCmd(): Unit = {
    val amount = readLine.toInt
    (1 to amount).foreach {
      case i => {
        executeCommand(parse(readLine))
        time += 1
      }
    }
  }

  def fromFile(filename: String): Unit = {
    for (line <- scala.io.Source.fromFile(filename).getLines()) {
      val st = System.nanoTime()
      val p = parse(line)
      radixTree.updateMap("solution:parse", st)
      executeCommand(p)
      time += 1
    }
//    printMap(timeMap)
//    printMap(radixTree.insertTimeMap)
  }

  def executeCommand(cmd: Command): Unit = {
    var startTime = System.nanoTime()
    cmd match {
      case add: Add => {
        executeInsert(add, time)
        updateTimeMap(timeMap, "add", System.nanoTime() - startTime)
      }
      case query: Query => {
//        println(queries + "   Searching: " + query.terms.mkString(" "))
        queries += 1
        startTime = System.nanoTime()
        executeQuery(query)
        updateTimeMap(timeMap, "query", System.nanoTime() - startTime)

      }
      case del: Del => {
        executeDelete(del)
        updateTimeMap(timeMap, "del", System.nanoTime() - startTime)
      }
      case wquery: WQuery => {
//        println(queries + "   WSearching: " + wquery.terms.mkString(" "))
        queries += 1
        executeWQuery(wquery)
        updateTimeMap(timeMap, "wquery", System.nanoTime() - startTime)
      }
      case _ => {}
    }
  }

  implicit val ord = new Ordering[Int] {
    override def compare(x: Int, y: Int): Int = ???
  }

  def printMap(map: scala.collection.mutable.HashMap[String, Long]): Unit = {
    val a = new scala.collection.mutable.PriorityQueue[Int]()
    map.foreach {
      case (k, v) => println(k + " " + v / 1000000000f)
    }
    println()
  }
}
