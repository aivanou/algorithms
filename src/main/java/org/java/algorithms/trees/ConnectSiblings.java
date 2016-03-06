package org.java.algorithms.trees;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 */
public class ConnectSiblings {
    public static void main(String[] args) {
    }

    public static void connectSiblingsSeq(TNode root) {
        Queue<TNode> currq = new ArrayDeque<>();
        Queue<TNode> nextq = new ArrayDeque<>();
        currq.add(root);
        while (!currq.isEmpty()) {
            TNode prev = null;
            while (!currq.isEmpty()) {
                TNode curr = currq.poll();
                //combine curr and prev
                if (curr.getRight() != null)
                    nextq.add(curr.getRight());
                if (curr.getLeft() != null)
                    nextq.add(curr.getLeft());
                prev = curr;
            }
            currq = nextq;
            nextq = new ArrayDeque<>();
            continue;
        }
    }

    public static void connectSiblingsRec(TNode node) {
    }
}
