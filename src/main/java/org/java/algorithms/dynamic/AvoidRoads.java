package org.java.algorithms.dynamic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://community.topcoder.com/stat?c=problem_statement&pm=1889&rd=4709
 */
public class AvoidRoads {
    public static void main(String[] args) {
        System.out.println(numWays(35, 31, new String[] {}));
    }

    public static long numWays(int w, int h, String[] bad) {
        long[][] ways = new long[h + 1][w + 1];
        int[][] blocks = new int[bad.length][4];
        for (int i = 0; i < bad.length; ++i) {
            String[] t = bad[i].split(" ");
            blocks[i][0] = Integer.valueOf(t[0]);
            blocks[i][1] = Integer.valueOf(t[1]);
            blocks[i][2] = Integer.valueOf(t[2]);
            blocks[i][3] = Integer.valueOf(t[3]);
        }
        for (int i = 0; i <= h; ++i)
            ways[i][0] = 1;
        for (int i = 0; i <= w; ++i)
            ways[0][i] = 1;
        for (int i = 1; i <= h; ++i) {
            for (int j = 1; j <= w; ++j) {
                // i-1,j -> i, j; i,j-1 -> i, j
                boolean can1 = true, can2 = true;
                for (int k = 0; k < blocks.length; ++k) {

                }
                ways[i][j] = ways[i - 1][j] + ways[i][j - 1];
            }
        }
        return ways[h][w];
    }
}
