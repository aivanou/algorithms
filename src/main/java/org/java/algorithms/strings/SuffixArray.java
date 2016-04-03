package org.java.algorithms.strings;

import java.util.Arrays;
import java.util.Comparator;

/**
 */

import java.util.*;

public class SuffixArray {

    public static void main(String[] args) {
        String text = "mississippi";
        int[] sfx = buildSuffixArray(text);
        System.out.println(Arrays.toString(sfx));
        int[] lcp = computeLcp(text, sfx);
        System.out.println(search(text, "ssip"));
    }

    public static boolean search(String text, String pattern) {
        int[] sfx = buildSuffixArray(text);
        int l = 0, r = sfx.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            int res = 0;
            for (int i = 0; i < pattern.length(); ++i) {
                if (sfx[mid] + i > text.length())
                    return false;
                if (pattern.charAt(i) > text.charAt(sfx[mid] + i)) {
                    res = 1;
                    break;
                } else if (pattern.charAt(i) < text.charAt(sfx[mid] + i)) {
                    res = -1;
                    break;
                }
            }
            if (res == 0)
                return true;
            else if (res > 0) {
                l = mid + 1;
            } else {
                r = -mid - 1;
            }
        }
        return false;
    }

    private static int[] zFunction(String text) {
        int n = text.length();
        int[] z = new int[n];
        for (int i = 1, l = 0, r = 0; i < n; ++i) {
            z[i] = Math.min(r - i + 1, z[i - l]);
            while (i + z[i] < n && text.charAt(z[i]) == text.charAt(z[i] + i))
                z[i] += 1;
            if (z[i] + i - 1 > r) {
                l = i;
                r = z[i] + i - 1;
            }
        }
        return z;
    }

    private static int[] computeLcp(String text, int[] sfx) {
        int n = text.length();
        int[] order = new int[sfx.length];
        int[] lcp = new int[sfx.length];
        for (int i = 0; i < n; ++i) {
            order[sfx[i]] = i;
        }
        int currentLcp = 0;
        for (int i = 0; i < n; ++i) {
            if (currentLcp != 0) {
                currentLcp--;
            }
            if (order[i] == n - 1) {
                currentLcp = 0;
                continue;
            }
            int j = sfx[order[i] + 1];
            while (i + currentLcp < n && j + currentLcp < n &&
                text.charAt(i + currentLcp) == text.charAt(j + currentLcp))
                currentLcp++;
            lcp[order[i]] = currentLcp;
        }
        return lcp;
    }

    private static int[] buildSuffixArray(String text) {
        int n = text.length();
        Suffix[] suffices = new Suffix[n];
        int[] sfx = new int[n];
        for (int i = 0; i < n; ++i) {
            sfx[i] = text.charAt(i);
        }
        int[] nextSfx = new int[n];
        for (int cnt = 1; cnt < n; cnt *= 2) {
            for (int i = 0; i < n; ++i) {
                suffices[i] = new Suffix(i, sfx[i], i + cnt < n ? sfx[i + cnt] : -1);
            }
            Arrays.sort(suffices, (s1, s2) -> {
                if (s1.rank1 == s2.rank1) {
                    return Integer.compare(s1.rank2, s2.rank2);
                }
                return Integer.compare(s1.rank1, s2.rank1);
            });
            for (int i = 0; i < n; i++)
                nextSfx[suffices[i].idx] = i > 0 &&
                    suffices[i].rank1 == suffices[i - 1].rank1 &&
                    suffices[i].rank2 == suffices[i - 1].rank2 ? nextSfx[suffices[i - 1].idx] : i;
            sfx = nextSfx;
        }
        for (int i = 0; i < n; ++i) {
            sfx[i] = suffices[i].idx;
        }
        return sfx;
    }

    private static class Suffix {
        int idx;
        int rank1;
        int rank2;

        public Suffix(int idx, int rank1, int rank2) {
            this.idx = idx;
            this.rank1 = rank1;
            this.rank2 = rank2;
        }

        @Override public String toString() {
            return "{" +
                "idx=" + idx +
                ", " + rank1 +
                ", " + rank2 +
                '}';
        }
    }
}