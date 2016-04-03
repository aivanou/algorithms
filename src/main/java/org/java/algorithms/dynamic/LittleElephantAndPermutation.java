package org.java.algorithms.dynamic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * https://community.topcoder.com/stat?c=problem_statement&pm=12735
 */
public class LittleElephantAndPermutation {
    public static void main(String[] args) {
        int k = 74;
        int n = 10;
        for (int i = 0; i < n + 1; ++i) {
            for (int j = 0; j < n + 1; ++j) {
                for (int v = 0; v < k + 1; ++v) {
                    dp[v][i][j] = -1;
                }
            }
        }
        System.out.println(findRec(k, n, 0));
    }

    static final long MOD = 1000000007L;
    static final int N = 51;
    static final int K = 2501;
    static long[][][] dp = new long[K][N][N];

    public static long findRec(int k, int empty, int occupied) {
        if (dp[k][empty][occupied] != -1) {
            return dp[k][empty][occupied];
        }
        long res = 0;
        int number = empty + occupied;
        if (number == 0) {
            res = k == 0 ? 1 : 0;
            dp[k][empty][occupied] = res;
            return res;
        }
        if (empty >= 1) {
            res += (findRec(Math.max(0, k - number), empty - 1, occupied) * empty) % MOD;
            res += (findRec(Math.max(0, k - number), empty - 1, occupied) * 2 * empty * occupied) % MOD;
        }
        if (empty >= 2) {
            res += (findRec(Math.max(0, k - 2 * number), empty - 2, occupied + 1) * empty * (empty - 1)) % MOD;
        }
        if (occupied >= 1) {
            res += (occupied * occupied * findRec(k, empty, occupied - 1)) % MOD;
        }

        dp[k][empty][occupied] = res % MOD;
        return res % MOD;
    }

    public static int nPermRec(int n) {
        return nPermRec(n, new HashSet<>(), 0);
    }

    public static int nPermRec(int n, Set<Integer> used, int len) {
        if (len == n) {
            return 1;
        }
        int count = 0;
        for (int i = 0; i < n; ++i) {
            if (!used.contains(i + 1)) {
                used.add(i + 1);
                count += nPermRec(n, used, len + 1);
                used.remove(i + 1);
            }
        }
        return count;
    }
}
