import rx.Observable;
import rx.Subscriber;

import java.util.*;
import java.util.concurrent.*;

public class Worksheet1 {

// Given a stream of unsorted integers, find the median element in sorted order at any given time.
// http://www.ardendertat.com/2011/11/03/programming-interview-questions-13-median-of-integer-stream/
    public static class MedianOfIntegerStream {

        public Queue<Integer> minHeap;
        public Queue<Integer> maxHeap;
        public int numOfElements;

        public MedianOfIntegerStream() {
            minHeap = new PriorityQueue<Integer>();
            maxHeap = new PriorityQueue<Integer>(10, new MaxHeapComparator());
            numOfElements = 0;
        }

        public void addNumberToStream(Integer num) {
            maxHeap.add(num);
            if (numOfElements % 2 == 0) {
                if (minHeap.isEmpty()) {
                    numOfElements++;
                    return;
                } else if (maxHeap.peek() > minHeap.peek()) {
                    Integer maxHeapRoot = maxHeap.poll();
                    Integer minHeapRoot = minHeap.poll();
                    maxHeap.add(minHeapRoot);
                    minHeap.add(maxHeapRoot);
                }
            } else {
                minHeap.add(maxHeap.poll());
            }
            numOfElements++;
        }

        public Double getMedian() {
            if (numOfElements % 2 != 0)
                return new Double(maxHeap.peek());
            else
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }

        private class MaxHeapComparator implements Comparator<Integer> {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        }
    }

    static Observable<String> customSync() {
        return Observable.create(subscriber -> {
            for (int i = 0; i < 100; ++i)
                if (!subscriber.isUnsubscribed())
                    subscriber.onNext(i + "");
        });
    }

    static class Task implements Callable<String> {

        @Override public String call() throws Exception {
            BlockingDeque<Integer> q = new LinkedBlockingDeque<>();
            try {
                q.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {

            }
            return "test string";
        }
    }

}
