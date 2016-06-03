package org.java.algorithms.trees;

/**
 */
public class BSTToLL {

    public static void main(String[] args) {

    }

    static TNode toList(TNode root) {
        if (root == null)
            return null;
        if (root.getLeft() != null) {
            TNode left = toList(root.getLeft());
            while (left.getRight() != null) {
                left = left.getRight();
            }
            root.setLeft(left);
            left.setRight(root);
        }
        if (root.getRight() != null) {
            TNode right = toList(root.getRight());
            while (right.getLeft() != null) {
                right = right.getLeft();
            }
            root.setRight(right);
            right.setLeft(root);
        }
        return root;
    }
}
