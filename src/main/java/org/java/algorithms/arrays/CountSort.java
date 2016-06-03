package org.java.algorithms.arrays;

import java.util.Arrays;

/**
 */
public class CountSort {
    public static void main(String[] args) {
        int[] arr = new int[] { 4, 3, 1, 9, 0, 0, 3, 2, 5, 8, 10, 3, 4, 0, 7, 6, 5 };
        sort(arr, 11);
        System.out.println(Arrays.toString(arr));
    }

    static void sort(int[] arr, int range) {
        int[] count = new int[range + 1];
        for (int i = 0; i < arr.length; ++i) {
            count[arr[i]]++;
        }
        for (int i = 1; i < count.length; ++i) {
            count[i] += count[i - 1];
        }
        int[] out = new int[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            out[count[arr[i]] - 1] = arr[i];
            --count[arr[i]];
        }
        System.arraycopy(out, 0, arr, 0, arr.length);
    }
}
