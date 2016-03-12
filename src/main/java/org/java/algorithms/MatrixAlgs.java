package org.java.algorithms;

import java.util.Arrays;
import java.util.BitSet;

/**
 */
public class MatrixAlgs {
    public static void main(String[] args) {
        int[][] arr = {
            { 1, 2, 3, 4, 5 },
            { 6, 7, 8, 9, 10 },
            { 11, 12, 13, 14, 15 },
            { 16, 17, 18, 19, 20 },
            { 21, 22, 23, 24, 25 }
        };
        rotate90(arr);
        print(arr);
        char[] str = "aabaabc".toCharArray();
        System.out.println(Arrays.toString(successor(str)));
    }

    public static void print(int[][] arr) {
        for (int i = 0; i < arr.length; ++i) {
            for (int j = 0; j < arr[i].length; ++j) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void rotate90(int[][] arr) {
        for (int i = 0; i < arr.length / 2; ++i) {
            for (int j = i; j < arr.length - i - 1; ++j) {
                int t = arr[i][j];
                arr[i][j] = arr[arr.length - 1 - j][i];
                arr[arr.length - 1 - j][i] = arr[arr.length - 1 - i][arr.length - 1 - j];
                arr[arr.length - 1 - i][arr.length - 1 - j] = arr[j][arr.length - 1 - i];
                arr[j][arr.length - 1 - i] = t;
            }
        }
    }

    public static boolean isSymmetric(long[][] arr, int n) {
        for (int i = 0; i < n / 2; ++i) {
            for (int j = i; j < n - i; ++j) {
                if (arr[i][j] != arr[n - i - 1][n - j - 1])
                    return false;
            }
        }
        return true;
    }

    public static char[] successor(char[] str) {
        int chInd = -1;
        for (int i = str.length - 1; i > 0; --i) {
            if (str[i] > str[i - 1]) {
                chInd = i - 1;
                break;
            }
        }
        if (chInd == -1) {
            return null;
        }
        int destInd = chInd + 1;
        for (int i = chInd + 2; i < str.length; ++i) {
            if (str[i] < str[destInd] && str[i] > str[chInd]) {
                destInd = i;
            }
        }
        char t = str[chInd];
        str[chInd] = str[destInd];
        str[destInd] = t;
        Arrays.sort(str, chInd + 1, str.length);
        return str;
    }
}
