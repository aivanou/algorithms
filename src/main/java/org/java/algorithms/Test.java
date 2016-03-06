package org.java.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<String> arr = new ArrayList<>(n);
        for (int i = 0; i < n; ++i)
            arr.add(sc.nextLine());
        arr.sort((o1, o2) -> {
            for (int i = 0; i < Math.min(o1.length(), o2.length()); ++i)
                if (o1.charAt(i) < o2.charAt(i))
                    return -1;
                else if (o1.charAt(i) > o2.charAt(i))
                    return 1;
            if (o1.length() > o2.length()) {
                for (int i = Math.min(o1.length(), o2.length()); i < o1.length(); ++i)
                    if (o1.charAt(i) > o2.charAt(o2.length() - 1))
                        return 1;
                    else if (o1.charAt(i) < o2.charAt(o2.length() - 1))
                        return -1;
            } else if (o1.length() < o2.length()) {
                for (int i = Math.min(o1.length(), o2.length()); i < o1.length(); ++i)
                    if (o1.charAt(o1.length() - 1) > o2.charAt(i))
                        return -1;
                    else if (o1.charAt(o1.length() - 1) < o2.charAt(i))
                        return 1;
            }

            return o1.compareTo(o2);
        });
    }
}
