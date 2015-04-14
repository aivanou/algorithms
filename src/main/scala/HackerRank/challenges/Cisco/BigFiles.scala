import scala.collection.mutable._
import scala.collection.immutable.List

/*
* https://www.hackerrank.com/contests/software-challenge/challenges/big-file-system-search/copy-from/3073718
* */
object BigFiles {

  def main(args: Array[String]) {
    solve(args)
  }

  def solve(args: Array[String]) {

    val files = ListBuffer[List[Int]]()
    (1 to readLine.toInt).foreach {
      _ => {
        files += readLine.split(" ").map(s => s.toInt).toList.drop(1)
      }
    }
    val queries = ListBuffer[(Int, List[Int])]()
    (1 to readLine.toInt).foreach {
      _ => {
        queries += (readLine.split(" ").map(s => s.toInt).toList.splitAt(1) match {
          case (List(x), lst: List[Int]) => (x, lst.drop(1))
        })
      }
    }
    val index = HashMap[Int, HashSet[Int]]() // Term => fileId
    val freqIndex = HashMap[Int, HashMap[Int, Int]]() //fileId => Term => Frequency
    for (i <- 1 to files.size) {
      freqIndex.put(i, HashMap[Int, Int]())
      for (term <- files(i - 1)) {
        index.get(term) match {
          case Some(set) => index.put(term, set += i)
          case None => {
            index.put(term, HashSet[Int](i))
          }
        }
        freqIndex.get(i) match {
          case Some(doc) => {
            doc.get(term) match {
              case Some(freq) => {
                doc.put(term, freq + 1)
              }
              case None => {
                doc.put(term, 1)
              }
            }
          }
        }
      }
    }
    for ((qtype, query) <- queries) {
      qtype match {
        case 1 => println(queryAll(index, freqIndex, query).size)
        case 2 => println(queryAny(index, freqIndex, query).size)
        case 3 => println(querySome(index, freqIndex, query).size)
      }
    }
  }

  def queryAll(index: HashMap[Int, HashSet[Int]], freqIndex: HashMap[Int, HashMap[Int, Int]], query: List[Int]): HashSet[Int] = {
    var ans: HashSet[Int] = null
    val qfreqMap = HashMap[Int, Int]()
    for (term <- query) {
      qfreqMap.get(term) match {
        case Some(freq) => qfreqMap.put(term, freq + 1)
        case None => qfreqMap.put(term, 1)
      }
      index.get(term) match {
        case Some(map) => {
          if (ans == null) ans = map
          else ans = ans & map
        }
        case None => {}
      }
    }
    if (ans == null)
      return HashSet[Int]()
    val res = HashSet[Int]()
    for (fileId <- ans) {
      val freqMap = freqIndex.get(fileId).get
      for (key <- qfreqMap.keySet) {
        val qcount = qfreqMap.get(key).get
        val fcount = freqMap.get(key).get
        if (fcount >= qcount)
          res += fileId
      }
    }
    return res
  }

  def queryAny(index: HashMap[Int, HashSet[Int]], freqIndex: HashMap[Int, HashMap[Int, Int]], query: List[Int]): HashSet[Int] = {
    var ans: HashSet[Int] = null
    for (term <- query) {
      index.get(term) match {
        case Some(map) =>
          if (ans == null)
            ans = map
          else ans = ans | map
        case None => {}
      }
    }
    if (ans == null)
      return HashSet[Int]()
    return ans
  }

  def querySome(index: HashMap[Int, HashSet[Int]], freqIndex: HashMap[Int, HashMap[Int, Int]], query: List[Int]): HashSet[Int] = {
    return (queryAny(index, freqIndex, query) &~ queryAll(index, freqIndex, query))
  }
}


