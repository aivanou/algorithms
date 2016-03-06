package org.java.algorithms.graph;

/**
 */
public class Vertex {
    public int id;
    public int w;

    public Vertex(int id, int w) {
        this.id = id;
        this.w = w;
    }

    public Vertex(int id) {
        this.id = id;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Vertex vertex = (Vertex) o;

        return id == vertex.id;

    }

    @Override public int hashCode() {
        return id;
    }

    @Override public String toString() {
        return id + "";
    }
}
