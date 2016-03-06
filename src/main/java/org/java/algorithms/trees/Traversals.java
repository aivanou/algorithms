package org.java.algorithms.trees;

import java.util.Stack;

/**
 */
public class Traversals {
    public static void main(String[] args) {
//        inOrderTraversal(TreeUtils.fromSortedArray(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }), 1);
        iterativeInOrder(TreeUtils.fromSortedArray(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }));
//        iterativeReversedInOrder(TreeUtils.fromSortedArray(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }));
    }

    public static void iterativeInOrder(TNode root) {
        Stack<TNode> st = new Stack();
        TNode curr = root;
        curr.depth = 1;
        while (true) {
            while (curr != null) {
                st.push(curr);
                if (curr.getLeft() != null)
                    curr.getLeft().depth = curr.depth + 1;
                curr = curr.getLeft();
            }

            if (!st.isEmpty()) {
                curr = st.pop();
                System.out.println("Depth: " + curr.depth + " :" + curr.getV());
                if (curr.getRight() != null)
                    curr.getRight().depth = curr.depth + 1;
                curr = curr.getRight();
            } else {
                break;
            }
        }
    }

    public static void iterativeReversedInOrder(TNode root) {
        Stack<TNode> st = new Stack();
        TNode curr = root;
        curr.depth = 1;
        while (true) {
            if (curr != null) {
                st.push(curr);
                if (curr.getRight() != null)
                    curr.getRight().depth = curr.depth + 1;
                curr = curr.getRight();
            } else {
                if (!st.isEmpty()) {
                    curr = st.pop();
                    System.out.println("Depth: " + curr.depth + " :" + curr.getV());
                    if (curr.getLeft() != null)
                        curr.getLeft().depth = curr.depth + 1;
                    curr = curr.getLeft();
                } else {
                    break;
                }
            }
        }
    }

    public static void inOrderTraversal(TNode node, int depth) {
        if (node == null)
            return;
        inOrderTraversal(node.getLeft(), depth + 1);
        System.out.println("Depth: " + depth + " : " + node.getV());
        inOrderTraversal(node.getRight(), depth + 1);
    }
}
