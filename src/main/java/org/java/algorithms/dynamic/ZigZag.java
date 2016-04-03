package org.java.algorithms.dynamic;

/**
 * https://community.topcoder.com/stat?c=problem_statement&pm=1259&rd=4493
 */
public class ZigZag {
    public static void main(String[] args) {
        int[] n = new int[] { 1, 17, 5, 10, 13, 15, 10, 5, 16, 8 };
        System.out.println(solve1(n));
    }

    public static int solve1(int[] arr) {
        int n = arr.length;
        int[] dp1 = new int[n];
        int[] dp2 = new int[n];
        for (int i = 0; i < arr.length; ++i) {
            dp1[i] = 1;
            dp2[i] = 1;
            for (int j = 0; j < i; ++j) {
                if (arr[i] > arr[j]) {
                    dp1[i] = Math.max(dp2[j] + 1, dp1[i]);
                } else {
                    dp2[i] = Math.max(dp1[j] + 1, dp2[i]);
                }
            }
        }
        return Math.max(dp1[n - 1], dp2[n - 1]);
    }
}
