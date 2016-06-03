package org.java.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

/**
 */
public class RandomShuffle {

    public static void main(String[] args) {
        //        System.out.println(findNumber(new int[] { 1, 1, 3, 3, 6, 6, 7, 7, 10, 10 }, 0, 10));
        System.out.println("tt");
    }

    static String toExcel(int n) {
        char[] table = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'g', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z' };
        int base = 26;
        StringBuilder bldr = new StringBuilder();
        while (n != 0) {
            int res = n % base;
            if (res == 0) {
                bldr.append('z');
                n = n / base - 1;
            } else {
                bldr.append(table[res - 1]);
                n /= base;
            }
        }
        return bldr.reverse().toString();
    }

    static int findNumber(int[] arr, int l, int r) {
        int mid = l + (r - l) / 2;
        if (mid == 0 || mid == arr.length - 1) {
            return arr[mid];
        } else {
            if (arr[mid] != arr[mid - 1] && arr[mid] != arr[mid + 1]) {
                return arr[mid];
            }
            if (arr[mid] == arr[mid - 1]) {
                if ((mid + 1) % 2 == 0) {
                    return findNumber(arr, mid + 1, r);
                } else {
                    return findNumber(arr, l, mid - 1);
                }
            } else if (arr[mid] == arr[mid + 1]) {
                if ((mid + 1) % 2 == 0) {
                    return findNumber(arr, l, mid - 1);
                } else {
                    return findNumber(arr, mid + 1, r);
                }
            }
        }
        return -1;
    }

    static void check() {
        Map<String, Integer> result = new HashMap<>();
        Random rand = new Random();
        for (int i = 0; i < 1000000; ++i) {
            int[] arr = new int[] { 1, 2, 3, 4 };
            fisherYatesShuffle(arr, max -> rand.nextInt(max + 1));
            String key = arrToString(arr);
            result.put(key, result.getOrDefault(key, 0) + 1);
        }
        for (String key : result.keySet()) {
            System.out.println(key + "  " + result.get(key));
        }
    }

    static String arrToString(int[] arr) {
        return String.format("%d%d%d%d", arr[0], arr[1], arr[2], arr[3]);
    }

    static void naiveShuffle(int[] arr, Function<Integer, Integer> genNumber) {
        for (int i = 0; i < arr.length; ++i) {
            int ind = genNumber.apply(arr.length - 1);
            swap(arr, i, ind);
        }
    }

    static void fisherYatesShuffle(int[] arr, Function<Integer, Integer> genNumber) {
        for (int i = arr.length - 1; i >= 0; --i) {
            int ind = genNumber.apply(i);
            swap(arr, i, ind);
        }
    }

    static void swap(int[] arr, int i, int j) {
        int v = arr[i];
        arr[i] = arr[j];
        arr[j] = v;
    }
}
