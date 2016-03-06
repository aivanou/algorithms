package org.java.algorithms.trees;

import java.util.Stack;

/**
 */
public class FindAPairWithSum {
    public static void main(String[] args) {
        printPair(TreeUtils.fromSortedArray(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }), 19);
    }

    private static void printPair(TNode root, int value) {
        Stack<TNode> forward = new Stack<>();
        Stack<TNode> backward = new Stack<>();
        TNode fcurr = root;
        TNode bcurr = root;
        boolean stepForward = true;
        boolean stepBackward = true;
        while (true) {
            if (stepForward) {
                stepForward = false;
                fcurr = stepForward(forward, fcurr);
            }
            if (stepBackward) {
                stepBackward = false;
                bcurr = stepBackward(backward, bcurr);
            }
            if (fcurr == null || bcurr == null)
                return;
            if (fcurr.getV() > bcurr.getV()) {
                System.out.println("-1");
            } else if (fcurr.getV() + bcurr.getV() > value) {
                bcurr = bcurr.getLeft();
                stepBackward = true;
            } else if (fcurr.getV() + bcurr.getV() < value) {
                fcurr = fcurr.getRight();
                stepForward = true;
            } else {
                System.out.println(fcurr.getV() + "  " + bcurr.getV());
                return;
            }
        }
    }

    private static TNode stepForward(Stack<TNode> st, TNode curr) {
        while (curr != null) {
            st.push(curr);
            curr = curr.getLeft();
        }
        if (!st.isEmpty()) {
            curr = st.pop();
        } else {
            return null;
        }
        return curr;
    }

    private static TNode stepBackward(Stack<TNode> st, TNode curr) {
        while (curr != null) {
            st.push(curr);
            curr = curr.getRight();
        }
        if (!st.isEmpty()) {
            curr = st.pop();
        } else {
            return null;
        }
        return curr;
    }

}
