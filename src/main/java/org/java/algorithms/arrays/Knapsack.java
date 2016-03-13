package org.java.algorithms.arrays;

/**
 */
public class Knapsack {
    public static void main(String[] args) {
        int[] w = new int[] { 13, 20 };
        int[] v = new int[] { 32, 43};
        int mW = 701;
        unboundKnapsack(w, v, mW);
    }

    static void unboundKnapsack(int[] w, int[] v, int mW) {
        int[] m = new int[mW + 1];
        int[] inTheBag = new int[mW + 1];
        int[] things = new int[w.length + 1];
        for (int i = 1; i <= mW; ++i) {
            int cV = 0;
            int thing = 0;
            for (int j = 0; j < w.length; ++j) {
                if (i < w[j]) {
                    continue;
                }
                if (m[i - w[j]] + v[j] > cV) {
                    thing = j;
                    cV = m[i - w[j]] + v[j];
                }
            }
            if (cV > m[i - 1]) {
                inTheBag[i] = thing;
                m[i] = cV;
            } else {
                inTheBag[i] = -1;
                m[i] = m[i - 1];
            }
        }
        System.out.println(m[mW]);
        int ind = mW;
        while (ind > 0) {
            while (inTheBag[ind] == -1 && ind > 0) {
                ind -= 1;
            }
            things[inTheBag[ind]] += 1;
            ind -= w[inTheBag[ind]];
        }
        for (int i = 0; i < things.length; ++i) {
            System.out.print(i + " : " + things[i] + "; ");
        }
        System.out.println();
    }
}
