package org.java.algorithms.dynamic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Given an array arr[0 … n-1] containing n positive integers, a subsequence of arr[] is called Bitonic if it is first increasing, then decreasing. Write a
 * function that takes an array as argument and returns the length of the longest bitonic subsequence.
 */
public class BitonicTour {
    public static void main(String[] args) {
        System.out.println(find(new int[] { 1, 11, 2, 10, 4, 5, 2, 1 }));
    }

    static int find(int[] seq) {
        int[] lisParent = new int[seq.length];
        int[] ldsParent = new int[seq.length];
        int[] lis = computeLis(seq, lisParent);
        int[] lds = computeLds(seq, ldsParent);
        int max = 0;
        int maxInd = 0;
        for (int i = 0; i < seq.length; ++i) {
            if (max < lis[i] + lds[i] - 1) {
                max = lis[i] + lds[i] - 1;
                maxInd = i;
            }
        }
        List<Integer> lst = new LinkedList<>();
        int k = lisParent[maxInd];
        while (k != -1) {
            lst.add(seq[k]);
            k = lisParent[k];
        }
        k = ldsParent[maxInd];
        while (k != -1) {
            lst.add(seq[k]);
            k = ldsParent[k];
        }
        System.out.println(lst);
        return max;
    }

    static int[] computeLis(int[] seq, int[] parent) {
        int[] lis = new int[seq.length];
        for (int i = 0; i < seq.length; ++i) {
            lis[i] = 1;
            parent[i] = -1;
        }
        for (int i = 1; i < seq.length; ++i) {
            for (int j = 0; j < i; ++j) {
                if (seq[i] > seq[j] && lis[i] < lis[j] + 1) {
                    lis[i] = lis[j] + 1;
                    parent[i] = j;
                }
            }
        }
        return lis;
    }

    static int[] computeLds(int[] seq, int[] parent) {
        int[] lds = new int[seq.length];
        for (int i = 0; i < seq.length; ++i) {
            lds[i] = 1;
            parent[i] = -1;
        }
        for (int i = seq.length - 2; i >= 0; --i) {
            for (int j = seq.length - 1; j > i; --j) {
                if (seq[i] > seq[j] && lds[i] < lds[j] + 1) {
                    lds[i] = lds[j] + 1;
                    parent[i] = j;
                }
            }
        }
        return lds;
    }
}
