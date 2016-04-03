package org.java.algorithms.dynamic;

import java.util.Arrays;

/**
 * https://community.topcoder.com/stat?c=problem_statement&pm=1166&rd=4705
 */
public class Jewelry {
    public static void main(String[] args) {
        System.out.println(howMany(new int[] { 1, 2, 3, 4, 5, 6, 6 }));
    }

    public static long howMany(int[] values) {
        int max = 30000;
        int n = values.length;

        long[][] bin = new long[n + 1][n + 1];
        for (int i = 0; i <= n; ++i) {
            bin[i][0] = 1;
            for (int j = 1; j <= i; ++j)
                bin[i][j] = bin[i - 1][j] + bin[i - 1][j - 1];
        }
        for (int i = 0; i <= n; ++i) {
            System.out.println(Arrays.toString(bin[i]));
        }

        long[][] waysAbove = new long[max + 1][31];
        long[][] waysBelow = new long[max + 1][31];
        Arrays.sort(values);
        waysBelow[0][0] = 1;
        waysAbove[0][0] = 1;
        long count = 0;
        for (int i = 1; i <= values.length; ++i) {
            for (int s = 0; s <= max; ++s) {
                waysBelow[s][i] = waysBelow[s][i - 1];
                if (s >= values[i - 1])
                    waysBelow[s][i] += waysBelow[s - values[i - 1]][i - 1];
                waysAbove[s][i] = waysAbove[s][i - 1];
                if (s >= values[n - i])
                    waysAbove[s][i] += waysAbove[s - values[n - i]][i - 1];
            }
        }
        for (int i = 0; i < values.length; ++i) {
            int len = 1;
            while (i + len < values.length && values[i] == values[i + len])
                len++;
            for (int j = i; j < i + len; ++j)
                for (int s = (j - i + 1) * values[i]; s <= max; ++s) {
                    count += bin[len][j - i + 1] *
                        waysBelow[s - (j - i + 1) * values[i]][i] * waysAbove[s][n - 1 - j];
                }
            i += len - 1;
        }
        return count;
    }
}
