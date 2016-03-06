package org.java.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class GrayCodes {
    public static void main(String[] args) {
        generate(16);
    }

    public static void generate(int n) {
        List<Integer> codes = new ArrayList<>(n);
        codes.add(0);
        codes.add(1);
        generate(codes, n, 1);
        codes.stream().map(v -> Integer.toBinaryString(v)).forEach(System.out::println);
    }

    private static void generate(List<Integer> codes, int n, int nbit) {
        if (codes.size() >= n)
            return;
        reflect(codes);
        append(codes, nbit);
        generate(codes, n, nbit + 1);
    }

    private static void append(List<Integer> codes, int nbit) {
        for (int i = codes.size() / 2; i < codes.size(); ++i) {
            int v = codes.get(i);
            codes.set(i, v | (1 << nbit));
        }
    }

    private static void reflect(List<Integer> codes) {
        int len = codes.size();
        for (int i = 0; i < len; ++i) {
            codes.add(codes.get(len - i - 1));
        }
    }
}
