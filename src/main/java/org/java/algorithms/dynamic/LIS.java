package org.java.algorithms.dynamic;

import java.util.Arrays;

/**
 * Longest Increasing Subsequence
 */
public class LIS {
    public static void main(String[] args) {
        int[] a = new int[] { 0, 3, 5, 8, 11, 14, 16, 20, 24, 30 };
        System.out.println(find(new int[] { 2, 5, 3, 7, 11, 8, 10, 13, 6 }));
        //        f(a, -1);
    }

    static int f(int[] arr, int el) {
        int l = 0, r = arr.length - 1;
        while (r > l) {
            int mid = l + (r - l) / 2;
            if (arr[mid] > el)
                r = mid;
            else
                l = mid;
        }
        System.out.println(el + "  " + arr[l] + "  " + arr[r]);
        return arr[l];
    }

    static int find(int[] seq) {
        int[] lastEls = new int[seq.length + 1];
        lastEls[0] = seq[0];
        int len = 1;
        int[] parent = new int[seq.length + 1];
        for (int i = 1; i < seq.length; ++i) {
            if (seq[i] < lastEls[0]) {
                parent[i] = -1;
                lastEls[0] = i;
            } else if (seq[i] > lastEls[len - 1]) {
                lastEls[len++] = i;
                parent[i] = lastEls[len - 1];
            } else {
                int l = 0, r = len - 1;
                while (r > l) {
                    int mid = l + (r - l) / 2;
                    if (lastEls[mid] >= seq[i]) {
                        r = mid;
                    } else {
                        l = mid + 1;
                    }
                }
                lastEls[r] = i;
                parent[i] = lastEls[r - 1];
            }
        }
        int k = lastEls[len - 1];
        int[] lis = new int[len];
        for (int i = len - 1; i >= 0; --i) {
            if (k == -1)
                break;
            lis[i] = seq[k];
            k = parent[k];
        }
        System.out.println(Arrays.toString(lis));
        return len;
    }
}
