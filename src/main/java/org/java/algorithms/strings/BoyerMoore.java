package org.java.algorithms.strings;

/**
 */
public class BoyerMoore {
    public static void main(String[] args) {
        System.out.println(find("1234123213221321341234", "213"));
    }

    public static int find(String text, String pattern) {
        char[] txt = text.toCharArray();
        char[] p = pattern.toCharArray();
        int[] btable = createBadChTable(p);
        int shift = 0;
        while (shift < (txt.length - p.length)) {
            int ind = p.length - 1;
            while (ind >= 0 && txt[shift + ind] == p[ind]) {
                ind--;
            }
            if (ind < 0)
                return shift;
            shift += Math.max(1, ind - btable[txt[ind + shift]]);
        }
        return -1;
    }

    public static int[] createBadChTable(char[] pattern) {
        int[] table = new int[256];
        for (int i = 0; i < 256; ++i) {
            table[i] = pattern.length;
        }
        for (int i = 0; i < pattern.length; ++i) {
            table[pattern[i]] = i;
        }
        return table;
    }
}
