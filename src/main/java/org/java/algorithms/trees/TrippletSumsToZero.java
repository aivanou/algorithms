package org.java.algorithms.trees;

/**
 */
public class TrippletSumsToZero {
    public static void main(String[] args) {

    }

    public static void printTripplet(TNode root) {
        toLL(root);
        TNode curr = head;
        while (curr != null) {
            if (curr.getV() < 0) {
                findPair(curr.getV());
            }
            curr = curr.getRight();
        }
    }

    static void findPair(int c) {

    }

    static TNode head, tail;

    public static void toLL(TNode node) {
        if (node == null)
            return;
        toLL(node.getLeft());
        if (tail != null) {
            tail.setRight(node);
        } else {
            head = node;
        }
        tail = node;
        toLL(node.getRight());
    }
}
