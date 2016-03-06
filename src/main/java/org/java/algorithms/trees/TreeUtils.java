package org.java.algorithms.trees;

import java.util.List;
import java.util.Stack;

/**
 */
public class TreeUtils {

    public static TNode fromSortedList(List<Integer> lst) {
        return fromSortedList(lst, 0, lst.size() - 1);
    }

    public static TNode fromSortedArray(int[] arr) {
        return fromSortedArray(arr, 0, arr.length - 1);
    }

    private static TNode fromSortedArray(int[] lst, int l, int r) {
        if (l > r)
            return null;
        int mid = l + (r - l) / 2;
        TNode node = new TNode(lst[mid], fromSortedArray(lst, l, mid - 1), fromSortedArray(lst, mid + 1, r));
        return node;
    }

    private static TNode fromSortedList(List<Integer> lst, int l, int r) {
        if (l > r)
            return null;
        int mid = l + (r - l) / 2;
        TNode node = new TNode(lst.get(mid), fromSortedList(lst, l, mid - 1), fromSortedList(lst, mid + 1, r));
        return node;
    }
}
