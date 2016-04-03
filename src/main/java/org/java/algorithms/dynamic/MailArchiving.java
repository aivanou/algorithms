package org.java.algorithms.dynamic;

/**
 * https://community.topcoder.com/stat?c=problem_statement&pm=4457&rd=6539
 */
public class MailArchiving {
    public static void main(String[] args) {
        //        String[] f = new String[] { "a", "b", "b", "c", "d", "e", "d", "e", "d", "e", "c", "c", "a", "a", "a", "f", "g", "g", "f", "a", "h", "h", "i", "j", "i",
        //            "j", "a", "a", "k", "k", "l", "m", "k", "l", "m", "a", "o", "o", "p", "a" };
        String[] f = new String[] { "a", "b", "a", "c", "a", "a", "b", "a", "c", "d", "a" };
        System.out.println(minSelections(f));
    }

    public static int minSelections(String[] destFolders) {
        int n = destFolders.length;
        int[] names = new int[n];
        for (int i = 0; i < n; ++i)
            names[i] = destFolders[i].hashCode();
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= n; ++j) {
                dp[i][j] = 1000000;
            }
            dp[i][i] = 1;
        }
        for (int i = 1; i < n; ++i) {
            for (int j = 0; j < n - i; ++j) {
                for (int split = j; split < j + i; ++split) {
                    dp[j][i + j] = Math.min(dp[j][i + j],
                        dp[j][split] +
                            dp[split + 1][i + j] - (names[j] == names[j + i] ? 1 : 0));
                }
            }
        }
        return dp[0][n - 1];
    }
}
