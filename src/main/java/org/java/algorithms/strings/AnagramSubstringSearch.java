package org.java.algorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Anagram Substring Search
 */
public class AnagramSubstringSearch {
    public static void main(String[] args) {
        System.out.println(search("BACDGABCDA".toLowerCase().toCharArray(), "ABCD".toLowerCase().toCharArray()));
    }

    public static int search(char[] text, char[] p) {
        int[] ptrn = new int[50];
        int[] cnt = new int[50];
        for (int i = 0; i < p.length; ++i) {
            ptrn[p[i] - 'a']++;
            cnt[text[i] - 'a']++;
        }
        int mc = 0;
        if (eq(cnt, ptrn))
            mc++;
        for (int i = p.length; i < text.length; ++i) {
            cnt[text[i - p.length] - 'a']--;
            cnt[text[i] - 'a']++;
            if (eq(cnt, ptrn))
                mc++;
        }
        return mc;
    }

    public static boolean eq(int[] c1, int[] c2) {
        for (int i = 0; i < c1.length; ++i)
            if (c1[i] != c2[i])
                return false;
        return true;
    }
}
