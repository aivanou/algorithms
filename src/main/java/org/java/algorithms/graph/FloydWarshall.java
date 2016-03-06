package org.java.algorithms.graph;

import java.util.Scanner;

/**
 */
public class FloydWarshall {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int m = s.nextInt();
        int[][] l = new int[n + 1][n + 1];
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= n; ++j) {
                l[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < m; ++i) {
            int v1 = s.nextInt();
            int v2 = s.nextInt();
            int w = s.nextInt();
            l[v1][v2] = w;
        }
        l = floyd(l, n);
        int q = s.nextInt();
        for (int i = 0; i < q; ++i) {
            int f = s.nextInt();
            int t = s.nextInt();
            if (f == t) {
                System.out.println("0");
                continue;
            }
            int v = l[f][t];
            if (v == Integer.MAX_VALUE)
                System.out.println("-1");
            else
                System.out.println(v);
        }
    }

    static int[][] floyd(int[][] l, int n) {
        for (int k = 1; k <= n; ++k) {
            int[][] d = new int[n + 1][n + 1];
            for (int i = 1; i <= n; ++i) {
                for (int j = 1; j <= n; ++j) {
                    if (l[i][k] == Integer.MAX_VALUE || l[k][j] == Integer.MAX_VALUE)
                        d[i][j] = l[i][j];
                    else
                        d[i][j] = Math.min(l[i][j], l[i][k] + l[k][j]);
                }
            }
            l = d;
        }
        return l;
    }
}
