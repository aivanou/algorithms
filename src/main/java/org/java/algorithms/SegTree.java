package org.java.algorithms;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

/**
 * Segment tree with lazy propagation
 */
public class SegTree {
    public static void main(String[] args) {
        int[] arr = new int[] { 5, 3, 1, 7, 8, 4, 2, 5, 66, 4, 6, 6, 4, 7, 8, 93, 1, 2322 };
        SegTree tree = new SegTree(arr);
        tree.print();
        System.out.println();
        tree.updateRange(3, 7, 1000);
        tree.print();
        System.out.println(tree.querySum(1, 12));

    }

    private SNode[] tree;
    private int[] lazy;
    private int nleafs;

    public SegTree(int[] arr) {
        build(arr);
    }

    public int querySum(int from, int to) {
        return querySum(from, to, 0, nleafs, 1);
    }

    private int querySum(int from, int to, int l, int r, int nodePos) {
        if (from > r || to < l || l > r)
            return 0;
        if (lazy[nodePos] != 0) {
            tree[nodePos].sum += (r - l + 1) * lazy[nodePos];
            if (l != r) {
                lazy[left(nodePos)] = lazy[nodePos];
                lazy[right(nodePos)] = lazy[nodePos];
            }
            lazy[nodePos] = 0;
        }
        if (l == r) {
            return tree[nodePos].sum;
        }
        if (from <= l && to >= r)
            return tree[nodePos].sum;
        int mid = l + (r - l) / 2;
        int s1 = querySum(from, to, l, mid, left(nodePos));
        int s2 = querySum(from, to, mid + 1, r, right(nodePos));
        return s1 + s2;
    }

    public void updateRange(int from, int to, int diff) {
        updateRange(from, to, diff, 0, nleafs, 1);
    }

    private void updateRange(int from, int to, int diff, int l, int r, int nodePos) {
        if (lazy[nodePos] != 0) {
            tree[nodePos].sum += (r - l + 1) * lazy[nodePos];
            tree[nodePos].max += (r - l + 1) * lazy[nodePos];
            tree[nodePos].min += (r - l + 1) * lazy[nodePos];
            if (l != r) {
                lazy[left(nodePos)] += lazy[nodePos];
                lazy[right(nodePos)] += lazy[nodePos];
            }
            lazy[nodePos] = 0;
        }
        if (from > r || to < l || l > r)
            return;
        if (from <= l && to >= r) {
            tree[nodePos].sum += (r - l + 1) * diff;
            tree[nodePos].min += (r - l + 1) * diff;
            tree[nodePos].max += (r - l + 1) * diff;
            if (l != r) {
                lazy[left(nodePos)] += diff;
                lazy[right(nodePos)] += diff;
            }
            return;
        }
        int mid = l + (r - l) / 2;
        updateRange(from, to, diff, l, mid, left(nodePos));
        updateRange(from, to, diff, mid + 1, r, right(nodePos));
        SNode ln = tree[left(nodePos)];
        SNode rn = tree[right(nodePos)];
        SNode nd = tree[nodePos];
        nd.sum = ln.sum + rn.sum;
        nd.max = Math.max(ln.max, rn.max);
        nd.min = Math.max(ln.min, rn.min);
    }

    public void update(int ind, int value) {
        update(0, nleafs, ind, value, 1);
    }

    private int update(int l, int r, int ind, int value, int nodePos) {
        if (ind < l || ind > r)
            return -1;
        int mid = l + (r - l) / 2;
        tree[nodePos].max = Math.max(tree[nodePos].max, value);
        tree[nodePos].max = Math.min(tree[nodePos].min, value);
        if (l == r) {
            int v = tree[nodePos].sum;
            tree[nodePos].sum = value;
            return v;
        }
        int v;
        if (ind > mid) {
            v = update(mid + 1, r, ind, value, right(nodePos));
        } else {
            v = update(l, mid, ind, value, left(nodePos));
        }
        /**Note: the return value is the old value of the leaf, so in order to find sum
         * we subtract old value and add new value*/
        tree[nodePos].sum += value - v;
        return v;
    }

    public void print() {
        Queue<Map.Entry<Integer, Integer>> q = new ArrayDeque<>();
        q.add(new AbstractMap.SimpleEntry<>(1, 0));
        int currLevel = 0;
        while (!q.isEmpty()) {
            Map.Entry<Integer, Integer> e = q.poll();
            int nodePos = e.getKey();
            if (nodePos >= tree.length || tree[nodePos] == null)
                continue;
            int l = e.getValue();
            if (l != currLevel) {
                System.out.println();
                currLevel = l;
            }
            boolean leaf = left(nodePos) >= tree.length || tree[left(nodePos)] == null || tree[right(nodePos)] == null;
            String m = leaf ? "l: " : "";
            System.out.print(m + tree[nodePos].sum + " ");
            q.add(new AbstractMap.SimpleEntry<>(left(nodePos), l + 1));
            q.add(new AbstractMap.SimpleEntry<>(right(nodePos), l + 1));
        }
        System.out.println();
    }

    private void build(int[] arr) {
        int len = getPow(arr.length) + 1;
        this.tree = new SNode[len];
        this.lazy = new int[len];
        this.nleafs = arr.length - 1;
        build(arr, 0, arr.length - 1, 1);
    }

    private SNode build(int[] arr, int l, int r, int pos) {
        if (l >= r) {
            SNode nd = new SNode(arr[l], arr[l], arr[l]);
            tree[pos] = nd;
            return nd;
        }
        int mid = l + (r - l) / 2;
        SNode ln = build(arr, l, mid, left(pos));
        SNode rn = build(arr, mid + 1, r, right(pos));
        SNode nd = new SNode(ln.sum + rn.sum, Math.max(ln.max, rn.max), Math.min(ln.max, rn.max));
        tree[pos] = nd;
        return nd;
    }

    private int left(int i) {
        return i * 2;
    }

    private int right(int i) {
        return i * 2 + 1;
    }

    private int parent(int i) {
        return i / 2;
    }

    private class SNode {
        int sum;
        int max;
        int min;

        public SNode(int sum, int max, int min) {
            this.sum = sum;
            this.max = max;
            this.min = min;
        }
    }

    private static int getPow(int n) {
        int p = (int) (Math.ceil(Math.log(n) / Math.log(2)));
        return 2 * (int) Math.pow(2, p) - 1;
    }
}
