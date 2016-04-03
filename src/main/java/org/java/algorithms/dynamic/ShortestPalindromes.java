package org.java.algorithms.dynamic;

import java.util.HashMap;
import java.util.Map;

/**
 * https://community.topcoder.com/stat?c=problem_statement&pm=1861&rd=4630
 */
public class ShortestPalindromes {
    public static void main(String[] args) {
        System.out.println(shortest("race"));
        System.out.println(shortestMemo("lluwnwnthtnsnrhrkv,r893lf", new HashMap<>()));
    }

    public static String shortest(String base) {
        if (isPalindrome(base))
            return base;
        int n = base.length();
        if (base.charAt(0) == base.charAt(n - 1))
            return base.charAt(0) + shortest(base.substring(1, n - 1)) + base.charAt(0);
        String str1 = base.charAt(0) + shortest(base.substring(1)) + base.charAt(0);
        String str2 = base.charAt(n - 1) + shortest(base.substring(0, n - 1)) + base.charAt(n - 1);
        return str1.compareTo(str2) > 0 ? str2 : str1;
    }

    public static String shortestMemo(String base, Map<String, String> dp) {
        if (dp.containsKey(base))
            return dp.get(base);
        if (isPalindrome(base))
            return base;
        int n = base.length();
        if (base.charAt(0) == base.charAt(n - 1))
            return base.charAt(0) + shortest(base.substring(1, n - 1)) + base.charAt(0);
        String str1 = base.charAt(0) + shortest(base.substring(1)) + base.charAt(0);
        String str2 = base.charAt(n - 1) + shortest(base.substring(0, n - 1)) + base.charAt(n - 1);
        String ans = str1.compareTo(str2) > 0 ? str2 : str1;
        dp.put(base, ans);
        return ans;
    }

    static boolean isPalindrome(String str) {
        for (int i = 0; i < str.length() / 2; ++i)
            if (str.charAt(i) != str.charAt(str.length() - 1 - i))
                return false;
        return true;
    }
}
