package org.java.algorithms.dynamic;

import java.util.Arrays;

/**
 * Imagine you have a special keyboard with following four keys: Key 1: Prints 'A' on screenKey 2: (Ctrl-A): Select screen Key 3: (Ctrl-C): Copy selection to
 * bufferKey 4: (Ctrl-V): Print buffer on screen appending it after what has already been printed. If you can only press the keyboard for N times (with the
 * above four keys), write a program to produce maximum numbers of A's.
 */
public class MaxNumberOfA {
    public static void main(String[] args) {
        maxA(14);
        System.out.println(maxA1(14, new int[15]));
    }

    static int maxA(int n) {
        int[] dp = new int[n + 3];
        int[] b = new int[n + 3];
        dp[1] = 1;
        for (int i = 1; i <= n; ++i) {
            dp[i + 1] = Math.max(dp[i + 1], dp[i] + 1);
            dp[i + 1] = Math.max(dp[i + 1], dp[i] + b[i]);
            b[i + 2] = dp[i];
        }
        System.out.println(Arrays.toString(dp));
        return 1;
    }

    static int maxA1(int n, int[] dp) {
        if (n <= 6)
            return n;
        int max = 0, mult = 2;
        for (int i = n - 3; i >= 0; --i) {
            if (dp[i] == 0) {
                dp[i] = maxA1(i, dp);
            }
            int curr = mult * dp[i];
            max = Math.max(max, curr);
            mult++;
        }
        return max;
    }
}