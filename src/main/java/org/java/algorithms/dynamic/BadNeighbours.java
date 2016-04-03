package org.java.algorithms.dynamic;

/**
 */
public class BadNeighbours {
    public static void main(String[] args) {
        int[] d = new int[] { 94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61, 6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397, 52, 72, 37, 51, 1, 81,
            45, 435, 7, 36, 57, 86, 81, 72 };
        //        int[] d = new int[] { 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 };
        int[] dp1 = new int[d.length + 1];
        int[] dp2 = new int[d.length + 1];
        dp1[0] = d[0];
        dp1[1] = d[1];
        dp1[2] = Math.max(dp1[1], dp1[0] + d[2]);
        dp2[0] = d[1];
        dp2[1] = d[2];
        dp2[2] = Math.max(dp2[1], dp2[0] + d[3]);

        for (int i = 3; i < d.length - 1; ++i) {
            dp1[i] = Math.max(dp1[i - 2], dp1[i - 3]) + d[i];
            dp1[i] = Math.max(dp1[i], dp1[i - 1]);
        }
        for (int i = 4; i < d.length; ++i) {
            dp2[i] = Math.max(dp2[i - 2], dp2[i - 3]) + d[i];
            dp2[i] = Math.max(dp2[i], dp2[i - 1]);
        }
        System.out.println(Math.max(dp1[d.length - 2], dp2[d.length - 2]));
    }
}
