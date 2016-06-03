import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

import scala.io.Source
import scala.collection.mutable._
import util.Random

object FeedOptimizer {

  implicit val storyOrd = new Ordering[Story] {
    override def compare(x: Story, y: Story): Int = {
      return x.id.compareTo(y.id)
    }
  }

  def containsStory(stories: ListBuffer[Story], story: Story): (Boolean, Int) = {
    var mscore = 0
    for (storyInList <- stories) {
      mscore += storyInList.score
      if (story.id == storyInList.id)
        return (true, 0)
    }
    return (false, mscore)
  }

  def buildStoryArray(stories: List[Story], browserHeight: Int): (Int, List[Story]) = {
    if (stories.isEmpty)
      return (0, List[Story]())
    val storiesHeights = scala.collection.mutable.HashMap[Int, ListBuffer[ListBuffer[Story]]]()
    val scoreHeight = Array.fill[Int](browserHeight + 1)(0)
    var maxScore = 0
    var heightMaxScore = 0
    for (story <- stories) {
      if (story.score > maxScore) {
        maxScore = story.score
        heightMaxScore = story.height
      }
      storiesHeights.get(story.height) match {
        case Some(lst) => lst.append(ListBuffer[Story](story))
        case None => {
          val lst = ListBuffer[Story](story)
          val levelLst = ListBuffer[ListBuffer[Story]]()
          levelLst.append(lst)
          storiesHeights.put(story.height, levelLst)
        }
      }
    }
    for (currHeight <- 1 to browserHeight) {
      var maxCurrentScore = 0
      for (story <- stories) {
        if (story.height < currHeight) {
          storiesHeights.get(currHeight - story.height) match {
            case Some(storyListsOnHeight) => {
              for (storyList <- storyListsOnHeight) {
                val pair = containsStory(storyList, story)
                if (!pair._1) {
                  if (maxCurrentScore < pair._2 + story.score) {
                    maxCurrentScore = pair._2 + story.score
                    storiesHeights.get(currHeight) match {
                      case Some(currHeightStore) => {
                        val currHeightListLevel = storyList.clone()
                        currHeightListLevel.append(story)
                        currHeightStore.append(currHeightListLevel)
                      }
                      case None => {
                        val currStoryLists = ListBuffer[ListBuffer[Story]]()
                        val currStoryList = storyList.clone()
                        currStoryList.append(story)
                        currStoryLists.append(currStoryList)
                        storiesHeights.put(currHeight, currStoryLists)
                      }
                    }
                  }
                }
              }
            }
            case None => {}
          }
        }
      }
      if (maxCurrentScore > maxScore) {
        heightMaxScore = currHeight
        maxScore = maxCurrentScore
      }
    }
    val outStories = storiesHeights.get(heightMaxScore).get.sortWith {
      case (lst1, lst2) => {
        if (lst1.size == lst2.size) {
          compareStoryLists(lst1.sorted.toList, lst2.sorted.toList)
        }
        else lst1.size < lst2.size
      }
    }.iterator.next.sorted
    return (maxScore, outStories.toList);
  }

  def compareStoryLists(lst1: List[Story], lst2: List[Story]): Boolean = {
    val it1 = lst1.iterator
    val it2 = lst2.iterator
    while (!it1.isEmpty && it2.isEmpty) {
      val s1 = it1.next
      val s2 = it2.next()
      if (!(s1.id == s2.id)) {
        return s1.id >= s2.id
      }
    }
    return true
  }

  def solveFromFile(fname: String): Unit = {
    val reader = Source.fromFile(fname).bufferedReader();
    val params = reader.readLine().split(" +")
    val timeWindow = params(1).toInt
    val browserHeight = params(2).toInt
    var index = 1
    val storyQueue = scala.collection.mutable.Queue[Story]()
    var line = reader.readLine()
    while (line != null) {
      //    for (i <- 0 until nLines) {
      parse(line, index) match {
        case story: Story => {
          storyQueue.enqueue(story)
          index += 1
        }
        case Reload(reloadTime) => {
          cleanQueue(storyQueue, reloadTime - timeWindow)
          reload(storyQueue.toList, browserHeight)
        }
      }
      line = reader.readLine()
    }
  }

  def solveFromConsole(): Unit = {
    val params = readLine().split(" +")
    val nLines = params(0).toInt
    val timeWindow = params(1).toInt
    val browserHeight = params(2).toInt
    var index = 1
    val storyQueue = scala.collection.mutable.Queue[Story]()
    for (i <- 0 until nLines) {
      parse(readLine(), index) match {
        case story: Story => {
          storyQueue.enqueue(story)
          index += 1
        }
        case Reload(reloadTime) => {
          cleanQueue(storyQueue, reloadTime - timeWindow)
          reload(storyQueue.toList, browserHeight)
        }
      }
    }
  }

  def cleanQueue(stories: scala.collection.mutable.Queue[Story], minReloadTime: Int): Unit = {
    while (!stories.isEmpty) {
      if (stories.head.publishTime >= minReloadTime) {
        return
      }
      stories.dequeue()
    }
  }

  def reload(stories: List[Story], browserHeight: Int): Unit = {
    val pair = buildStoryArray(stories, browserHeight)
    print(pair._1 + " " + pair._2.length + " ")
    for (story <- pair._2) {
      print(story.id + " ")
    }
    println()

  }

  def parse(str: String, id: Int): Event = {
    val parts = str.trim.split("[ \t]+")
    str.trim.toLowerCase.charAt(0) match {
      case 's' =>
        return Story(id, parts(1).toInt, parts(2).toInt, parts(3).toInt)
      case 'r' =>
        return Reload(parts(1).toInt)
    }
  }

  trait Event

  case class Reload(reloadTime: Int) extends Event

  case class Story(id: Int, publishTime: Int, score: Int, height: Int) extends Event

    def main(args: Array[String]): Unit = {
      val fn = "/home/aliaksandr/study/coursera/reactive_programming/quickcheck/stories.txt"
  //    generateData(fn)
  //    val data = ListBuffer[String]()
  //    for (line <- Source.fromFile(fn).getLines()) {
  //      data.append(line)
  //    }
      solveFromFile(fn)
    }

  def generateData(file: String): Unit = {
    val amount = 10000
    val f = new File(file)
    if (f.exists())
      f.delete()
    f.createNewFile()
    val writer = new PrintWriter(new FileWriter(file, true));
    writer.println("1000 30 800");
    var minTime = 1
    for (i <- 0 until amount) {
      if (new Random().nextInt(10) >= 7) {
        val reload = randomReload(minTime, minTime + 10)
        minTime = reload.reloadTime + 1
        writer.println("R " + reload.reloadTime)
      }
      else {
        val story = randomStory(i, 30, 10, minTime, minTime + 2)
        minTime = story.publishTime + 1
        writer.println("S " + story.publishTime + " " + story.score + " " + story.height)
      }
    }
    writer.close()
  }

  def randomReload(min: Int, max: Int): Reload = {
    return Reload(random(min, max))
  }

  def randomStory(id: Int, maxHeight: Int, minHeight: Int, minPublishTime: Int, maxPublishTime: Int): Story = {
    return Story(id, random(minPublishTime, maxPublishTime), random(10, 200), random(minHeight, maxHeight))
  }

  def random(min: Int, max: Int): Int = {
    return new Random().nextInt(max - min) + min
  }

}
