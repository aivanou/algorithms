package HakerEarth

import scala.reflect.ClassTag

//min-max heap

trait Heap[T] {

  def insert(elem: T): Unit

  def top(): T

  def isEmpty: Boolean

  def getSize: Int

}

object MaxHeap {
  def apply[T](size: Int)(implicit m: ClassTag[T], ord: Ordering[T]): Heap[T] = {
    return new MaxHeap[T](size, new Array[T](size +1))(ord)
  }

  def apply[T](arr: Array[T], maxSize: Int)(implicit m: ClassTag[T], ord: Ordering[T]): Heap[T] = {
    val heap = apply[T](maxSize)
    for (el <- arr)
      heap.insert(el)
    return heap
  }
}

class MaxHeap[T](mSize: Int, heap: Array[T])(implicit ordering: Ordering[T]) extends Heap[T] {

  implicit def compare(e1: T, e2: T) = ordering.compare(e1, e2)

  private var size = 0
  private val maxSize = mSize

  override def insert(elem: T): Unit = {
    if (size > maxSize)
      throw new IndexOutOfBoundsException("The heap is full, wiht max size :" + maxSize)
    size += 1
    heap(size) = elem
    heapify(heap, size)
  }

  private def heapify(heap: Array[T], elIndex: Int): Unit = {
    if (elIndex <= 1)
      return
    val pr = parent(elIndex)
    if (compare(heap(pr), heap(elIndex)) < 0) {
      swap(heap, pr, elIndex)
    }
    heapify(heap, pr)
  }

  override def top(): T = {
    val el = heap(1)
    heap(1) = heap(size)
    size -= 1
    downHeap(heap, size, 1)
    return el
  }

  private def downHeap(heap: Array[T], size: Int, elInd: Int): Unit = {
    val el = heap(elInd)
    val (l, r) = (left(elInd), right(elInd))
    val max = (size >= l, size >= r) match {
      case (true, true) => if (compare(heap(l), heap(r)) > 0) l else r
      case (true, false) => l
      case (false, true) => r
      case (false, false) => return
    }
    if (compare(heap(max), el) < 0)
      return
    swap(heap, elInd, max)
    downHeap(heap, size, max)
  }

  override def isEmpty() = size == 0

  private def swap(heap: Array[T], i1: Int, i2: Int): Unit = {
    val t = heap(i1)
    heap(i1) = heap(i2)
    heap(i2) = t
  }

  private def parent(ind: Int) = compute(ind)(v => v / 2)

  private def left(ind: Int) = compute(ind)(v => v * 2)

  private def right(ind: Int) = compute(ind)(v => v * 2 + 1)

  private def compute(ind: Int)(f: (Int) => Int) = f(ind)

  override def getSize: Int = return size
}