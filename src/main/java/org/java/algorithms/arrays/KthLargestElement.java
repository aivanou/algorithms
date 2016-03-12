package org.java.algorithms.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 */
public class KthLargestElement {
    public static void main(String[] args) {
        int[] arr = new int[] { 4, 5, 1, 6, 9, 33, 1, 4, 5, 1, 12, 6, 8, 1 };
        System.out.println(findKthLargest1(arr, 0, arr.length - 1, 2));
    }

    private static int findKthLargest2(int[] arr, int l, int r, int k) {
        if (l >= r)
            return arr[l];
        int pivot = findPivot(arr, l, r);
        if (pivot == k) {
            return arr[k];
        } else if (pivot > k) {
            return findKthLargest2(arr, l, pivot - 1, k);
        } else {
            return findKthLargest2(arr, pivot + 1, r, k);
        }
    }

    private static int findPivot(int[] arr, int l, int r) {
        List<Integer> medians = new ArrayList<>();
        int batch = 5;
        int parts = (r - l) / batch;
        for (int i = 0; i < parts; ++i) {
            Arrays.sort(arr, i * batch, (i + 1) * batch);
            medians.add(arr[i * batch + batch / 2]);
        }
        if ((r - l) % batch != 0) {
            Arrays.sort(arr, batch * parts, arr.length);
            medians.add(arr[parts * batch + (arr.length - parts * batch) / 2]);
        }
        medians.sort((v1, v2) -> Integer.compare(v1, v2));
        return medians.get(medians.size() / 2);
    }

    private static int partition2(int[] arr, int l, int r, int pivot) {
        int i = l;
        for (int j = l; j <= r; ++j) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                ++i;
            }
        }
        return i;
    }

    private static int findKthLargest1(int[] arr, int l, int r, int k) {
        if (arr.length <= k)
            return -1;
        if (l == r)
            return arr[k];
        int pivot = partition1(arr, l, r);
        if (pivot == k) {
            return arr[k];
        } else if (pivot > k) {
            return findKthLargest1(arr, l, pivot - 1, k);
        } else {
            return findKthLargest1(arr, pivot + 1, r, k);
        }
    }

    private static int partition1(int[] arr, int l, int r) {
        int pivot = r;
        int i = l;
        for (int j = l; j < r; ++j) {
            if (arr[j] <= arr[pivot]) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, pivot);
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
