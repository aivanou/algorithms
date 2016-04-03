package org.java.algorithms.dynamic;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 */
public class Billboards {
    public static void main(String[] args)throws Exception {
        //System.out.println(solve2(new long[] { 1, 2, 3, 1, 6, 10 }, 2));
        Scanner  s= new Scanner(System.in);
        int n = s.nextInt(); int k=s.nextInt();
        long[] arr=new long[n];
        for(int i=0;i<n;++i) arr[i]=s.nextLong();
        System.out.println(solve1(arr,k));
    }

    static long solve1(long[] arr, int k) {
        long sum = 0;
        for (long v : arr) {
            sum += v;
        }
        int n = arr.length;
        long[] dp = new long[n + 1];
        for (int i = k + 1; i <= n; ++i) {
            long min = Integer.MAX_VALUE;
            for (int j = i - k; j < i; ++j) {
                min = Math.min(min, dp[j - 1] + arr[j - 1]);
            }
            dp[i] = min;
        }
        return sum - dp[n];
    }

    static long solve2(long[] arr, int k) {
        long sum = 0;
        for (long v : arr) {
            sum += v;
        }
        new ArrayList<String>().toArray(new String[]{});
        int n = arr.length;
        PriorityQueue<Long> q = new PriorityQueue<>();
        long[] g = new long[n + 1];
        for (int i = 0; i < k; ++i) {
            q.add(arr[i]);
            g[i + 1] = arr[i];
        }
        long[] dp = new long[n + 1];
        for (int i = k + 1; i <= n; ++i) {
            g[i] = dp[i - 1] + arr[i - 1];
            q.add(g[i]);
            dp[i] = q.peek();
            q.remove(g[i - k]);
        }
        return sum - dp[n];
    }

}
