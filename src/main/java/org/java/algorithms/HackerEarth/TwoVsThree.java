package org.java.algorithms.HackerEarth;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * https://www.hackerearth.com/code-monk-segment-tree-and-lazy-propagation/algorithm/2-vs-3/
 */
public class TwoVsThree {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int len = Integer.parseInt(reader.readLine());
        int[] arr = new int[len];
        int[] twoPowerMod = new int[len + 1];
        twoPowerMod[0] = 1;
        for (int i = 0; i < len; ++i) {
            arr[i] = reader.read() - '0';
            twoPowerMod[i + 1] = (twoPowerMod[i] * 2) % 3;
        }
        reader.readLine();
        int[] tree = build(arr, twoPowerMod);
        printTree(tree, 1, 0, len - 1);
        int queries = Integer.parseInt(reader.readLine());
        for (int i = 0; i < queries; ++i) {
            int[] query = parse(reader.readLine().split(" "));
            process(tree, query, len, twoPowerMod);
        }
        reader.close();
    }

    private static void process(int[] tree, int[] query, int len, int[] twoPowerMod) {
        if (query[0] == 0) {
            System.out.println(compute(tree, 1, query[1], query[2], 0, len - 1, twoPowerMod));
        } else {
            changeBit(tree, 1, 0, len - 1, query[1], twoPowerMod);
        }
    }

    private static int compute(int[] tree, int node, int from, int to, int l, int r, int[] twoPowerMod) {
        if (from > r || l > to) {
            return 0;
        }
        if (from >= l && to <= r) {
            return tree[node];
        }
        int mid = l + (r - l) / 2;
        compute(tree, node, from, to, l, mid, twoPowerMod);
        compute(tree, node, from, to, mid + 1, r, twoPowerMod);
        return ((tree[node * 2] % 3) * twoPowerMod[to - mid] + tree[node * 2 + 1]) % 3;
    }

    private static void changeBit(int[] tree, int node, int l, int r, int bitPos, int[] twoPowerMod) {
        if (l == r && l == bitPos) {
            tree[node] = 1;
            return;
        }
        int mid = l + (r - l) / 2;
        if (bitPos > mid)
            changeBit(tree, node * 2 + 1, mid + 1, r, bitPos, twoPowerMod);
        else
            changeBit(tree, node * 2, l, mid, bitPos, twoPowerMod);
        tree[node] = ((tree[node * 2] % 3) * twoPowerMod[r - mid] + tree[node * 2 + 1]) % 3;
    }

    private static int[] parse(String[] parts) {
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; ++i) {
            arr[i] = Integer.parseInt(parts[i]);
        }
        return arr;
    }

    private static void printTree(int[] arr, int node, int l, int r) {
        if (l == r) {
            System.out.println(String.format("position:%d, node ind:%d, value: %d", l, node, arr[node]));
            return;
        }
        System.out.println(String.format("region:[%d : %d], node: %d, value: %d", l, r, node, arr[node]));
        int mid = l + (r - l) / 2;
        printTree(arr, node * 2, l, mid);
        printTree(arr, node * 2 + 1, mid + 1, r);
    }

    private static int[] build(int[] data, int[] twoPowerMod) {
        int[] segtree = new int[data.length * 3];
        build(segtree, 1, 0, data.length - 1, data, twoPowerMod);
        return segtree;
    }

    private static void build(int[] arr, int node, int l, int r, int[] data, int[] twoPowerMod) {
        if (l == r) {
            arr[node] = data[l];
            return;
        }
        int mid = l + (r - l) / 2;
        build(arr, node * 2, l, mid, data, twoPowerMod);
        build(arr, node * 2 + 1, mid + 1, r, data, twoPowerMod);
        arr[node] = (((arr[node * 2] % 3) * twoPowerMod[r - mid]) % 3 + arr[node * 2 + 1]) % 3;
    }
}
