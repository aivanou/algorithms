package org.java.algorithms.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 */
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FordFulkenson {

    public static void main(String[] args) {
//        int graph[][] = new int[][] {
//            { 0, 16, 13, 0, 0, 0 },
//            { 0, 0, 10, 12, 0, 0 },
//            { 0, 4, 0, 0, 14, 0 },
//            { 0, 0, 9, 0, 0, 20 },
//            { 0, 0, 0, 7, 0, 4 },
//            { 0, 0, 0, 0, 0, 0 } };

        int graph[][] = new int[][] {
            { 0,1000,1000,0 },
            { 0,0,1,1000 },
            { 0,0,0,1000 },
            { 0, 0, 0,0 }};
        System.out.println(findFlow(graph, 0, 3));
    }

    public static int findFlow(int[][] graph, int s, int t) {
        int n = graph.length;
        int[][] residGraph = new int[n][n];
        int mFlow = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                residGraph[i][j] = graph[i][j];
            }
        }
        int[] parents = new int[n];
        while (bfs(residGraph, parents, s, t)) {
            int cFlow = Integer.MAX_VALUE;
            int p = t;
            while (p != s) {
                cFlow = Math.min(cFlow, residGraph[parents[p]][p]);
                p = parents[p];
            }
            p = t;
            while (p != s) {
                residGraph[parents[p]][p] -= cFlow;
                residGraph[p][parents[p]] += cFlow;
                p = parents[p];
            }
            mFlow += cFlow;
        }
        return mFlow;
    }

    private static boolean bfs(int[][] g, int[] parent, int s, int t) {
        boolean[] visited = new boolean[g.length];
        Queue<Integer> q = new ArrayDeque<>();
        q.add(s);
        parent[s] = -1;
        while (!q.isEmpty()) {
            int v = q.poll();
            if (v == t) {
                return true;
            }
            if(visited[v]) continue;
            visited[v]=true;
            for (int i = 0; i < g.length; ++i) {
                if (!visited[i] && g[v][i] > 0) {
                    q.add(i);
                    parent[i] = v;
                }
            }
        }
        return visited[t];
    }
}
