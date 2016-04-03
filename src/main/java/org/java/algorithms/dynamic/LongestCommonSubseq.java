package org.java.algorithms.dynamic;

/**
 */
public class LongestCommonSubseq {
    public static void main(String[] args) {
        System.out.println(lcs("banana", "ananab"));
    }

    static String lcs(String s1, String s2) {
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        int[][] dp = new int[c1.length + 1][c2.length + 1];
        int[][] parent = new int[c1.length + 1][c2.length + 1];
        for (int i = 1; i <= c1.length; ++i) {
            for (int j = 1; j <= c2.length; ++j) {
                if (c1[i - 1] == c2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    parent[i][j] = 0;
                } else {
                    if (dp[i - 1][j] > dp[i][j - 1]) {
                        parent[i][j] = 1;
                    } else
                        parent[i][j] = 2;
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        StringBuilder bldr = new StringBuilder();
        int i = c1.length;
        int j = c2.length;
        while (i > 0 && j > 0) {
            if (parent[i][j] == 0) {
                bldr.append(c1[i - 1]);
                i--;
                j--;
            } else if (parent[i][j] == 1) {
                i--;
            } else {
                j--;
            }
        }
        return bldr.reverse().toString();
    }
}
