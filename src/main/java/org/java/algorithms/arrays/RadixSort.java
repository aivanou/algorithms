package org.java.algorithms.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 */
public class RadixSort {
    public static void main(String[] args) {
        int[] arr = new int[] { 893, 8921, 809, 23, 1134, 42 };
        radixSort(arr, 4);
        System.out.println(Arrays.toString(arr));
    }

    static void radixSort(int[] arr, int mpos) {
        for (int i = 1; i <= mpos; ++i) {
            sortByPos(arr, i);
        }
    }

    static void sortByPos(int[] arr, int pos) {
        List<Stack<Integer>> objects = new ArrayList<>(10);
        for (int i = 0; i < 10; ++i)
            objects.add(new Stack<>());
        int[] count = new int[11];
        for (int num : arr) {
            int dig = getDigit(num, pos);
            count[dig]++;
            objects.get(dig).push(num);
        }
        for (int i = 1; i < count.length; ++i) {
            count[i] += count[i - 1];
        }
        int[] out = new int[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            int dig = getDigit(arr[i], pos);
            out[count[dig] - 1] = objects.get(dig).pop();
            --count[dig];
        }
        System.arraycopy(out, 0, arr, 0, arr.length);
    }

    static int getDigit(int num, int pos) {
        int cp = 1;
        while (pos != cp) {
            num /= 10;
            cp++;
        }
        return num % 10;
    }
}
