package org.java.algorithms.dynamic;

/**
 * Given n dice each with m faces, numbered from 1 to m, find the number of  ways to get sum X. X is the summation of values on each face when all  the dice
 * are
 * thrown.
 */
public class DiceThrow {
    public static void main(String[] args) {
        int n = 10;
        int m = 20;
        int x = 100;
        int[][] dp = new int[n + 1][x + 1];
    }

    static long solve(int n, int m, int x, int[][] dp) {
        if (n == 1) {
            if (x > m)
                return 0;
            return 1;
        }
        if (dp[n][x] != 0)
            return dp[n][x];
        int sum = 0;
        for (int i = 1; i <= m; ++i) {
            sum += solve(n - 1, m, x - i, dp);
        }
        dp[n][x] = sum;
        return sum;
    }
}
