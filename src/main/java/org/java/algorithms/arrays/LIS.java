package org.java.algorithms.arrays;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Longest Increasing sub sequence
 */
public class LIS {
    public static void main(String[] args) throws Exception {
        int[] arr = new int[] { 3, 1, 2, 84, 3, 11, 2, 3, 4, 5, 6, 88, 99 };
        System.out.println(Arrays.toString(findLis(arr)));
    }

    static int[] findLis(int[] arr) {
        int[] lpIndex = new int[arr.length + 1];
        int[] parent = new int[arr.length + 1];
        int l = 0;
        for (int i = 0; i < arr.length; ++i) {
            int low = 1;
            int high = l;
            while (high >= low) {
                int mid = low + (high - low) / 2;
                int v = arr[lpIndex[mid]];
                if (arr[i] < v) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            parent[i] = lpIndex[low - 1];
            lpIndex[low] = i;
            if (low > l) {
                l = low;
            }
        }
        int[] lis = new int[l];
        int ind = lpIndex[l];
        for (int i = l - 1; i >= 0; --i) {
            lis[i] = arr[ind];
            ind = parent[ind];
        }
        return lis;
    }
}
