package org.java.algorithms;

import javafx.util.Pair;

import java.util.*;

/**
 */
public class WorkSheet {
//    public static void main(String[] args) {
//        int[][][] dp = new int[10][10][2];
//        int[] coins = new int[] { 4, 5, 2, 1, 6, 7, 8, 4, 1, 3 };
//        System.out.println(maxScore(coins, 0, 0, 0, 0, 9, 0, dp));
//
//    }

    static void printAllPalindromes(char[] arr) {
        Map<Character, Integer> mp = new HashMap<>();
        for (char ch : arr) {
            if (!mp.containsKey(ch))
                mp.put(ch, 0);
            mp.put(ch, mp.get(ch) + 1);
        }
        printAllPalindromes(arr, 0, mp, new char[arr.length]);
    }

    static void printAllPalindromes(char[] arr, int pos, Map<Character, Integer> mp, char[] palindrome) {
        if (arr.length % 2 == 0 && pos * 2 >= arr.length) {
            System.out.println(Arrays.toString(palindrome));
            return;
        } else if (arr.length % 2 != 0 && pos * 2 == arr.length - 1) {
            //find the last ch
            System.out.println(Arrays.toString(palindrome));
            return;
        }
        for (Character ch : mp.keySet()) {
            int amount = mp.get(ch);
            if ((amount & 1) == 1)
                continue;
            for (int i = 1; i <= amount / 2; ++i) {
                mp.put(ch, mp.get(ch) - 2);
                palindrome[pos] = ch;
                palindrome[palindrome.length - 1 - pos] = ch;
                printAllPalindromes(arr, pos + 1, mp, palindrome);
                mp.put(ch, mp.get(ch) + 2);
            }
        }
    }

    static long mod = 1000000007;

