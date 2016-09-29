package org.java.algorithms.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 */
public class FloodGame {

    static Random r;

    public static void main(String[] args) {
        int n = 12, m = 12, ncolors = 6;
        int t = 10;
        r = new Random(n * m);
        for (int i = 0; i < t; ++i) {
            int[][] arr = gen(n, m, ncolors);
            solve(arr, n, m, ncolors);
        }
    }

    static void solve(int[][] arr, int n, int m, int ncolors) {
        Vertex head = cluster(toGraph(arr, n, m), n, m);
        System.out.println(greedyAlg(head, n, m, ncolors));
    }



    static int greedyAlg(Vertex head, int n, int m, int colors) {
        int moves = 0;
        while (head.combNodes != n * m && !head.verts.isEmpty()) {
            redraw(head, maxColor(head, colors));
            moves++;
            //            System.out.println(head.combNodes + "  " + head.verts.size());
        }
        return moves;
    }

    static int maxColor(Vertex head, int colors) {
        int mc = 0, mx = 0;
        for (int i = 0; i < colors; ++i) {
            int cc = sameColorAdj(head, i);
            if (cc > mx) {
                mx = cc;
                mc = i;
            }
        }
        return mc;
    }

    static int sameColorAdj(Vertex head, int color) {
        return head.verts.stream().filter(v -> v.c == color).collect(Collectors.toList()).size();
    }

    static void redraw(Vertex head, int color) {
        List<Vertex> changeColor = head.verts.stream().filter(v -> v.c == color).collect(Collectors.toList());
        for (Vertex v : changeColor) {
            head.verts.remove(v);
            v.verts.remove(head);
            for (Vertex next : v.verts) {
                next.verts.remove(v);
                if (!hasVertex(head, next)) {
                    head.verts.add(next);

                }
                if (!hasVertex(next, head)) {
                    next.verts.add(head);
                }
            }
            v.verts.clear();
            head.combNodes += v.combNodes;
        }
    }

    static Vertex[][] toGraph(int[][] arr, int n, int m) {
        Vertex[][] graph = new Vertex[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                Vertex v = new Vertex(arr[i][j], i, j, new ArrayList<>(4));
                v.root = v;
                graph[i][j] = v;
                if (i != 0) {
                    v.verts.add(graph[i - 1][j]);
                    graph[i - 1][j].verts.add(v);
                }
                if (j != 0) {
                    v.verts.add(graph[i][j - 1]);
                    graph[i][j - 1].verts.add(v);
                }

            }
        }
        return graph;
    }

    static Vertex cluster(Vertex[][] graph, int n, int m) {
        boolean[][] visited = new boolean[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (!visited[i][j]) {
                    visited[i][j] = true;
                    int cnodes = 0;
                    for (Vertex v : new ArrayList<>(graph[i][j].verts)) {
                        cnodes += combineByColor(graph[i][j], v, visited, graph[i][j].c);
                    }
                    graph[i][j].combNodes = cnodes + 1;
                }
                removeNodesWithTheSameColor(graph[i][j]);

            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                removeNodesWithTheSameColor(graph[i][j]);
                for (Vertex td : graph[i][j].toDelete) {
                    graph[i][j].verts.remove(td);
                    td.verts.remove(graph[i][j]);
                }
            }
        }
        return graph[0][0];
    }

    static void removeNodesWithTheSameColor(Vertex vr) {
        Iterator<Vertex> it = vr.verts.iterator();
        while (it.hasNext()) {
            Vertex v = it.next();
            if (v.c == vr.c) {
                it.remove();
            }
        }
    }

    static int combineByColor(Vertex startNode, Vertex node, boolean[][] processed, int color) {
        if (processed[node.i][node.j] || node.c != color) {
            return 0;
        }
        processed[node.i][node.j] = true;
        int cnodes = 1;
        for (Vertex v : node.verts) {
            cnodes += combineByColor(startNode, v, processed, color);
        }
        node.root = startNode;
        Iterator<Vertex> it = node.verts.iterator();
        while (it.hasNext()) {
            Vertex v = it.next();
            if (v.c == color || hasVertex(startNode, v.root)) {
                v.toDelete.add(node);
                node.toDelete.add(v);
                continue;
            }
            if (startNode.equals(v)) {
                continue;
            }
            node.toDelete.add(v);
            v.toDelete.add(node);
            startNode.verts.add(v.root);
            if (!hasVertex(v, startNode)) {
                v.verts.add(startNode);
            }
        }
        return cnodes;
    }

    static boolean hasVertex(Vertex node, Vertex check) {
        for (Vertex v : node.verts) {
            if (v.i == check.i && v.j == check.j) {
                return true;
            }
        }
        return false;
    }

    static class Vertex {
        int c;
        int i;
        int j;
        int combNodes;
        Vertex root;
        List<Vertex> toDelete = new LinkedList<>();
        List<Vertex> verts;

        public Vertex(int c, int i, int j, List<Vertex> verts) {
            this.c = c;
            this.i = i;
            this.j = j;
            this.verts = verts;
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Vertex vertex = (Vertex) o;

            if (c != vertex.c)
                return false;
            if (i != vertex.i)
                return false;
            return j == vertex.j;

        }

        @Override public int hashCode() {
            int result = c;
            result = 31 * result + i;
            result = 31 * result + j;
            return result;
        }

    }

    static int[][] gen(int n, int m, int ncolors) {
        int[][] a = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                a[i][j] = r.nextInt(ncolors);
            }
        }
        return a;
    }

    static void printGraph(Vertex v, int n, int m) {
        boolean[][] p = new boolean[n][m];
        int tnodes = 0;
        tnodes += printGraph(v, v, p);
        System.out.println("Total nodes: " + tnodes);
    }

    static int printGraph(Vertex v, Vertex par, boolean[][] p) {
        if (p[v.i][v.j])
            return 0;
        p[v.i][v.j] = true;
        System.out.println("V:[" + v.i + ";" + v.j + "] " + v.c + " " + v.combNodes + " " + " ->");
        System.out.println("REF FROM: " + par.i + " " + par.j);
        int tnodes = v.combNodes;
        for (Vertex next : v.verts) {
            tnodes += printGraph(next, v, p);
        }
        System.out.println("ENDV:[" + v.i + ";" + v.j + "] " + v.c);
        return tnodes;
    }

    static void print(int[][] arr) {
        for (int i = 0; i < arr.length; ++i) {
            for (int j = 0; j < arr[i].length; ++j) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}
