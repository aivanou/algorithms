import rx.Observable;
import rx.Subscriber;

import java.util.*;
import java.util.concurrent.*;

public class Worksheet1 {

//    public static void main(String[] args) {
//        ExecutorService exec = Executors.newCachedThreadPool();
//        Future<String> f = exec.submit(new Task());
//        Observable.from(f).subscribe(str -> System.out.println(str));
//
//        customSync().subscribe(v -> System.out.println(v));
//        Observable.from(new ArrayList<Object>()).flatMap();
//
//        Observable.create(t -> t.onNext(1)).map(v -> "" + v).subscribe(v -> System.out.println(v));
//    }

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

        static enum Types {
            type1, type2
        }

        public static void main(String[] args) {
//            TreeSet<String> set = new TreeSet<>();
//
//            set.add("JFK");
//            set.add("SFO");
//            System.out.println(set);
            System.out.println(Types.valueOf("type11"));
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
