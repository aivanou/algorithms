package org.java.algorithms.heaps;

/**
 */
public class Heap {
    public static void main(String[] args) {
        MinHeap h = new MinHeap();
        h.add(4);
        h.add(1);
        h.min();
        h.print();
    }

    private static class MinHeap {
        private int[] h = new int[1000];
        private int len;

        public void add(int el) {
            if (len == 0) {
                h[1] = el;
                len++;
                return;
            }
            len++;
            h[len] = el;
            balance(len);
        }

        public int min() {
            if (len == 1) {
                len = 0;
                return h[1];
            }
            int el = h[1];
            h[1] = h[len];
            len--;
            heapify(1);
            return el;
        }

        private void heapify(int ind) {
            int min = ind;
            int l = left(ind);
            int r = right(ind);
            if (l <= len && h[l] < h[min]) {
                min = l;
            }
            if (r <= len && h[r] < h[min]) {
                min = r;
            }
            if (min != ind) {
                heapify(min);
            }
        }

        public void print() {
            for (int i = 1; i <= len; ++i) {
                System.out.print(h[i] + " ");
            }
            System.out.println();
        }

        private void balance(int ind) {
            if (ind == 1)
                return;
            if (h[parent(ind)] > h[ind]) {
                swap(parent(ind), ind);
                balance(parent(ind));
            }
        }

        void swap(int i, int j) {
            int t = h[i];
            h[i] = h[j];
            h[j] = t;
        }

        int right(int ind) {
            return ind * 2;
        }

        int left(int ind) {
            return ind * 2 + 1;
        }

        int parent(int ind) {
            return ind / 2;
        }
    }
}
