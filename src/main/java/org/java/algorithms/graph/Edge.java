package org.java.algorithms.graph;

/**
 */
public class Edge {
    public int w;
    public Vertex from;
    public Vertex to;

    public Edge(int w, Vertex from, Vertex to) {
        this.w = w;
        this.from = from;
        this.to = to;
    }
}
