import rx.Observable;
import rx.Subscriber;

import java.io.ObjectInput;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Worksheet1 {

    static int N = 500002;
    static int inf = 1000000000;
    static int n, K, s[], SUM, M;
    static boolean f[];

    public static void main(String args[]) throws Exception {
                Scanner s = new Scanner(System.in);
                int n = s.nextInt();
                int[][] m = new int[n][n];
                for(int i=0;i<n;++i){
                    for(int j=0;j<n;++j){
                        m[i][j]=s.nextInt();
                    }
                }
//        int n = 2;
//        int[][] m = { { 1, 2 }, { 1, 3 } };
        int[][] dp = new int[n][n];
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                ans += find(m, dp, i, j, n, i, j);
            }
        }
        System.out.println(ans);
    }

    static int find(int[][] m, int[][] dp, int ci, int cj, int n, int pi, int pj) {
        if (ci >= n || cj >= n || ci < 0 || cj < 0)
            return 0;
        if (m[pi][pj] <= m[ci][cj] && !(pi == ci && pj == cj))
            return 0;
        if (dp[ci][cj] != 0)
            return dp[ci][cj];
        int ans = 0;
        ans += find(m, dp, ci + 1, cj, n, ci, cj);
        ans += find(m, dp, ci - 1, cj, n, ci, cj);
        ans += find(m, dp, ci, cj - 1, n, ci, cj);
        ans += find(m, dp, ci, cj + 1, n, ci, cj);
        dp[ci][cj] = ans + 1;
        return ans + 1;
    }

    static boolean farmingResources(int[] ft, int[] et, int[] lc, int[][] ic) {
        int[][] field = new int[1000][1000];
        int add = 100;
        int fj = ft[0] + add;
        int fi = ft[1] + add;
        int ftm = ft[2];

        int ej = et[0] + add;
        int ei = et[1] + add;
        int etm = et[2];

        for (int i = 0; i < ic.length; ++i) {
            field[ic[i][1] + add][ic[i][0] + add] = 1;
        }
        int mt = bfs(fi, fj, field, lc[1] + add, lc[0] + add);
        int me = bfs(ei, ej, field, lc[1] + add, lc[0] + add);
        System.out.println(mt);
        System.out.println(me);
        return mt * ftm < me * etm;
    }

    static int bfs(int si, int sj, int[][] field, int ei, int ej) {
        int nmoves = 0;
        Queue<P> q = new ArrayDeque<>();
        q.add(new P(si, sj, 0));
        boolean[][] visit = new boolean[300][300];
        while (!q.isEmpty()) {
            P p = q.poll();
            if (p.i <= 0 || p.j <= 0)
                continue;
            if (p.i >= 200 || p.j >= 200)
                continue;
            if (p.i == ei && p.j == ej)
                return p.n;
            if (visit[p.i][p.j] == true)
                continue;
            visit[p.i][p.j] = true;
            int[][] m = getMoves(p.i, p.j, field);
            for (int i = 0; i < 6; ++i) {
                int ni = m[i][0], nj = m[i][1];
                if (field[ni][nj] == 1)
                    continue;
                q.add(new P(ni, nj, p.n + 1));
            }

        }
        return 0;
    }

    static int[][] getMoves(int i, int j, int[][] field) {
        int[][] moves = new int[6][2];
        moves[0] = new int[] { i, j - 1 };
        moves[1] = new int[] { i + 1, j - 1 };
        moves[2] = new int[] { i + 1, j };
        moves[3] = new int[] { i, j + 1 };
        moves[4] = new int[] { i - 1, j + 1 };
        moves[5] = new int[] { i - 1, j };
        return moves;
    }

    private static class P {
        int i;
        int j;
        int n;

        public P(int i, int j, int n) {
            this.i = i;
            this.j = j;
            this.n = n;
        }
    }

    static int compute(int n) {
        int l = 1, r = n;
        while (l < r) {
            int mid = (l + r) / 2;
            if (mid * mid > n) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
            if (mid * mid == n)
                return mid;
        }
        return l;
    }

    static Map<String, List<Pair>> fill(String[] c) {
        Map<String, List<Pair>> mp = new HashMap<>();
        for (int i = 0; i < c.length; ++i) {
            String[] words = c[i].trim().split("[ \t]+");
            for (int j = 0; j < words.length; ++j) {
                String word = words[j];
                if (word.startsWith("(")) {
                    word = word.substring(1);
                } else if (word.contains("(")) {
                    String[] nw = word.split("\\(");
                    word = nw[1];
                } else if (word.endsWith(")")) {
                    word = word.substring(0, word.length() - 1);
                } else if (word.contains(")")) {
                    String[] nw = word.split("\\)");
                    word = nw[0];
                }
                if (word.endsWith(",")) {
                    word = word.substring(0, word.length() - 1);
                }
                if (word.startsWith("'")) {
                    word = word.substring(1);
                }
                if (word.endsWith("'")) {
                    word = word.substring(0, word.length() - 1);
                }
                if (!mp.containsKey(word))
                    mp.put(word, new LinkedList<Pair>());
                mp.get(word).add(new Pair(i, j));
            }
        }
        return mp;
    }

    static String[] taskMaker(String[] s, int c) {
        String m = "//DB";
        int ind = -1, to = -1;
        for (int i = 0; i < s.length - 1; ++i) {
            if (s[i + 1].trim().startsWith(m)) {
                int j = i + 1;
                while (j < s.length) {
                    if (!s[j].trim().startsWith(m))
                        break;
                    String[] v = s[j].trim().substring(5).split("//");
                    int id = Integer.valueOf(v[0]);
                    if (c == id) {
                        ind = i;
                        to = j;
                    }
                    j++;
                }
            }
            if (ind != -1)
                break;
        }

        List<String> lst = new LinkedList<>();
        for (int i = 0; i < s.length; ++i) {
            if (i == ind) {
                String[] z = s[to].split("//");
                System.out.println(z[0]);
                lst.add(z[0] + z[2]);
                continue;
            }
            if (s[i].trim().startsWith(m))
                continue;
            lst.add(s[i]);
        }
        return lst.toArray(new String[] {});
    }

    static void solve(int n, int m) {
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 0; i <= n; ++i) {
            dp[i][0] = 1;
            dp[0][i] = 0;
        }
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (i < j * m)
                    continue;
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        for (int i = 1; i <= n; ++i) {
            System.out.println(Arrays.toString(dp[i]));
        }
        for (int i = 0; i <= n && n - i >= i * m; i++) {
            if (n - i > i) {
                System.out.println((n - i) + "  " + i);
                System.out.println(dp[n - i][i]);
            }
        }
    }

    static int solve(int[] arr, int k) {
        int n = arr.length;
        int[] left = new int[n];
        int[] right = new int[n];
        for (int i = 0; i < n; ++i) {
            left[i] = -1;
            right[i] = -1;
        }
        for (int i = 1; i < n; ++i) {
            if (arr[i - 1] < arr[i]) {
                left[i] = i - 1;
            } else {
                int j = i - 1;
                while (j != -1 && arr[j] > arr[i]) {
                    j = left[j];
                }
                left[i] = j;
            }
        }
        // System.out.println(Arrays.toString(left));
        for (int i = n - 2; i >= 0; --i) {
            if (arr[i + 1] < arr[i]) {
                right[i] = i + 1;
            } else {
                int j = i + 1;
                while (j != -1 && arr[j] > arr[i]) {
                    j = right[j];
                }
                right[i] = j;
            }
        }
        //System.out.println(Arrays.toString(right));
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            int leftDist = 10050000, leftInd = 0, rightDist = 10050000, rightInd = 0;
            if (i - k >= 0) {
                int j = i - k;
                while (j != -1 && arr[j] > arr[i]) {
                    j = left[j];
                }
                if (j != -1) {
                    leftInd = j;
                    leftDist = i - j;
                }
            }
            if (i + k < n) {
                int j = i + k;
                while (j != -1 && arr[j] > arr[i]) {
                    j = right[j];
                }
                if (j != -1) {
                    rightInd = j;
                    rightDist = j - i;
                }
            }
            //System.out.println(arr[i] + "  " + i + "  " + leftInd + "  " + leftDist + "  " + rightInd + "  " + rightDist);
            if (leftDist == 10050000 && rightDist == 10050000) {
                continue;
            }
            ans += Math.min(rightDist, leftDist);
        }
        // System.out.println();
        return ans;
    }

    static double calc(long x1, long y1, long x2, long y2) {
        return Math.sqrt((1.00D * x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    static int solve(int no, int nz, int[] ao, int[] az, int pos, int bitmask) {
        if (no <= 0 || nz <= 0 || pos >= ao.length)
            return 0;
        int mw = 0;
        for (int i = 0; i < ao.length; ++i) {
            if ((bitmask & (1 << i)) != 0) {
                continue;
            }
            bitmask = bitmask | (1 << i);
            int curr = 1 + solve(no - ao[i], nz - az[i], ao, az, pos + 1, bitmask);
            mw = Math.max(mw, curr);
            bitmask = bitmask ^ (1 << i);
        }
        return mw;
    }

    static double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    static long dfs(Map<Integer, List<Map.Entry<Integer, Integer>>> g, boolean[] visited, int v) {
        if (visited[v])
            return 0;
        visited[v] = true;
        long maxTime = 0;
        if (g.get(v) == null)
            return 2;
        for (int i = 0; i < g.get(v).size(); ++i) {
            Map.Entry<Integer, Integer> p = g.get(v).get(i);
            int u = p.getKey();
            int tm = p.getValue();
            if (!visited[u]) {
                long ct = dfs(g, visited, u);
                maxTime = Math.max(ct + tm, maxTime);
            }
        }
        return maxTime + 2;
    }

    static int min(int[] arr, int[] p, int pos, int[] curr, int mn, boolean[] busy) {
        if (pos == arr.length)
            return mn;
        int minv = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; ++i) {
            if (busy[arr[i]])
                continue;
            busy[arr[i]] = true;
            int temp = pos < 2 ? 0 : p[pos] * (curr[pos] ^ curr[pos - 1] ^ curr[pos - 2]);
            int currMn = min(arr, p, pos + 1, curr, mn + temp, busy);
            minv = Math.min(currMn, minv);
            busy[arr[i]] = false;
        }
        return minv;
    }

    static int dfs(int[][] g, boolean[] visited, int v, int[] mpath) {
        if (visited[v])
            return 0;
        visited[v] = true;
        int l = 0;
        for (int i = 1; i < g[v].length; ++i) {
            if (g[v][i] == 1 && !visited[i]) {
                l += dfs(g, visited, i, mpath);
            }
        }
        mpath[v] = l + 1;
        return l + 1;
    }

    static void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int mid = l + (r - l) / 2;
            mergeSort(arr, l, mid);
            mergeSort(arr, mid + 1, r);
            mergeFunc(arr, l, mid, r);
        }
    }

    static int[] freq = new int[10000000];

    static void mergeFunc(int[] arr, int l, int mid, int r) {
        int[] aux = new int[r - l + 1];
        int lInd = l, rInd = mid + 1, ind = 0;
        int ninv = 0;
        while (lInd <= mid && rInd <= r) {
            if (arr[lInd] > arr[rInd]) {
                ninv++;
                aux[ind++] = arr[rInd++];
            } else {
                freq[arr[lInd]] += ninv;
                aux[ind++] = arr[lInd++];
            }
        }
        for (int i = lInd; i <= mid; ++i) {
            freq[arr[i]] += ninv;
            aux[ind++] = arr[i];
        }
        for (int i = rInd; i <= r; ++i)
            aux[ind++] = arr[i];
        System.arraycopy(aux, 0, arr, l, r - l + 1);
    }

    static int go(int x, int from, Map<Integer, ArrayList<Map.Entry<Integer, Integer>>> g) {
        int D1 = -inf;
        int D2 = -inf;

        for (int i = 0; i < g.get(x).size(); i++) {
            int v = g.get(x).get(i).getKey();
            int d = g.get(x).get(i).getValue();
            if (v != from) {
                D1 = Math.max(D1, go(v, x, g) + d);
                if (D1 > D2) {
                    int t = D1;
                    D1 = D2;
                    D2 = t;
                }
                s[x] += s[v];
                System.out.println(x + "  " + v + "  " + s[v]);
                if (0 < s[v] && s[v] < K) {
                    SUM += d;
                }
            }
        }
        if (D1 > 0)
            M = Math.max(M, D1 + D2);
        if (D2 > 0 && f[x])
            M = Math.max(M, D2);
        if (f[x])
            D2 = Math.max(D2, 0);
        return D2;
    }

    // Given a stream of unsorted integers, find the median element in sorted order at any given time.
    // http://www.ardendertat.com/2011/11/03/programming-interview-questions-13-median-of-integer-stream/
    public static class MedianOfIntegerStream {

        public Queue<Integer> minHeap;
        public Queue<Integer> maxHeap;
        public int numOfElements;

        public MedianOfIntegerStream() {
            minHeap = new PriorityQueue<Integer>();
            maxHeap = new PriorityQueue<Integer>(10, new MaxHeapComparator());
            numOfElements = 0;
        }

        public void addNumberToStream(Integer num) {
            maxHeap.add(num);
            if (numOfElements % 2 == 0) {
                if (minHeap.isEmpty()) {
                    numOfElements++;
                    return;
                } else if (maxHeap.peek() > minHeap.peek()) {
                    Integer maxHeapRoot = maxHeap.poll();
                    Integer minHeapRoot = minHeap.poll();
                    maxHeap.add(minHeapRoot);
                    minHeap.add(maxHeapRoot);
                }
            } else {
                minHeap.add(maxHeap.poll());
            }
            numOfElements++;
        }

        public double getMedian() {
            if (numOfElements % 2 != 0)
                return maxHeap.peek();
            else
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }

        private class MaxHeapComparator implements Comparator<Integer> {
            @Override public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        }
    }

    private static class Pair {
        int r;
        int c;

        public Pair(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Pair pair = (Pair) o;

            if (r != pair.r)
                return false;
            if (c != pair.c)
                return false;

            return true;
        }

        @Override public int hashCode() {
            int result = r;
            result = 31 * result + c;
            return result;
        }
    }

}