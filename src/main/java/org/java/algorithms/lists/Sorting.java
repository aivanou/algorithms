package org.java.algorithms.lists;

/**
 */
public class Sorting {
    public static void main(String[] args) {
        //        LUtil.printList(LUtil.fromArray(new int[] { 3, 2, 1, 4 }));
        LNode root = LUtil.fromArray(new int[] { 4, 3, 1, 6, 8, 64, 3, 2, 43, 6 });
        qsort(root);
        LUtil.printList(root);
    }

    public static LNode qsort(LNode root) {
        if (root == null || root.next == null) {
            return root;
        }
        LNode l = root;
        LNode r = tail(root);
        qsort(l, r);
        return root;
    }

    public static LNode tail(LNode root) {
        while (root.next != null) {
            root = root.next;
        }
        return root;
    }

    public static void qsort(LNode l, LNode r) {
        LUtil.printList(l);
        if (r != null && l != r && l != r.next) {
            LNode mid = partition(l, r);
            qsort(l, mid.prev);
            qsort(mid.next, r);
        }
    }

    public static LNode partition(LNode l, LNode r) {
        int pData = r.data;
        LNode j = l;
        for (LNode i = l; i != null; i = i.next) {
            if (pData > i.data) {
                int t = j.data;
                j.data = i.data;
                i.data = t;
                j = j.next;
            }
        }
        int t = j.data;
        j.data = r.data;
        r.data = t;
        return j;
    }
}
