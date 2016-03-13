package org.java.algorithms;

import java.util.Arrays;

/**
 */
public class BinaryIndexedTree {
    public static void main(String[] args) {
        long[] tree = new long[16];
        update(tree, 11, 32);
        update(tree, 10, 10);
        System.out.println(Arrays.toString(tree));
        System.out.println(query(tree, 11));
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
