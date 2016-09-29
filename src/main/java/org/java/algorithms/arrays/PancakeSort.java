package org.java.algorithms.arrays;

import java.util.Arrays;

/**
 */
public class PancakeSort {
    public static void main(String[] args) {
        int[] arr = new int[] { 1, 5, 3, 4, 7, 2, -1 };
        psort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void psort(int[] arr) {
        for (int i = arr.length - 1; i >= 0; --i) {
            int mpos = findMax(arr, i);
            flip(arr, mpos);
            flip(arr, i);
        }
    }

    static int findMax(int[] arr, int len) {
        int mp = 0;
        for (int i = 0; i <= len; ++i) {
            if (arr[i] > arr[mp]) {
                mp = i;
            }
        }
        return mp;
    }

    static void flip(int[] arr, int pos) {
        int l = -1, r = pos + 1;
        while (++l < --r) {
            swap(arr, l, r);
        }
    }

    static void swap(int[] arr, int i1, int i2) {
        int t = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = t;
    }
}
