package HakerEarth

import collection.mutable.Stack
import org.scalatest._


class HeapSpec extends FlatSpec with Matchers {

  "A Heap" should "return single element" in {
    val heap = MaxHeap[Int](10)
    heap.insert(1)
    heap.top() should be(1)
    heap.isEmpty should be(true)
    heap.getSize should be(0)
  }

  it should "should return Max Element" in {
    val heap = MaxHeap[Int](10)
    heap.insert(1)
    heap.insert(50)
    heap.insert(25)
    heap.insert(70)
    heap.insert(2)
    heap.top should be(70)
    heap.top should be(50)
    heap.top should be(25)
    heap.top should be(2)
    heap.top should be(1)
  }

  it should "be empty" in {
    val heap = MaxHeap[Int](0)
    heap.getSize should be(0)
    heap.isEmpty should be(true)
  }

  it should "throw IndexOutOfBoundsException if the amount of elements more than allowed Max Size" in {
    val heap = MaxHeap[Int](1)
    heap.insert(1)
    a[IndexOutOfBoundsException] should be thrownBy {
      heap.insert(1)
    }
  }

  it should "handle same elements" in {
    val heap = MaxHeap[Int](5)
    heap.insert(1)
    heap.insert(1)
    heap.top should be(1)
    heap.top should be(1)
  }

}
