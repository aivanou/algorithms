package org.java.algorithms.dynamic;

import java.util.Arrays;

/**
 * https://community.topcoder.com/stat?c=problem_statement&pm=1215&rd=4555
 */
public class StripePainter {
    public static void main(String[] args) {
        String p = "BECBBDDEEBABDCADEAAEABCACBDBEECDEDEACACCBEDABEDADD";
        System.out.println(minStrokes(p));
    }

    public static int minStrokes(String stripes) {
        int mlen = stripes.length();
        int[][] dp = new int[mlen][mlen];
        for (int i = 0; i < mlen; ++i) {
            for (int j = 0; j < mlen; ++j) {
                dp[i][j] = 1000;
            }
            dp[i][i] = 1;
        }
        for (int i = 1; i < mlen; ++i) {
            for (int j = 0; j < mlen - i; ++j) {
                for (int k = j; k < j + i; ++k) {
                    dp[j][j + i] = Math.min(
                        dp[j][j + i],
                        dp[j][k] + dp[k + 1][j + i] -
                            (stripes.charAt(j) == stripes.charAt(j + i) ? 1 : 0));
                }
            }
        }
        return dp[0][mlen - 1];
    }

    static int[][] dpRec = new int[1000][1000];
    static int count = 0;

    public static int minStrokesRec(char[] pattern, char[] current, int strokes) {
        int pos = -1;
        for (int i = 0; i < pattern.length; ++i) {
            if (pattern[i] != current[i]) {
                pos = i;
                break;
            }
        }
        if (pos == -1) {
            return strokes;
        }
        count++;
        int i1 = Math.abs(new String(pattern).hashCode()) % 1000;
        int i2 = Math.abs(new String(current).hashCode()) % 1000;
        if (dpRec[i1][i2] != 0)
            return dpRec[i1][i2];
        int minStrokes = Integer.MAX_VALUE;
        for (int i = pos; i < pattern.length; ++i) {
            char[] newCurrent = Arrays.copyOf(current, current.length);
            for (int j = pos; j <= i; ++j) {
                newCurrent[j] = pattern[pos];
            }
            //            System.out.println(Arrays.toString(newCurrent));
            int currStrokes = minStrokesRec(pattern, newCurrent, strokes + 1);
            minStrokes = Math.min(minStrokes, currStrokes);
        }
        dpRec[i1][i2] = minStrokes;
        return minStrokes;
    }
}
