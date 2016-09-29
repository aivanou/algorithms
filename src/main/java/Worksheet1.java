import java.awt.*;
import java.io.ObjectInput;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Worksheet1 {

    /**
     * static double find(int d, int p){
     * double p2 = 1D*p/100;
     * double p3 = 1D*(1-p)/100;
     * double[] probs = new double[d+1];
     * probs[2]= p2;
     * probs[3]=p3;
     * for(int i=4;i<=d;++i){
     * probs[i]=probs[i-2]*p2+probs[i-3]*p3;
     * }
     * return probs[d];
     * }
     */

    static int nextGreater(int[] arr) {
        Stack<Integer> st = new Stack<>();
        int len = arr.length - 1;
        st.push(arr[len]);
        System.out.println("-1");
        for (int i = len - 1; i >= 0; --i) {
            if (st.peek() > arr[i]) {
                System.out.println(st.peek());
                st.push(arr[i]);
            } else {
                while (!st.isEmpty() && st.peek() <= arr[i]) {
                    st.pop();
                }
                if (st.isEmpty()) {
                    System.out.println("-1");
                } else {
                    System.out.println(st.peek());
                }
                st.push(arr[i]);
            }
        }
        return 1;
    }

    static int frow(int[][] arr) {
        int n = 0;
        for (int i = 0; i < arr.length; ++i)
            if (arr[0][i] == 1) {
                n = i;
                break;
            }
        if (n == 0)
            return 0;
        int r = 1;
        int mRow = 0;
        while (r != arr.length - 1) {
            if (n == 0) {
                return mRow;
            }
            if (arr[r][n] == 1) {
                for (int i = n - 1; i >= 0; --i) {
                    if (arr[r][i] == 0) {
                        n = i + 1;
                        mRow = r;
                        break;
                    }
                }
            }
            r++;
        }
        return mRow;
    }

    public static int mult(int a, int b) {
        if (b == 1)
            return a;
        if (b % 2 == 0) {
            return mult(a + a, b / 2);
        }
        return a + mult(a, b - 1);
    }

    public static boolean isPal(int n) {
        List<Integer> lst = new LinkedList<>();
        int c = n;
        while (c != 0) {
            lst.add(c % 10);
            c /= 10;
        }
        for (int i = 0; i < lst.size() / 2; ++i) {
            if (lst.get(i) != lst.get(lst.size() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    static boolean sumsUp(int[] arr, int sum) {
        boolean[][] dp = new boolean[arr.length + 1][sum + 1];
        for (int i = 0; i < arr.length; ++i) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= arr.length; ++i) {
            for (int j = 1; j <= sum; ++j) {
                dp[i][j] = dp[i - 1][j - arr[i]];
            }
        }
        return dp[arr.length][sum];
    }

    static int mod = 1000000007;

    static void npasses(int[][] b, int[][] s, int n, int m, long st) {
        long[][][] dp = new long[m + 1][n + 2][2];
        for (int i = 1; i <= n; ++i) {
            dp[0][i][0] = st;
        }
        for (int time = 1; time <= m; ++time) {
            for (int i = 1; i <= n; ++i) {
                long c1 = dp[time - 1][i][0];
                long c2 = dp[time - 1][i][1];
                for (int dist = 0; dist <= n; ++dist) {
                    if (i + dist <= n) {
                        c1 = Math.max(c1, dp[time - dist][i + dist][0]);
                        c2 = Math.max(c2, dp[time - dist][i + dist][1]);
                    }
                    if (i - dist >= 1) {
                        c1 = Math.max(c1, dp[time - dist][i - dist][0]);
                        c2 = Math.max(c2, dp[time - dist][i - dist][1]);
                    }
                    c1 = Math.max(c1, dp[time - 1][i][1] * b[i][time - 1]);
                    c2 = Math.max(c2, dp[time - 1][i][0] * s[i][time - 1]);
                    dp[time][i][0] = c1;
                    dp[time][i][1] = c2;
                }
            }
        }
        long max = 0;
        for (int i = 1; i <= n; ++i) {
            max = Math.max(max, dp[m][i][0]);
        }
        System.out.println(max);
    }

    static void cakePortfolio(char[] s1, char[] s2, int d) {
        int n = s1.length;
        char[][] t1 = new char[d][26];
        char[][] t2 = new char[d][26];
        for (int i = 0; i < d; ++i) {
            int ind = i;
            while (ind < n) {
                t1[i][s1[ind] - 'a']++;
                t2[i][s2[ind] - 'a']++;
                ind += d;
            }
        }
        for (int i = 0; i < d; ++i) {
            for (int j = 0; j < 26; ++j) {
                if (t1[i][j] != t2[i][j]) {
                    System.out.printf("false");
                    return;
                }
            }
        }
        System.out.printf("true");
    }

    static void count(long max) {
        long n1 = 1L, n2 = 2L;
        long ind = 2;
        while (n2 <= max) {
            long temp = n1 + n2;
            n1 = n2;
            n2 = temp;
            ind++;
        }
        System.out.println(ind);
    }

    public static int[] findProds(int[] arr) {
        int[] p = new int[arr.length];
        int[] ans = new int[arr.length];
        p[arr.length - 1] = arr[arr.length - 1];
        for (int i = arr.length - 2; i >= 0; --i) {
            p[i] = arr[i] * p[i + 1];
        }
        ans[0] = p[1];
        int currProd = arr[0];
        for (int i = 1; i < arr.length - 1; ++i) {
            ans[i] = currProd * p[i + 1];
            currProd *= arr[i];
        }
        ans[arr.length - 1] = currProd;
        return ans;
    }

    public static String longestPalindrome(String s) {
        if (s.length() == 1)
            return s;
        char[] str = s.toCharArray();
        int n = str.length;
        boolean[][] tb = new boolean[n][n];
        for (int i = 0; i < n; ++i)
            tb[i][i] = true;
        int len = 1, sInd = 0;
        for (int i = 0; i < n - 1; ++i) {
            if (str[i] == str[i + 1]) {
                len = 2;
                sInd = i;
                tb[i][i + 1] = true;
            }
        }
        for (int clen = 3; clen <= n; ++clen) {
            for (int start = 0; start < n - clen + 1; ++start) {
                int end = start + clen - 1;
                if (str[start] == str[end] && tb[start + 1][end - 1]) {
                    tb[start][end] = true;
                    if (clen > len) {
                        len = clen;
                        sInd = start;
                    }
                }
            }
        }
        return s.substring(sInd, sInd + len);
    }

    public static String shortestPalindrome(String s) {
        if (s == null || s.length() <= 1)
            return s;

        String result = null;

        int len = s.length();
        int mid = len / 2;

        for (int i = mid; i >= 1; i--) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                if ((result = scanFromCenter(s, i - 1, i)) != null)
                    return result;
            } else {
                if ((result = scanFromCenter(s, i - 1, i - 1)) != null)
                    return result;
            }
        }

        return result;
    }

    private static String scanFromCenter(String s, int l, int r) {
        int i = 1;

        //scan from center to both sides
        for (; l - i >= 0; i++) {
            if (s.charAt(l - i) != s.charAt(r + i))
                break;
        }

        //if not end at the beginning of s, return null
        if (l - i >= 0)
            return null;

        StringBuilder sb = new StringBuilder(s.substring(r + i));
        sb.reverse();

        return sb.append(s).toString();
    }

    public static void permutation(String str) {
        permutation("", str);
    }

    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0)
            System.out.println(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
        }
    }

    static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    static void reverse(int[] nums, int l, int r) {
        for (int i = l, j = r - 1; i < j; ++i, --j) {
            swap(nums, i, j);
        }
    }

    static public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new LinkedList<>();
        build(ans, nums, new LinkedList<Integer>());
        return ans;
    }

    static void build(List<List<Integer>> perms, int[] nums, List<Integer> perm) {
        if (perm.size() == nums.length) {
            perms.add(new ArrayList<Integer>(perm));
            return;
        }
        Stack<Integer> s = new Stack<>();
        new ArrayList<Integer>(s);
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            if (nums[i] == -100500)
                continue;
            int num = nums[i];
            nums[i] = -100500;
            perm.add(num);
            build(perms, nums, perm);
            perm.remove((Integer) num);
            nums[i] = num;
        }
    }

    static List<List<Integer>> bperms(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        for (int i = 0; i < nums.length; ++i) {
            List<List<Integer>> next = new ArrayList<>();
            for (List<Integer> r : res) {
                for (int j = 0; j < r.size() + 1; ++j) {
                    r.add(j, nums[i]);
                    List<Integer> temp = new ArrayList<>(r);
                    next.add(temp);
                    r.remove(j);
                }
            }
            res = next;
        }
        return res;
    }

    static boolean hasDubls(int[] arr, int k) {
        Set<Integer> s1 = new HashSet<>();
        for (int i = 0; i < k; ++i) {
            if (s1.contains(arr[i]))
                return true;
            s1.add(arr[i]);
        }
        for (int i = k; i < arr.length; ++i) {
            if (s1.contains(arr[i]))
                return true;
            s1.remove(arr[i - k]);
            s1.add(arr[i]);
        }
        return false;
    }

    static void buildPath(String[] f, String[] t) {
        int n = f.length;
        Map<String, String> m = new HashMap<>();
        Map<String, String> revm = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            m.put(f[i], t[i]);
            revm.put(t[i], f[i]);
        }
        Deque<String> path = new ArrayDeque<>();
        String st1 = f[0];
        path.add(st1);
        while (true) {
            if (!revm.containsKey(st1)) {
                break;
            }
            st1 = revm.get(st1);
            path.addFirst(st1);
            if (!m.containsKey(st1)) {
                break;
            }
            st1 = m.get(st1);
            path.addFirst(st1);
            //            System.out.println(path);
        }
        st1 = t[0];
        //        while (true) {
        //            if (!m.containsKey(st1)) {
        //                break;
        //            }
        //            st1 = m.get(st1);
        //            path.addLast(st1);
        //            if (!revm.containsKey(st1)) {
        //                break;
        //            }
        //            st1 = revm.get(st1);
        //            path.addLast(st1);
        //        }
        System.out.println(path);
    }

    public static void dc(String input) {
        List<String> expr = new LinkedList<>();
        int st = 0;
        int numOps = 0;
        for (int i = 0; i < input.length(); ++i) {
            if (isOp(input.charAt(i) + "")) {
                String number = input.substring(st, i);
                expr.add(number);
                expr.add(input.charAt(i) + "");
                st = i + 1;
                numOps++;
            }
        }
        expr.add(input.substring(st));
        print(expr, numOps);
    }

    static void print(List<String> expr, int cNum) {
        if (cNum == 0) {
            System.out.println(expr.get(0));
            return;
        }
        for (int i = 0; i < expr.size(); ++i) {
            String e = expr.get(i);
            if (isOp(e)) {
                List<String> newExpr = concat(expr, i);
                print(newExpr, cNum - 1);
            }
        }
    }

    static boolean isOp(String e) {
        return e.equals("+") || e.equals("-") || e.equals("*");
    }

    static List<String> concat(List<String> lst, int ind) {
        List<String> newLst = new ArrayList<>(lst.size() - 2);
        for (int i = 0; i < lst.size(); ++i) {
            if (i + 1 == ind) {
                String newVal = "(" + lst.get(i) + lst.get(i + 1) + lst.get(i + 2) + ")";
                newLst.add(newVal);
                i += 2;
            } else {
                newLst.add(lst.get(i));
            }
        }
        return newLst;
    }

    static int findDuplicate(int[] nums) {
        int n = nums.length - 1, res = 0;
        for (int p = 0; p < 32; ++p) {
            int bit = (1 << p), a = 0, b = 0;
            for (int i = 0; i <= n; ++i) {
                if (i > 0 && (i & bit) > 0)
                    ++a;
                if ((nums[i] & bit) > 0)
                    ++b;
            }
            if (b > a)
                res += bit;
        }
        return res;
    }

    public static int search(int[] nums, int target) {
        if (nums[0] == target)
            return 0;
        if (nums[nums.length - 1] == target)
            return nums.length - 1;
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == target)
                return mid;
            if (nums[l] < nums[r]) {
                if (nums[mid] > target)
                    r = mid - 1;
                else
                    l = mid + 1;
            } else {
                if (nums[mid] >= nums[l]) {
                    if (nums[l] > target || nums[mid] < target) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                } else {
                    if (nums[mid] > target || nums[r] < target) {
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                }
            }
        }
        return -1;
    }

    static char[][] zeroXMatrix(int n, int m) {
        char[][] a = new char[n][m];
        for (int i = 0; i < Math.min(n / 2, m / 2); ++i) {
            if (i % 2 == 0) {
                fill(a, i, 'X', n, m);
            } else {
                fill(a, i, 'O', n, m);
            }
        }
        if (Math.min(n, m) % 2 != 0) {
            fill(a, Math.min(n, m) / 2, 'X', n, m);
        }
        return a;
    }

    static void fill(char[][] a, int i, char ch, int n, int m) {
        for (int j = i; j < n - i; ++j) {
            a[j][i] = ch;
            a[j][m - 1 - i] = ch;
        }
        for (int j = i; j < m - i; ++j) {
            a[i][j] = ch;
            a[n - 1 - i][j] = ch;
        }
    }

    static void printInc(int[][] a, int m, int n) {
        int[] col = new int[n];
        for (int i = 0; i < col.length; ++i)
            col[i] = -1;
        PriorityQueue<Integer> q = new PriorityQueue<>(n + 2, new Comparator<Integer>() {
            @Override public int compare(Integer i1, Integer i2) {
                if (col[i1] == -1 && col[i2] == -1)
                    return 0;
                else if (col[i1] == -1)
                    return 1;
                else if (col[i2] == -1)
                    return -1;
                return Integer.compare(a[i1][col[i1]], a[i2][col[i2]]);
            }
        });
        col[0] = 0;
        q.add(0);
        while (!q.isEmpty()) {
            int row = q.poll();
            if (row != col.length - 1 && col[row + 1] == -1) {
                col[row + 1] = 0;
                q.add(row + 1);
            }
            System.out.print(a[row][col[row]] + " ");
            if (col[row] != m - 1) {
                col[row] += 1;
                q.add(row);
            }
        }
        System.out.println();
    }

    static void getPowerOfSet(int[] set, Stack<Integer> subset, List<List<Integer>> res, int currInd) {
        if (set.length == subset.size()) {
            res.add(new ArrayList<>(subset));
            return;
        }
        if (!subset.isEmpty())
            res.add(new ArrayList<>(subset));
        for (int i = currInd; i < set.length; ++i) {
            int el = set[i];
            subset.push(el);
            getPowerOfSet(set, subset, res, i + 1);
            subset.pop();
        }

    }

    public static int findOccurs(int[] arr, int l, int r, int num) {
        if (l > r)
            return 0;
        else if (l == r) {
            return arr[l] == num ? 1 : 0;
        }
        int mid = l + (r - l) / 2;
        if (arr[mid] == num) {
            return findOccurs(arr, l, mid - 1, num) + findOccurs(arr, mid + 1, r, num) + 1;
        } else if (arr[mid] > num) {
            return findOccurs(arr, l, mid - 1, num);
        } else {
            return findOccurs(arr, mid + 1, r, num);
        }
    }

    public static void findCombs(char[] arr, Stack<Integer> positions, List<String> res) {
        if (positions.isEmpty()) {
            res.add(new String(arr));
            return;
        }
        int pos = positions.pop();
        arr[pos] = '1';
        findCombs(arr, positions, res);
        arr[pos] = '0';
        findCombs(arr, positions, res);
        positions.push(pos);
    }

    static long smallestNumber(String str, int n) {
        if (str.length() == n)
            return 0;
        boolean[] removed = new boolean[str.length()];
        char[] s = str.toCharArray();
        for (int i = 0; i < str.length() - 1; ++i) {
            if (s[i] > s[i + 1]) {
                removed[i] = true;
                n--;
            }
            if (n == 0) {
                return buildNumber(s, removed);
            }
        }
        for (int i = s.length - 1; i >= 0; --i) {
            if (!removed[i]) {
                removed[i] = true;
                n--;
            }
            if (n == 0)
                return buildNumber(s, removed);
        }
        return 0;
    }

    static long buildNumber(char[] str, boolean[] removed) {
        StringBuilder bldr = new StringBuilder();
        for (int i = 0; i < str.length; ++i) {
            if (!removed[i])
                bldr.append(str[i]);
        }
        return Long.parseLong(bldr.toString());
    }

    static char[] reverse(char[] str) {
        int st = 0;
        for (int i = 0; i < str.length; ++i) {
            if (str[i] == ' ') {
                reverse(str, st, i - 1);
                st = i;
            }
            while (str[i] == ' ') {
                i++;
                st++;
            }
        }
        if (st != str.length - 1) {
            reverse(str, st, str.length - 1);
        }
        return str;
    }

    static void reverse(char[] str, int st, int end) {
        while (st < end) {
            swap(str, st++, end--);
        }
    }

    static void swap(char[] str, int i1, int i2) {
        str[i1] = (char) (str[i1] ^ str[i2]);
        str[i2] = (char) (str[i1] ^ str[i2]);
        str[i1] = (char) (str[i1] ^ str[i2]);
    }

    static String getLargestPalindrome(String str) {
        int mlen = 1;
        int mind = 0;
        for (int i = 1; i < str.length() - 1; ++i) {
            int clen = findPal(str, i, i + 1);
            if (clen > mlen) {
                mind = i;
                mlen = clen;
            }
            clen = findPal(str, i - 1, i + 1) + 1;
            if (clen > mlen) {
                mind = i;
                mlen = clen;
            }
        }
        if (mlen % 2 == 1) {
            System.out.println(str.substring(mind - mlen / 2, mind + mlen / 2 + 1));
        } else {
            mlen -= 1;
            System.out.println(str.substring(mind - mlen / 2, mind + mlen / 2 + 2));
        }
        return "";
    }

    static int findPal(String str, int c1, int c2) {
        int len = 0;
        while (c1 >= 0 && c2 <= str.length() - 1) {
            if (str.charAt(c1--) != str.charAt(c2++)) {
                return len;
            }
            len += 2;
        }
        return len;
    }

    static long findPos(long num) {
        long pos = 0;
        int nd = ndigs(num);
        long base = 9;
        long temp = 0;
        for (int i = 1; i < nd; ++i) {
            pos += base;
            temp += base;
            base *= 10;
        }
        num = num - temp - 1;
        pos += nd * num;
        return pos;
    }

    static int ndigs(long n) {
        if (n == 0)
            return 0;
        return 1 + ndigs(n / 10);
    }

    static List<String> merge(String a, String b) {
        List<String> res = new LinkedList<>();
        merge(a.toCharArray(), b.toCharArray(), 0, 0, new Stack<>(), res);
        return res;
    }

    static void merge(char[] a, char[] b, int ai, int bi, Stack<Character> str, List<String> res) {
        if (str.size() == a.length + b.length) {
            System.out.println(str);
            res.add(toString(str));
            return;
        }
        for (int i = ai; i < a.length; ++i) {
            str.push(a[i]);
            merge(a, b, i + 1, bi, str, res);
            str.pop();
        }
        for (int i = bi; i < ai; ++i) {
            if (i >= b.length)
                break;
            str.push(b[i]);
            merge(a, b, ai, i + 1, str, res);
            str.pop();
        }
    }

    public static void printOddOccurringNumbers(int[] a) {
        int xor = 0;
        for (int i = 0; i < a.length; i++) {
            int shifted = 1 << a[i];
            xor ^= shifted;
        }
        for (int i = 0; i < a.length; i++) {
            int shifted = 1 << a[i];
            if ((shifted & xor) == shifted) {
                System.out.print(a[i] + " ");
                xor ^= shifted;
            }
        }
    }

    static String toString(Stack<Character> st) {
        StringBuilder b = new StringBuilder();
        for (char c : st)
            b.append(c);
        return b.toString();
    }

    static boolean ok(int[] cnt, int n) {
        for (int i = 0; i < 4; ++i)
            if (cnt[i] > n / 2)
                return false;
        return true;
    }

    static int f(char[] sl, char[] W, int i) {
        for (int j = 0; j < 4; ++j)
            if (sl[i] == W[j])
                return j;
        return 1;
    }

    public static void mint(char[] s) {
        int n = s.length;
        int[] cnt = new int[4];
        char[] w = "ACTG".toCharArray();
        for (int i = 0; i < n; ++i)
            ++cnt[f(s, w, i)];

        if (ok(cnt, n)) { // already steady
            return;
        }
        int ans = n;
        int j = 0;
        for (int i = 0; i < n; ++i) {
            while (j < n && !ok(cnt, n))
                --cnt[f(s, w, j++)];
            if (ok(cnt, n))
                ans = Math.min(ans, j - i);
            ++cnt[f(s, w, i)];
        }
        System.out.println(ans);
    }

    static void solve(char[] arr) {
        int[] freq = new int[4];
        char[] w = new char[256];
        w['A'] = 1;
        w['C'] = 0;
        w['T'] = 2;
        w['G'] = 3;
        for (char c : arr) {
            ++freq[w[c]];
        }
        if (good(freq, arr.length)) {
            System.out.println("0");
            return;
        }
        int j = 0;
        int ans = arr.length;
        for (int i = 0; i < arr.length; ++i) {
            while (j < arr.length && !good(freq, arr.length)) {
                --freq[w[arr[j++]]];
            }
            if (good(freq, arr.length)) {
                ans = Math.min(ans, j - i);
            }
            ++freq[w[arr[i]]];
        }
        System.out.println(ans);
    }

    static boolean good(int[] freq, int len) {
        for (int i = 0; i < freq.length; ++i) {
            if (freq[i] > len / 4)
                return false;
        }
        return true;
    }

    static boolean has(char[] s, int k) {
        int prime = 101;
        int base = 173, pow = 1;
        int num = 0;
        Map<Integer, Integer> mp = new HashMap<>();
        for (int i = 0; i < k - 1; ++i) {
            pow = (pow * base) % prime;
        }
        for (int i = 0; i < k; ++i) {
            num = (num * base + s[i]) % prime;
        }
        mp.put(num, 0);
        System.out.println(k + " " + num + "  " + pow);
        for (int i = k; i < s.length; ++i) {
            num = (base * (num + prime - s[i - k] * pow % prime) + s[i]) % prime;
            System.out.println(k + " " + num);
            if (mp.containsKey(num)) {
                int i1 = mp.get(num);
                int i2 = i - k + 1;
                System.out.println(" " + i1 + "  " + i2);
                boolean has = true;
                for (int j = 0; j < k; ++j) {
                    if (s[i1++] != s[i2++]) {
                        mp.put(num, i - k + 1);
                        has = false;
                        break;
                    }
                }
                if (has) {
                    return true;
                }
                continue;
            }
            mp.put(num, i - k + 1);
        }
        return false;
    }

    static int count = 0;

    public static void nperms(int[] ids, int cind) {
        if (ids.length <= cind) {
            System.out.println(Arrays.toString(ids));
            return;
        }
        for (int i = cind; i < ids.length; ++i) {
            swap(ids, i, cind);
            nperms(ids, cind + 1);
            swap(ids, i, cind);
        }
    }

    static List<HashMap<Character, Integer>> freq = new LinkedList<>();

    static void printPairs(int[] arr) {
        List<Stack<Integer>> pairs = new ArrayList();
        for (int i = 0; i < arr.length; ++i)
            pairs.add(new Stack<>());
        printPairs(arr, pairs, 0);
    }

    static void printPairs(int[] arr, List<Stack<Integer>> pairs, int ind) {
        if (ind == arr.length) {
            System.out.println(pairs);
            return;
        }
        for (int i = 0; i <= ind; ++i) {
            pairs.get(i).push(arr[ind]);
            printPairs(arr, pairs, ind + 1);
            pairs.get(i).pop();
        }
    }

    public static void print(String originalElements, int start, String stringTillNow) {
        if (start == originalElements.length()) {
            System.out.println(stringTillNow);
            return;
        }
        for (int i = start; i < originalElements.length(); i++) {
            print(originalElements, i + 1, stringTillNow + "(" + originalElements.substring(start, i + 1) + ")");
        }
    }

    static final long MOD = 1000000007L;

    static int nInc(int[] arr, int m) {
        int[][] dp = new int[m + 1][arr.length];
        for (int i = 0; i < arr.length; ++i) {
            dp[1][i] = 1;
        }
        for (int t = 2; t <= m; ++t) {
            for (int i = 0; i < arr.length; ++i) {
                int l = 0;
                for (int j = 0; j < i; ++j) {
                    if (arr[i] >= arr[j]) {
                        l += dp[t - 1][j];
                    }
                }
                dp[t][i] = l;
            }
        }
        int sum = 0;
        for (int i = 0; i < arr.length; ++i) {
            sum += dp[m][i];
        }
        return sum;
    }

    public static void main(String[] args) {
    }

    public static int maxRoute(int[][] arr, int sx, int sy, int ex, int ey) {
        Stack<java.awt.Point> st = new Stack<>();
        st.push(new java.awt.Point(sx, sy));
        boolean[][] visited = new boolean[arr.length][arr[0].length];
        int mroute = 0;
        int rows = arr.length, cols = arr[0].length;
        while (!st.isEmpty()) {
            Point curr = st.pop();
            visited[curr.x][curr.y] = true;
            int[] coords = getCoords(curr.x, curr.y);
            for (int i = 0; i < 4; ++i) {
                int newX = coords[i << 1];
                int newY = coords[(i << 1) | 1];
                if (newX < rows && newY < cols && newX >= 0 && newY >= 0 && !visited[newX][newY]) {
                }
            }
        }
        return mroute;
    }

    static int[] getCoords(int x, int y) {
        int[] c = new int[8];
        for (int i = 0; i < c.length; ++i)
            c[i] = 0;
        c[0] = x - 1;
        c[1] = y;
        c[2] = x;
        c[3] = y - 1;
        c[4] = x + 1;
        c[5] = y;
        c[6] = x;
        c[7] = y + 1;
        return c;
    }

    public static void modifyList(LNode head) {
        int len = 0;
        LNode curr = head;
        while (curr != null) {
            len++;
            curr = curr.next;
        }
        int sInd = len / 2;
        curr = head;
        while (sInd-- > 0) {
            curr = curr.next;
        }
        LNode prev = null;
        while (curr != null) {
            LNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        curr = prev;
        sInd = len / 2;
        LNode lowCurr = head;
        while (sInd-- > 0) {
            LNode lnext = lowCurr.next;
            LNode rnext = curr.next;
            lowCurr.next = curr;
            curr.next = lnext;
            curr = rnext;
            lowCurr = lnext;
        }
        lowCurr.next = null;
    }

    public static int maxLength(int[] arr, int k) {
        int ml = 0, csum = 0;
        int l = 0, ind = 0;
        while (ind < arr.length) {
            while (csum <= k) {
                csum += arr[ind++];
            }
            while (csum > k) {
                csum -= arr[l++];
            }
            ml = Math.max(ind - l + 1, ml);
        }
        return ml;
    }

    static int findStringsOpt(int n) {
        int[][] dp = new int[n + 1][3];
        dp[1][0] = 1;
        dp[1][1] = 1;
        //        dp[1][1] = 0;
        for (int i = 2; i <= n; ++i) {
            dp[i][0] = dp[i - 1][0] + dp[i - 1][1];
            dp[i][1] = dp[i - 1][0] + dp[i - 2][0];
        }
        return dp[n][1] + dp[n][0];
    }

    static int ops = 0;

    static int findStrings(int n) {
        return printAllStrings(new char[n + 1], false, 1);
    }

    static int printAllStrings(char[] arr, boolean hasB, int pos) {
        if (pos == arr.length) {
            //System.out.println(Arrays.toString(arr));
            return 1;
        }
        ops++;
        arr[pos] = 'a';
        int v1 = printAllStrings(arr, hasB, pos + 1);
        if (!hasB) {
            arr[pos] = 'b';
            v1 += printAllStrings(arr, true, pos + 1);
        }
        if (pos <= 1 || !(arr[pos - 1] == 'c' && arr[pos - 2] == 'c')) {
            arr[pos] = 'c';
            v1 += printAllStrings(arr, hasB, pos + 1);
        }
        return v1;
    }

    static Map<Integer, GNode> build(int[][] e, int[] values) {
        Map<Integer, GNode> nodes = new HashMap<>();
        for (int i = 0; i < values.length; ++i) {
            nodes.put(i + 1, new GNode(i + 1, values[i]));
        }
        for (int i = 0; i < e.length; ++i) {
            GNode f = nodes.get(e[i][0]);
            GNode t = nodes.get(e[i][1]);
            f.nodes.add(t);
            t.nodes.add(f);
        }
        return nodes;
    }

    static void computeTraffic(GNode root) {
        computeChildSums(root, null);
        int traffic = 0;
        for (GNode child : root.nodes) {
            traffic = Math.max(traffic, child.sum);
        }
        root.traffic = traffic;
        int sum = root.sum;
        for (GNode child : root.nodes) {
            computeChildTraffic(child, root, sum - child.sum);
        }
    }

    static void computeChildTraffic(GNode node, GNode prev, int parentSum) {
        int maxTraffic = parentSum;
        for (GNode child : node.nodes) {
            if (prev != null && prev.id == child.id) {
                continue;
            }
            maxTraffic = Math.max(maxTraffic, child.sum);
            computeChildTraffic(child, node, parentSum + node.sum - child.sum);
        }
        node.traffic = maxTraffic;
    }

    static int computeChildSums(GNode node, GNode prev) {
        int sum = 0;
        for (GNode child : node.nodes) {
            if (prev != null && child.id == prev.id) {
                continue;
            }
            sum += computeChildSums(child, node);
        }
        node.sum = sum + node.value;
        return node.sum;
    }

    static class GNode {
        int id;
        int value;
        int sum;
        int traffic;
        List<GNode> nodes;

        public GNode(int id, int value) {
            this.id = id;
            this.value = value;
            this.nodes = new ArrayList<>();
        }

        public GNode(int id, int value, List<GNode> nodes) {
            this.id = id;
            this.value = value;
            this.nodes = nodes;
        }
    }

    static void moveZeroes(int[] arr) {
        int pos = 0;
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] != 0) {
                arr[pos] = arr[i];
                pos++;
            }
        }
        for (int i = pos; i < arr.length; ++i)
            arr[i] = 0;
    }

    static void add(Character ch, int ind) {
        while (ind < MAX) {
            Map<Character, Integer> mp = tree.get(ch);
            int fr = mp.getOrDefault(ch, 0);
            mp.put(ch, fr + 1);
            ind += ind & (-ind);
        }
    }

    static void update(Character ch, int ind) {
        Character from = chars[ind];
        while (ind < MAX) {
            Map<Character, Integer> mp = tree.get(ch);
            int fr = mp.getOrDefault(ch, 0);
            mp.put(ch, fr + 1);
            mp.put(from, mp.get(from) - 1);
            ind += ind & (-ind);
        }
        chars[ind] = ch;
    }

    static final int MAX = 100001;
    static Character[] chars = new Character[MAX];
    static List<Map<Character, Integer>> tree = new ArrayList<>(MAX);

    static boolean query(int l, int r) {
        Map<Character, Integer> m1 = query(l);
        Map<Character, Integer> m2 = query(r);
        return isPalindrome(sub(m2, m1));
    }

    static Map<Character, Integer> sub(Map<Character, Integer> m2, Map<Character, Integer> m1) {
        Map<Character, Integer> res = new HashMap<>();
        for (Character key : m2.keySet()) {
            res.put(key, m2.get(key));
        }
        for (Character key : m1.keySet()) {
            res.put(key, res.get(key) - m1.get(key));
        }
        return res;
    }

    static Map<Character, Integer> query(int n) {
        Map<Character, Integer> res = new HashMap<>();
        while (n >= 0) {
            res = merge(res, tree.get(n));
            n -= n & (-n);
        }
        return res;
    }

    static Map<Character, Integer> merge(Map<Character, Integer> m1, Map<Character, Integer> m2) {
        Map<Character, Integer> res = new HashMap<>(m1);
        for (char key : m2.keySet()) {
            int f = res.getOrDefault(key, 0);
            res.put(key, f + m2.get(key));
        }
        return res;
    }

    static boolean isPalindrome(Map<Character, Integer> str) {
        boolean hasOdd = false;
        for (char key : str.keySet()) {
            int f = str.get(key);
            if (f % 2 == 1) {
                if (hasOdd)
                    return false;
                else
                    hasOdd = true;
            }
        }
        return true;
    }

    static void solve(Scanner s) {
        int n = s.nextInt();
        int k = s.nextInt();
        int[] a = new int[n + 1];
        List<List<Integer>> g = new LinkedList<>();
        for (int i = 0; i < n; ++i) {
            a[i + 1] = s.nextInt();
            g.add(new LinkedList<>());
        }
        g.add(new LinkedList<>());
        for (int i = 0; i < n - 1; ++i) {
            int u = s.nextInt();
            int v = s.nextInt();
            g.get(u).add(v);
            g.get(v).add(u);
        }
        long[][] dp = new long[n + 1][105];
        solve(g, dp, new boolean[n + 1], 1, a, k);
        long ans = 0;
        for (int i = 1; i <= n; ++i) {
            if (treeSize[i] >= k) {
                ans += dp[i][k];
                ans %= MOD;
            }
        }
        System.out.println(ans);
    }

    static int[] treeSize = new int[1000005];

    static void solve(List<List<Integer>> g, long[][] dp, boolean[] visited, int v, int[] a, int k) {
        visited[v] = true;
        int parent = -1;
        for (int u : g.get(v)) {
            if (!visited[u]) {
                solve(g, dp, visited, u, a, k);
            } else {
                parent = u;
                continue;
            }
            treeSize[v] += treeSize[u];
        }
        dp[v][a[v]] = 1;
        for (int u : g.get(v)) {
            if (u == parent)
                continue;
            long[] temp = new long[k + 1];
            for (int i = 0; i <= k; ++i) {
                temp[i] = dp[v][i];
            }
            for (int i = 0; i <= k; ++i) {
                for (int j = 0; j <= k - i; ++j) {
                    temp[i + j] += dp[v][i] * dp[u][j];
                    temp[i + j] %= MOD;
                }
            }
            for (int i = 0; i < temp.length; ++i) {
                dp[v][i] = temp[i];
            }
        }
        treeSize[v] += a[v];
    }

    public static int[] toArray(Stack<Integer> s) {
        int[] a = new int[s.size()];
        for (int i = 0; i < a.length; ++i)
            a[i] = s.get(i);
        return a;
    }

    public static int printCombs(int n, int num, Stack<Integer> ans, int ind, String str) {
        if (ans.size() == num) {
            return findPerms(toArray(ans), 0, str);
        }
        int sum = 0;
        for (int i = ind; i <= n; ++i) {
            ans.push(i);
            int cr = printCombs(n, num, ans, i + 1, str);
            sum += cr;
            sum %= MOD;
            ans.pop();
        }
        return sum;
    }

    public static int findPerms(int[] ids, int cind, String str) {
        if (ids.length <= cind) {
            return 1;
        }
        int sum = 0;
        for (int i = cind; i < ids.length; ++i) {
            swap(ids, i, cind);
            sum += findPerms(ids, cind + 1, str);
            sum %= MOD;
            swap(ids, i, cind);
        }
        return sum;
    }

    public static int maxSum(int[] a1, int a2[]) {
        int sum = 0;
        int k1 = 0, k2 = 0;
        for (int i = 0; i < Math.min(a1.length, a2.length); ++i) {
            if (a1[i] == a2[i]) {
                sum += Math.max(k1, k2);
                k1 = 0;
                k2 = 0;
            }
            k1 += a1[i];
            k2 += a2[i];
        }
        return sum;
    }

    static void print(LNode lst) {
        while (lst != null) {
            System.out.print(lst.data + "->");
            lst = lst.next;
        }
        System.out.println();
    }

    static LNode sum(LNode l1, LNode l2) {
        LNode r1 = reverse(l1);
        LNode r2 = reverse(l2);
        int carry = 0;
        LNode c1 = r1, c2 = r2, p1 = c1, p2 = c2;
        while (c1 != null && c2 != null) {
            c1.data += c2.data + carry;
            carry = c1.data / 10;
            c1.data %= 10;
            p1 = c1;
            p2 = c2;
            c1 = c1.next;
            c2 = c2.next;
        }
        if (c1 == null) {
            p1.next = c2;
            while (c2 != null && carry != 0) {
                c2.data += carry;
                carry = c2.data / 10;
                c2.data %= 10;
                p1 = c2;
                c2 = c2.next;
            }
        } else {
            while (c1 != null && carry != 0) {
                c1.data += carry;
                carry = c1.data / 10;
                c1.data %= 10;
                p1 = c1;
                c1 = c1.next;
            }
        }
        if (carry != 0) {
            LNode newNode = new LNode(carry, null);
            p1.next = newNode;
        }
        return reverse(r1);
    }

    static LNode reverse(LNode root) {
        if (root == null || root.next == null) {
            return root;
        }
        LNode prev = root, curr = root.next;
        prev.next = null;
        while (curr != null) {
            LNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }

    /**
     * [],{},()
     * [{()}{()}]
     * [({)}]
     */

    static boolean isBalanced(String expr) {
        Stack<Character> stack = new Stack<>();
        char[] open = new char[] { '(', '{', '[' };
        char[] close = new char[] { ')', '}', ']' };
        for (int i = 0; i < expr.length(); ++i) {
            if (has(open, expr.charAt(i))) {
                stack.push(expr.charAt(i));
            } else if (has(close, expr.charAt(i))) {
                int ind = getInd(close, expr.charAt(i));
                if (stack.isEmpty() || stack.pop() != open[ind]) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    static boolean has(char[] arr, char el) {
        return getInd(arr, el) != -1;
    }

    static int getInd(char[] arr, char el) {
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] == el)
                return i;
        }
        return -1;
    }

    static int dfs(Map<Integer, LinkedList<Integer>> g, int v, int path, boolean[] visited, int n) {
        if (visited[v])
            return 10000000;
        visited[v] = true;
        if (v == n)
            return 0;
        int mpath = 100000;

        for (int u : g.get(v)) {

            if (!visited[u]) {
                //System.out.println(v+"  "+u);
                int cpath = 1 + dfs(g, u, path + 1, visited, n);
                mpath = Math.min(mpath, cpath);
                visited[u] = false;
            }
        }
        visited[v] = false;
        if (mpath == 100000)
            return 0;
        return mpath;
    }

    static Random r = new Random();

    static int rand(int max) {
        return r.nextInt(max) + 1;
    }

    static Worksheet1.LNode fromArr(int[] arr) {
        Worksheet1.LNode root = new LNode(arr[0]);
        Worksheet1.LNode prev = root;
        for (int i = 1; i < arr.length; ++i) {
            Worksheet1.LNode next = new LNode(arr[i]);
            prev.next = next;
            prev = next;
        }
        LNode curr = root;
        while (curr != null) {
            System.out.print(curr.data + "   ");
            curr = curr.next;
        }
        System.out.println();
        return root;
    }

    static void modify(Worksheet1.LNode root) {
        Stack<Worksheet1.LNode> st = new Stack<>();
        Worksheet1.LNode slow = root, fast = root;
        if (root == null || root.next == null)
            return;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        while (slow != null) {
            st.push(slow);
            slow = slow.next;
        }
        Worksheet1.LNode curr = root;
        while (!st.isEmpty()) {
            Worksheet1.LNode next = st.pop();
            if (next.data == curr.data) {
                break;
            }
            Worksheet1.LNode temp = curr.next;
            curr.next = next;
            next.next = temp;
            curr = temp;
        }
        curr.next = null;
        curr = root;
        while (curr != null) {
            System.out.print(curr.data + "   ");
            curr = curr.next;
        }
        System.out.println();
    }

    private static class LNode {
        int data;
        LNode next;

        public LNode(int data) {
            this.data = data;
            this.next = null;
        }

        public LNode(int data, Worksheet1.LNode next) {
            this.data = data;
            this.next = next;
        }
    }
    /**
     *
     * test1("")
     * test2("aabbaa")
     * test3(null)
     * test4("aaabbaa")
     * test5("aabbaaa")
     * test6("123321 123321")
     * test6("123321 2aaaaaabbbbbbaaaaaa1 123321")
     *
     * t[i][j]: t[i][j] = t[i+1][j-1]+2, if s[i]==s[j]
     * else
     *          t[i][j] = Max(t[i+1][j],t[i][j-1])
     *
     * */
}