    static int[][][] dp = new int[20][200][1500];

    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        int t = s.nextInt();
        while (t-- > 0) {
            int n = s.nextInt();
            int m = s.nextInt();
            Map<Integer, List<Pair<Integer, Integer>>> g = new HashMap<>();
            long[] dist = new long[n + 1];

            for (int i = 0; i <= n; ++i) {
                if (!g.containsKey(i))
                    g.put(i, new ArrayList<>());
                dist[i] = Integer.MAX_VALUE;
            }
            for (int i = 0; i < m; ++i) {
                int v1 = s.nextInt();
                int v2 = s.nextInt();
                int w = s.nextInt();
                g.get(v1).add(new Pair<>(v2, w));
                g.get(v2).add(new Pair<>(v1, w));
            }
            int st = s.nextInt();
            solve(g, n, st, dist);
        }
    }

    static void solve(Map<Integer, List<Pair<Integer, Integer>>> g, int n, int st, long[] dist) {
        boolean[] processed = new boolean[n + 1];
        Queue<Integer> pq = new PriorityQueue<>((i1, i2) -> {
            if (i1 == i2)
                return i1;
            return i2;
        });
        Queue<Pair<Integer, Integer>> q = new PriorityQueue<>((p1, p2) -> p1.getValue().compareTo(p2.getValue()));
        q.add(new Pair<>(st, 0));
        dist[st] = 0;
        while (!q.isEmpty()) {
            Pair<Integer, Integer> cp = q.poll();
            int v = cp.getKey();
            if (processed[v])
                continue;
            processed[v] = true;
            for (Pair<Integer, Integer> p : g.get(v)) {
                int u = p.getKey();
                int w = p.getValue();
                if (dist[u] > dist[v] + w) {
                    dist[u] = dist[v] + w;
                }
                if (!processed[u]) {
                    q.add(new Pair<>(u, (int) dist[u]));
                }
            }
        }
        for (int i = 1; i < dist.length; ++i) {
            if (i == st)
                continue;
            if (dist[i] == Integer.MAX_VALUE)
                System.out.print("-1 ");
            else
                System.out.print(dist[i] + " ");
        }
        System.out.println();
    }

    static void union(int[] leaders, int a, int b) {
        int l1 = findLeader(leaders, a);
        int l2 = findLeader(leaders, b);
        leaders[l1] = l2;
        leaders[l1] = l1;
    }

    static void makeLeader(int[] leaders, int s) {
        int l = findLeader(leaders, s);
        leaders[s] = s;
        leaders[l] = s;
    }

    static int findLeader(int[] leaders, int s) {
        while (leaders[s] != s) {
            leaders[s] = leaders[leaders[s]];
            s = leaders[s];
        }
        return s;
    }

    static class Vertex {
        int id;
        int v;

        public Vertex(int id, int v) {
            this.id = id;
            this.v = v;
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Vertex vertex = (Vertex) o;

            return id == vertex.id;

        }

        @Override public int hashCode() {
            return id;
        }

    }

    public static void gen(String[] arr) {
        Map<String, Integer> m = new HashMap<>();
        int len = arr.length;
        for (int i = 0; i < len; ++i) {
            String v1 = arr[i];
            for (int j = 0; j < len; ++j) {
                if (i == j)
                    continue;
                String v2 = arr[j];
                if (!m.containsKey(v1 + v2)) {
                    m.put(v1 + v2, 0);
                }
                m.put(v1 + v2, m.get(v1 + v2) + 1);
            }
        }
        int max = -1;
        String res = "";
        for (String key : m.keySet()) {
            if (m.get(key) > max) {
                max = m.get(key);
                res = key;
            }
        }
        System.out.println(res);
    }

    static long countCoprimes(String n1, String n2) {
        return countCoprimes(n2.toCharArray(), n2.length() - 1, true, dp, 0, 0) -
            countCoprimes(n1.toCharArray(), n1.length() - 1, true, dp, 0, 0);
    }

    static int countCoprimes(char[] number, int pos, boolean isLast, int[][][] dp, int sum, int squareSum) {
        if (pos == -1)
            return gcd(sum, squareSum) <= 1 ? 1 : 0;
        if (!isLast && dp[pos][sum][squareSum] != -1) {
            return dp[pos][sum][squareSum];
        }
        int to;
        if (isLast) {
            to = number[pos] - '0';
        } else {
            to = 9;
        }
        System.out.println(to);
        int res = 0;
        for (int i = 0; i <= to; ++i) {
            res += countCoprimes(number, pos - 1, isLast && (i == to), dp, sum + i, squareSum + i * i);
        }
        if (!isLast)
            dp[pos][sum][squareSum] = res;
        return res;
    }

    static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    static long build(int n, long[] dp) {
        if (dp[n] != 0)
            return dp[n];
        long t = (n + build(n - 1, dp) % mod) % mod;
        dp[n] = t;
        return t;
    }

    static void maxPaths(int[][] m, int rows, int cols) {
        for (int i = 0; i < rows; ++i) {
            m[i][0] = 1;
        }
        for (int j = 0; j < cols; ++j) {
            m[0][j] = 1;
        }
        for (int i = 1; i < rows; ++i) {
            for (int j = 1; j < cols; ++j) {
                m[i][j] = m[i - 1][j] + m[i][j - 1];
            }
        }
    }

    static int maxScore(int[] coins, int player, int sc1, int sc2, int l, int r, int moves, int[][][] dp) {
        if (moves >= coins.length) {
            if (player == 1)
                return sc1;
            else
                return sc2;
        }
        if (player == 0) {
            if (dp[l][r][0] != 0)
                return dp[l][r][0];
            int v1 = maxScore(coins, 1, sc1 + coins[l], sc2, l + 1, r, moves + 1, dp);
            int v2 = maxScore(coins, 1, sc1 + coins[r], sc2, l, r - 1, moves + 1, dp);
            dp[l][r][0] = Math.max(v1, v2);
            return Math.max(v1, v2);
        } else {
            if (dp[l][r][1] != 0)
                return dp[l][r][1];
            int v1 = maxScore(coins, 0, sc1, sc2 + coins[l], l + 1, r, moves + 1, dp);
            int v2 = maxScore(coins, 0, sc1, sc2 + coins[r], l, r - 1, moves + 1, dp);
            dp[l][r][1] = Math.max(v1, v2);
            return Math.max(v1, v2);
        }
    }

    static double maxScore(int x, int y, double p1, double p2,
        int w, int nx, int ny, int sc, double cp, double[][] dp) {
        if (dp[nx][ny] != 0) {
            return dp[nx][ny];
        }
        if (sc >= w) {
            return cp;
        } else if (nx == 0) {
            double s = maxScore(x, y, p1, p2, w, nx, ny - 1, sc + y, cp * p2, dp);
            dp[0][ny] = s;
            return s;
        } else if (ny == 0) {
            double s = maxScore(x, y, p1, p2, w, nx, ny - 1, sc + x, cp * p1, dp);
            dp[nx][0] = s;
            return s;
        } else {
            double m1 = maxScore(x, y, p1, p2, w, nx - 1, ny, sc + x, cp * p1, dp);
            double m2 = maxScore(x, y, p1, p2, w, nx, ny - 1, sc + y, cp * p2, dp);
            dp[nx][ny] = Math.max(m1, m2);
            return dp[nx][ny];
        }
    }

    static double maxScoreDyn(int x, int y, double p1, double p2, int w, int n) {

        return 0.0;
    }
}
