package org.java.algorithms.dynamic;

/**
 * Given a string S, find the longest palindromic subsequence.
 */
public class LongestPalindromicSubseq {
    public static void main(String[] args) {
        System.out.println(lps("banana"));
    }

    static String lps(String s1) {
        char[] c1 = s1.toCharArray();
        int[][] dp = new int[c1.length][c1.length];
        int[][] parent = new int[c1.length + 1][c1.length + 1];
        for (int i = 0; i < c1.length; ++i) {
            dp[i][i] = 1;
        }
        for (int len = 1; len <= c1.length; ++len) {
            for (int i = 0; i < c1.length - len; ++i) {
                if (c1[i] == c1[i + len]) {
                    dp[i][i + len] = dp[i + 1][i + len - 1] + 2;
                } else {
                    dp[i][i + len] = Math.max(dp[i + 1][i + len], dp[i][i + len - 1]);
                }
            }
        }
        System.out.println(dp[0][c1.length - 1]);
        return "";
    }
}