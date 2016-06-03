package org.java.algorithms.trees;

/**
 */
public class TNode {
    public int v;
    public TNode left;
    public TNode right;
    public int depth;
    public TNode parent;

    public void setV(int v) {
        this.v = v;
    }

    public void setLeft(TNode left) {
        this.left = left;
    }

    public void setRight(TNode right) {
        this.right = right;
    }

    public int getV() {

        return v;
    }

    public TNode getLeft() {
        return left;
    }

    public TNode getRight() {
        return right;
    }

    public TNode(int v, TNode left, TNode right) {
        this.v = v;
        this.left = left;
        this.right = right;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TNode tNode = (TNode) o;

        return v == tNode.v;

    }

    @Override public int hashCode() {
        return v;
    }
}
