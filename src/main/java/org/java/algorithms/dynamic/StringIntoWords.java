package org.java.algorithms.dynamic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Word break problem - You are given a long work which maybe combination of different words. You are also given a dictionary. You have to tell if words in
 * dictionary can be combined to form this long word.
 */
public class StringIntoWords {
    public static void main(String[] args) {
        Set<String> dict = new HashSet<>();
        dict.add("i");
        dict.add("a");
        dict.add("am");
        dict.add("ace");
        dict.add("want");
        dict.add("to");
        dict.add("buy");
        dict.add("chicken");
        split("iwanttobuychickenaceiiiiamaaamamaam", dict);
    }

    public static String[] split(String text, Set<String> dict) {
        boolean[][] dp = new boolean[text.length()][text.length()];
        int[][] splitPos = new int[text.length()][text.length()];
        for (int i = 0; i < text.length(); ++i) {
            for (int j = 0; j < text.length(); ++j) {
                splitPos[i][j] = -2;
            }
            if (dict.contains(text.charAt(i) + "")) {
                dp[i][i] = true;
                splitPos[i][i] = -1;
            }
        }
        for (int len = 2; len <= text.length(); ++len) {
            for (int start = 0; start <= text.length() - len; ++start) {
                int end = start + len - 1;
                if (dict.contains(text.substring(start, end + 1))) {
                    dp[start][end] = true;
                    splitPos[start][end] = -1;
                    continue;
                }
                for (int i = 0; i < end - start; ++i) {
                    if (dp[start][start + i] && dp[start + i + 1][end]) {
                        dp[start][end] = true;
                        splitPos[start][end] = i;
                        break;
                    }
                }
            }
        }
        rebuild(splitPos, text, 0, text.length() - 1);
        return null;
    }

    static void rebuild(int[][] split, String text, int i, int j) {
        if (split[i][j] == -2) {
            return;
        }
        if (split[i][j] == -1) {
            System.out.println(text.substring(i, j + 1));
            return;
        }
        int coord = split[i][j];
        rebuild(split, text, i, i + coord);
        rebuild(split, text, i + coord + 1, j);
    }
}
