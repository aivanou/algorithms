package org.java.algorithms.graph;

import java.util.*;

/**
 */
public class Prim {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int m = s.nextInt();
        Map<Integer, List<javafx.util.Pair<Integer, Integer>>> g = new HashMap<>();
        for (int i = 0; i <= n; ++i) {
            if (!g.containsKey(i))
                g.put(i, new ArrayList<>());
        }
        for (int i = 0; i < m; ++i) {
            int v1 = s.nextInt();
            int v2 = s.nextInt();
            int w = s.nextInt();
            g.get(v1).add(new javafx.util.Pair<>(v2, w));
            g.get(v2).add(new javafx.util.Pair<>(v1, w));
        }
        int st = s.nextInt();
        solve(g, n, st);
    }

    static void solve(Map<Integer, List<javafx.util.Pair<Integer, Integer>>> g, int n, int st) {
        boolean[] processed = new boolean[n + 1];
        Queue<javafx.util.Pair<Integer, Integer>> q = new PriorityQueue<>((p1, p2) -> p1.getValue().compareTo(p2.getValue()));
        q.add(new javafx.util.Pair<>(st, 0));
        int totalW = 0;
        while (!q.isEmpty()) {
            javafx.util.Pair<Integer, Integer> cp = q.poll();
            int v = cp.getKey();
            int cw = cp.getValue();
            if (processed[v])
                continue;
            processed[v] = true;
            totalW += cw;
            for (javafx.util.Pair<Integer, Integer> p : g.get(v)) {
                int u = p.getKey();
                int w = p.getValue();
                if (!processed[u]) {
                    q.add(new javafx.util.Pair<>(u, w));
                }
            }
        }
        System.out.println(totalW);
    }
}
