package org.java.algorithms.trees;

import java.util.Queue;
import java.util.Random;

/**
 */
public class Treap {

    public static final Random rand = new Random();

    public static void main(String[] args) {
        TreapNode root = new TreapNode(null, null, 1, rand.nextInt(10000));
        int[] arr = new int[] { 3, 55, 3, 1, 77, 8, 96, 5, 4, 2, 32 };
        for (int i = 0; i < 10; ++i) {
            root = add(root, rand.nextInt(100));
        }
        fillWithData(root, arr, 0);
        inOrder(root);
        System.out.println();
        root = moveToFront(root, 3-1, 12);
        inOrder(root);
        System.out.println();
    }

    public static TreapNode moveToFront(TreapNode root, int l, int r) {
        TreapNode[] t1 = split(root, r);
        TreapNode left = t1[0];
        TreapNode right = t1[1];
        TreapNode[] t2 = split(left, l);
        TreapNode absLeft = t2[0];
        TreapNode middle = t2[1];
        return merge(merge(middle, absLeft), right);
    }

    public static void fillWithData(TreapNode node, int[] arr, int ind) {
        if (node == null)
            return;
        fillWithData(node.left, arr, ind);
        int tind = size(node.left);
        node.data = arr[tind + ind];
        fillWithData(node.right, arr, ind + tind + 1);
    }

    public static int size(TreapNode node) {
        if (node == null)
            return 0;
        return node.size;
    }

    public static void recalcSize(TreapNode node) {
        node.size = size(node.left) + size(node.right) + 1;
    }

    public static void inOrder(TreapNode node) {
        if (node == null)
            return;
        inOrder(node.left);
        System.out.print(String.format("[%d] ", node.data));
        inOrder(node.right);

    }

    public static TreapNode add(TreapNode root, int el) {
        int priority = rand.nextInt(10000);
        TreapNode[] temp = split(root, el);
        TreapNode newNode = new TreapNode(null, null, 1, priority);
        return merge(merge(temp[0], newNode), temp[1]);
    }

    public static TreapNode[] split(TreapNode root, int data) {
        int currInd = size(root.left) + 1;
        if (currInd <= data) {
            if (root.right != null) {
                TreapNode[] temp = split(root.right, data - currInd);
                TreapNode newLeft = new TreapNode(root.left, temp[0], root.priority, root.data, 0);
                recalcSize(newLeft);
                return new TreapNode[] { newLeft, temp[1] };
            } else {
                TreapNode newLeft = new TreapNode(root.left, null, root.priority, root.data, 0);
                recalcSize(newLeft);
                return new TreapNode[] { newLeft, null };
            }
        } else {
            if (root.left != null) {
                TreapNode[] temp = split(root.left, data);
                TreapNode newRight = new TreapNode(temp[1], root.right, root.priority, root.data, 0);
                recalcSize(newRight);
                return new TreapNode[] { temp[0], newRight };
            } else {
                TreapNode newRight = new TreapNode(null, root.right, root.priority, root.data, 0);
                recalcSize(newRight);
                return new TreapNode[] { null, newRight };
            }
        }
    }

    public static TreapNode merge(TreapNode l, TreapNode r) {
        if (l == null)
            return r;
        if (r == null)
            return l;
        if (l.priority > r.priority) {
            TreapNode rChild = merge(l.right, r);
            TreapNode newNode = new TreapNode(l.left, rChild, l.priority, l.data, 0);
            recalcSize(newNode);
            return newNode;
        } else {
            TreapNode lChild = merge(l, r.left);
            TreapNode newNode = new TreapNode(lChild, r.right, r.priority, r.data, 0);
            recalcSize(newNode);
            return newNode;
        }
    }

    public static TreapNode buildCartTree(int[] arr) {
        int[] parent = new int[arr.length + 1];
        int[] left = new int[arr.length + 1];
        int[] right = new int[arr.length + 1];
        int root = 1;
        for (int i = 2; i <= arr.length; ++i) {
            int el = arr[i - 1];
            int last = i - 1;
            while (arr[last] < el && last != root) {
                last = parent[last];
            }
            if (el > arr[last]) {
                parent[last] = i;
                left[i] = last;
                root = i;
            } else if (right[last] == 0) {
                right[last] = i;
                parent[i] = last;
            } else {
                parent[right[last]] = i;
                parent[i] = last;
                left[i] = right[last];
                right[last] = i;
            }
        }
        return buildCartTree(arr, parent, left, right, root);
    }

    static TreapNode buildCartTree(int[] arr, int[] parent, int[] left, int[] right, int root) {
        if (root == 0)
            return null;
        TreapNode node = new TreapNode();
        node.ind = root;
        //        node.data = arr[root];
        node.left = buildCartTree(arr, parent, left, right, left[root]);
        node.right = buildCartTree(arr, parent, left, right, right[root]);
        return node;
    }

    private static class TreapNode {
        TreapNode left;
        TreapNode right;
        int size;
        int priority;
        int ind;
        int data;

        public TreapNode() {
        }

        public TreapNode(TreapNode left, TreapNode right, int priority) {
            this.left = left;
            this.right = right;
            this.priority = priority;
        }

        public TreapNode(TreapNode left, TreapNode right, int priority, int data, int size) {
            this.data = data;
            this.size = size;
            this.left = left;
            this.right = right;
            this.priority = priority;
        }

        public TreapNode(TreapNode left, TreapNode right, int size, int priority) {
            this.size = size;
            this.left = left;
            this.right = right;
            this.priority = priority;
        }
    }
}
