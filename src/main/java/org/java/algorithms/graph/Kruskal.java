package org.java.algorithms.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import javafx.util.*;

/**
 */
public class Kruskal {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        Queue<Edge> q = new PriorityQueue<>((e1, e2) -> {
            if (e1.w == e2.w)
                return Integer.compare(e1.getSum(), e2.getSum());
            return Integer.compare(e1.w, e2.w);
        });
        int n = s.nextInt();
        int m = s.nextInt();
        Map<Integer, List<javafx.util.Pair<Integer, Integer>>> g = new HashMap<>();
        int[] roots = new int[n + 1];
        for (int i = 0; i <= n; ++i) {
            roots[i] = i;
            if (!g.containsKey(i))
                g.put(i, new ArrayList<>());
        }
        for (int i = 0; i < m; ++i) {
            int v1 = s.nextInt();
            int v2 = s.nextInt();
            int w = s.nextInt();
            g.get(v1).add(new javafx.util.Pair<>(v2, w));
            g.get(v2).add(new javafx.util.Pair<>(v1, w));
            q.add(new Edge(v1, v2, w));
        }
        int tw = 0;
        while (!q.isEmpty()) {
            Edge e = q.poll();
            if (find(roots, e.from, e.to))
                continue;
            union(roots, e.from, e.to);
            tw += e.w;
        }
        System.out.println(tw);
    }

    private static int root(int[] s, int v) {
        while (v != s[v]) {
            s[v] = s[s[v]];
            v = s[v];
        }
        return v;
    }

    private static void union(int[] s, int v1, int v2) {
        int r1 = root(s, v1);
        int r2 = root(s, v2);
        s[r1] = r1;
        s[r2] = r1;
    }

    private static boolean find(int[] s, int v1, int v2) {
        return root(s, v1) == root(s, v2);
    }

    private static class Edge {
        public int from;
        public int to;
        public int w;

        public Edge(int from, int to, int w) {
            this.w = w;
            this.to = to;
            this.from = from;
        }

        public int getSum() {
            return from + to + w;
        }
    }
}
