package org.java.algorithms.graph;

/**
 */
public class Pair {
    public int v1;
    public int v2;
    public int w;

    public Pair(int v1, int v2, int w) {
        this.v1 = v1;
        this.v2 = v2;
        this.w = w;
    }

    public Pair(int v2, int v1) {
        this.v2 = v2;
        this.v1 = v1;
    }
}
