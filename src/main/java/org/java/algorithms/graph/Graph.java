package org.java.algorithms.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 */
public class Graph {
    public static Graph build(Pair[] arr) {
        return new Graph(arr);
    }

    public Map<Integer, Vertex> vertices;
    public Map<Vertex, List<Edge>> edges;

    public Graph(Map<Integer, Vertex> vertices, Map<Vertex, List<Edge>> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    private Graph(Pair[] arr) {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
        for (Pair p : arr) {
            Vertex v1 = getVertex(p.v1);
            Vertex v2 = getVertex(p.v2);
            edges.get(v1).add(new Edge(p.w, v1, v2));
        }
    }

    private Vertex getVertex(int id) {
        Vertex v;
        if (!vertices.containsKey(id)) {
            v = new Vertex(id, 0);
            vertices.put(id, v);
            edges.put(v, new ArrayList<>());
        } else {
            v = vertices.get(id);
        }
        return v;
    }

    public Graph revert() {
        Map<Integer, Vertex> revVertices = new HashMap<>(vertices);
        Map<Vertex, List<Edge>> revEdges = new HashMap<>();
        for (Vertex v : edges.keySet()) {
            for (Edge e : edges.get(v)) {
                Edge ne = new Edge(e.w, e.to, e.from);
                if (!revEdges.containsKey(e.from)) {
                    revEdges.put(e.from, new ArrayList<>());
                }
                if (!revEdges.containsKey(e.to)) {
                    revEdges.put(e.to, new ArrayList<>());
                }
                revEdges.get(e.to).add(ne);
            }
        }
        return new Graph(revVertices, revEdges);
    }
}
