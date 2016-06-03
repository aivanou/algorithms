package org.java.algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class BinaryIndexedTree {
    public static void main(String[] args) {
        long[] tree = new long[16];
        update(tree, 11, 2);
        update(tree, 10, 1);
        update(tree, 3, 7);
        update(tree, 4, 10);
        System.out.println(Arrays.toString(tree));
        System.out.println(query(tree, 11));
        System.out.println(readSingle(tree, 10));
    }

    public static long readSingle(long[] tree, int ind) {
        if (ind <= 0)
            return tree[0];
        long sum = tree[ind];
        int z = ind - (ind & (-ind));
        ind--;
        while (ind != z) {
            sum -= tree[ind];
            ind -= (ind & (-ind));
        }
        return sum;
    }

    public static void update(long[] tree, int ind, long val) {
        if (ind >= tree.length)
            return;
        tree[ind] += val;
        update(tree, ind + (ind & (-ind)), val);
    }

    public static long query(long[] tree, int ind) {
        if (ind == 0)
            return tree[0];
        return tree[ind] + query(tree, ind - (ind & (-ind)));
    }
}
