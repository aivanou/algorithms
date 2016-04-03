package org.java.algorithms.arrays;

/**
 */
public class MaxContigousSubArray {
    public static void main(String[] args) {
        int[] arr = new int[] { 5, 6, -7, 2, 3 };
        int currentMax = arr[0];
        int totalMax = arr[0];
        for (int i = 1; i < arr.length; ++i) {
            currentMax = Math.max(arr[i], currentMax + arr[i]);
            totalMax = Math.max(totalMax, currentMax);
        }
        System.out.println(totalMax);
    }
}
