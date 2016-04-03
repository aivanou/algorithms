package org.java.algorithms.dynamic;

/**
 * You have a set of n integers each in the range 0 ... K. Partition these integers into two subsets such that you minimize |S1 - S2|, where S1 and S2 denote
 * the sums of the elements in each of the two subsets.
 */
public class BalancedPartition {
    public static void main(String[] args) {
    }

    static int find(int[] seq) {
        int sum = 0;
        for (int v : seq)
            sum += v;
        boolean[][] dp = new boolean[seq.length][sum + 1];
        for (int i = 0; i < seq.length; ++i)
            dp[i][0] = true;
        for (int s = 0; s <= sum; ++s) {
            for (int i = 0; i < seq.length; ++i) {
                dp[i][s] = dp[i][s - 1];
                if (s >= seq[i]) {
                    dp[i][s] = dp[i][s] || dp[i - i][s - seq[i]];
                }
            }
        }
        return 1;
    }
}
