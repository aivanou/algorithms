package org.scala.algorithms.strings;

import java.util.Arrays;

/**
 * Given a string S, find the minimum number of cuts required to separate the string into a set of palindromes.
 */
public class MinCuts {
    public static void main(String[] args) {
        compute("banana");
    }

    static int compute(String str) {
        int n = str.length();
        char[] c = str.toCharArray();
        boolean[][] pals = new boolean[n][n];
        for (int i = 0; i < n; ++i)
            pals[i][i] = true;
        for (int len = 1; len < n; ++len) {
            for (int i = 0; i < n - len; ++i)
                if (c[i] == c[i + len])
                    pals[i][i + len] = true;
        }
        int[] mc = new int[n];
        mc[0] = 0;
        for (int i = 1; i < n; ++i) {
            int cm = 100000;
            if (pals[0][i]) {
                mc[i] = 0;
                continue;
            }
            for (int j = 0; j < i; ++j) {
                if (pals[j + 1][i]) {
                    cm = Math.min(cm, mc[j] + 1);
                }
            }
            mc[i] = cm;
        }
        System.out.println(Arrays.toString(mc));
        return 1;
    }
}
