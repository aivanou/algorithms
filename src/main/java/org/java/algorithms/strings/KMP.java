package org.java.algorithms.strings;

import java.util.Arrays;

/**
 */
public class KMP {
    public static void main(String[] args) {
//        System.out.println(find("1234123213221321341234", "213"));
        System.out.println(Arrays.toString(computeLPS("aaabaaa".toCharArray())));
    }

    public static int find(String text, String pattern) {
        char[] txt = text.toCharArray();
        char[] p = pattern.toCharArray();
        int indT = 0;
        int indP = 0;
        int[] lps = computeLPS(p);
        while (indT < txt.length) {
            if (txt[indT] == p[indP]) {
                ++indT;
                ++indP;
            }
            if (indP == p.length) {
                return indT - indP;
            }
            if (txt[indT] != p[indP]) {
                if (indP != 0)
                    indP = lps[indP - 1];
                else
                    ++indT;
            }
        }
        return -1;
    }

    public static int[] computeLPS(char[] p) {
        int[] lps = new int[p.length];
        lps[0] = 0;
        int prevLen = 0;
        int ind = 1;
        while (ind < p.length) {
            if (p[ind] == p[prevLen]) {
                lps[ind++] = ++prevLen;
            } else {
                if (prevLen != 0) {
                    prevLen = lps[prevLen - 1];
                } else {
                    lps[ind++] = 0;
                }
            }
        }
        return lps;
    }
}
