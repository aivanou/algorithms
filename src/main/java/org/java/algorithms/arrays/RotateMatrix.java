package org.java.algorithms.arrays;

import java.util.Arrays;

/**
 */
public class RotateMatrix {

    public static void main(String[] args) {
        int[][] m = new int[][] { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 }, { 16, 17, 18, 19, 20 }, { 21, 22, 23, 24, 25 }, };
        print(m);
        System.out.println();
        rotateForOne(m);
        print(m);
    }

    static void rotateForOne(int[][] matr) {
        int len = matr.length;
        for (int k = 0; k < len / 2; ++k) {
            int alu = matr[k][k];
            int ald = matr[len - 1 - k][k];
            int aru = matr[k][len - 1 - k];
            int ard = matr[len - 1 - k][len - 1 - k];
            for (int i = k + 1; i < len - k; ++i) {
                matr[k][len - i] = matr[k][len - i - 1];
                matr[len - i][len - k - 1] = matr[len - i - 1][len - k - 1];
                matr[len - k - 1][i - 1] = matr[len - k - 1][i];
                matr[i - 1][k] = matr[i][k];
            }
            matr[k][k + 1] = alu;
            matr[k + 1][len - k - 1] = aru;
            matr[len - k - 1][len - k - 2] = ard;
            matr[len - k - 2][k] = ald;
        }
    }

    static void rotate90(int[][] matr) {
        int len = matr.length;
        for (int k = 0; k < len / 2; ++k) {
            for (int i = k; i < len - k - 1; ++i) {
                int temp = matr[k][i];
                matr[k][i] = matr[len - 1 - i][k];
                matr[len - 1 - i][k] = matr[len - 1 - k][len - 1 - i];
                matr[len - 1 - k][len - 1 - i] = matr[i][len - 1 - k];
                matr[i][len - 1 - k] = temp;
            }
        }
    }

    static void print(int[][] matr) {
        int len = matr.length;
        for (int i = 0; i < len; ++i) {
            System.out.println(Arrays.toString(matr[i]));
        }

    }
    /**
     *
     * void makeDLL(TNode root){
     *     Dequeue<TNode> q = new ArrayDequeue<>();
     *     Dequeue<TNode> nextLevel = new ArrayDequeue<>();
     *     TNode prev = null;
     *     int level = 0;
     *     q.add(root);
     *     while(!q.isEmpty()){
     *         while(!q.isEmpty()){
     *              TNode curr = level%2==0?q.first():q.last();
     *              if(curr.left!=null) nextLevel.add(curr.left);
     *              if(curr.right!=null) nextLevel.add(curr.right);
     *              if(prev!=null){
     *                  prev.right=curr;
     *                  curr.left=prev;
     *                  prev=curr;
     *              }
     *         }
     *         level++;
     *         q=nextLevel;
     *         nextLevel=new ArrayDequeue<>();
     *     }
     * }
     *
     * */
}
