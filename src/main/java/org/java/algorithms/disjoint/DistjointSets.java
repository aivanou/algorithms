package org.java.algorithms.disjoint;

/**
 */
public class DistjointSets {
    public static void main(String[] args) {

    }

    public static void union(DSet set, int i1, int i2) {
        set.parent[root(set, i1)] = root(set, i2);
    }

    public static boolean find(DSet set, int i1, int i2) {
        return root(set, i1) == root(set, i2);
    }

    public static int root(DSet set, int ind) {
        while (set.parent[ind] != ind) {
            set.parent[ind] = set.parent[set.parent[ind]];
            ind = set.parent[ind];
        }
        return ind;
    }

    private static class DSet {
        public int[] data;
        public int[] parent;

        public DSet(int len) {
            this.data = new int[len];
            this.parent = new int[len];
        }
    }
}
