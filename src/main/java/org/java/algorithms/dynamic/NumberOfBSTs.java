package org.java.algorithms.dynamic;

/**
 */
public class NumberOfBSTs {
    public static void main(String[] args) {
        System.out.println(compute(3, new int[11]));
    }

    static int compute(int n, int[] bst) {
        if (n <= 1)
            return 1;
        if (bst[n] != 0)
            return bst[n];
        int sum = 0;
        for (int i = 1; i <= n; ++i) {
            int left = compute(i - 1, bst);
            int right = compute(n - i, bst);
            sum += left * right;
        }
        bst[n] = sum;
        return sum;
    }
}
