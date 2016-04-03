package org.java.algorithms.dynamic;

/**
 * https://community.topcoder.com/stat?c=problem_statement&pm=2829&rd=5072
 */
public class QuickSums {
    public static void main(String[] args) {
        String number = "9230560001";
        int sum = 71;
        System.out.println(minSumsRec(number.toCharArray(), sum, 0, 0, 0) - 1);
        System.out.println(minSums(number.toCharArray(), sum));
    }

    public static int minSums(char[] numbers, int sum) {
        int n = numbers.length;
        int[][] dp = new int[n + 1][sum + 1];
        for (int i = 0; i <= n; ++i)
            for (int j = 0; j <= sum; ++j)
                dp[i][j] = Integer.MAX_VALUE;
        dp[0][numbers[0] - '0'] = 0;
        for (int i = 1; i < n; ++i) {
            int num = getNumber(numbers, 0, i);
            if (num <= sum)
                dp[i][num] = 0;
            for (int s = 1; s <= sum; ++s) {
                int ind = 1;
                for (int k = i; k >= 1; --k) {
                    int curr = getNumber(numbers, k, i);
                    if (curr > sum || s < curr) {
                        ind += 1;
                        continue;
                    }
                    if (dp[i - ind][s - curr] == Integer.MAX_VALUE) {
                        ind += 1;
                        continue;
                    }
                    dp[i][s] = Math.min(dp[i - ind][s - curr] + 1, dp[i][s]);
                    ind += 1;
                }
            }
        }
        return dp[n - 1][sum];
    }

    public static int minSumsRec(char[] numbers, int sum, int currSum, int nadd, int pos) {
        if (currSum > sum)
            return Integer.MAX_VALUE;
        else if (currSum == sum)
            return nadd;
        int minNAdd = Integer.MAX_VALUE;
        for (int i = pos; i < numbers.length; ++i) {
            int curr = getNumber(numbers, pos, i);
            int currNAdd = minSumsRec(numbers, sum, currSum + curr, nadd + 1, i + 1);
            minNAdd = Math.min(minNAdd, currNAdd);
        }
        return minNAdd;
    }

    private static int getNumber(char[] n, int s, int e) {
        int num = 0;
        for (int i = s; i <= e; ++i) {
            num = num * 10 + n[i] - '0';
        }
        return num;
    }
}
