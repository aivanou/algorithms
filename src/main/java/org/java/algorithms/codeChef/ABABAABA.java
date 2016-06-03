package org.java.algorithms.codeChef;

import java.util.Scanner;

/**
 */
public class ABABAABA {
    public static void main(String[] args) throws Exception {
        //        Scanner s = new Scanner(System.in);
        //        int t = s.nextInt();
        //        s.nextLine();
        //        while (t-- > 0) {
        //            solve(s.nextLine());
        //        }
        solve("BABBBBBBA");
    }

    static void solve(String str) {
        char[] a = str.toCharArray();
        if (a.length == 1) {
            System.out.println("-1");
            return;
        } else if (a.length == 2) {
            if (a[0] == a[1]) {
                System.out.println(a[0]);
            } else {
                System.out.println("-1");
            }
            return;
        }
        int pa = 0, pb = 0;
        if (a.length <= 3) {
            for (int i = 0; i < a.length; ++i) {
                if (a[i] == 'A')
                    pa++;
                else if (a[i] == 'B')
                    pb++;
            }
            if (pa == 2)
                System.out.println('A');
            else if (pb == 2)
                System.out.println('B');
            else
                System.out.println("-1");
            return;
        }
        String res = "";
        if (a[0] == a[1] && a[0] != a[2]) {
            System.out.println(str.substring(1));
            return;
        } else if (a[a.length - 1] == a[a.length - 2] && a[a.length - 1] != a[a.length - 3]) {
            System.out.println(str.substring(0, str.length() - 1));
            return;
        }
        for (int i = 0; i < 3; ++i) {
            if (a[i] == 'A')
                pa++;
            else if (a[i] == 'B')
                pb++;
        }
        for (int i = 3; i < a.length; ++i) {
            if (a[i] == 'A')
                pa++;
            else if (a[i] == 'B')
                pb++;
            if (a[i - 1] == a[i - 2] && a[i - 1] != a[i] && a[i] == a[i - 3]) {
                res = str.substring(0, i - 1) + str.substring(i);
                break;
            }
        }
        if (!res.isEmpty()) {
            System.out.println(res);
        } else if (pa == 2)
            System.out.println('A');
        else if (pb == 2)
            System.out.println('B');
        else
            System.out.println("-1");
    }
}
