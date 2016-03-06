package org.java.algorithms.graph;

import java.util.*;

/**
 */
public class StronglyConnectedComponents {
    public static void main(String[] args) {

    }

    public static void build(Graph graph) {
        int[] time = new int[graph.vertices.size()];
        dfs(graph, new Vertex(1), new int[graph.vertices.size()], time, 1);
        Graph revert = graph.revert();
        int[] color = new int[graph.vertices.size()];
        for (Vertex v : revert.vertices.values()) {
            List<Vertex> component = findComponet(revert, v, color);
            System.out.println(component);
        }
    }

    public static List<Vertex> findComponet(Graph graph, Vertex v, int[] color) {
        Stack<Vertex> st = new Stack<>();
        List<Vertex> verts = new ArrayList<>();
        st.push(v);
        while (!st.isEmpty()) {
            Vertex curr = st.pop();
            verts.add(curr);
            color[curr.id] = 1;
            for (Edge e : graph.edges.get(v)) {
                if (color[e.to.id] != 0)
                    continue;
                st.push(e.to);
            }
        }
        return verts;
    }

    public static int dfs(Graph graph, Vertex curr, int[] color, int[] time, int currTime) {
        if (color[curr.id] == 2)
            return 0;
        color[curr.id] = 1;

        for (Edge edge : graph.edges.get(curr)) {
            if (color[edge.to.id] != 0)
                continue;
            int ftime = dfs(graph, edge.to, color, time, currTime++);
            currTime = ftime;
        }
        currTime++;
        time[curr.id] = currTime;
        color[curr.id] = 2;
        return currTime;
    }
}
