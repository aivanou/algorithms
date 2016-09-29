import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Spot1 {
    public static void main(String[] args) throws Exception {
        displayDigit('1', new Stack<Character>(), 2);
    }

    static void displayDigit(char start, Stack<Character> number, int total) {
        for (char dig = start; dig <= '9'; dig++) {
            number.push(dig);
            System.out.println(number);
            if (total > 0) {
                displayDigit('0', number, total - 1);
            }
            number.pop();
        }
    }

    static float findProb(int n, int x, int y, int steps, float cp) {
        if (x < 0 || y < 0 || x == n || y == n)
            return cp;
        else if (steps == 0)
            return 0.0f;
        float total = 0;
        total += findProb(n, x + 1, y, steps - 1, cp * 0.25f);
        total += findProb(n, x - 1, y, steps - 1, cp * 0.25f);
        total += findProb(n, x, y + 1, steps - 1, cp * 0.25f);
        total += findProb(n, x, y - 1, steps - 1, cp * 0.25f);
        return total;
    }

    static long maxDiff(long[] arr) {
        long[] maxLeft = new long[arr.length];
        long[] minLeft = new long[arr.length];
        long[] maxRight = new long[arr.length];
        long[] minRight = new long[arr.length];
        maxLeft[0] = arr[0];
        minLeft[0] = arr[0];
        for (int i = 1; i < arr.length; ++i) {
            maxLeft[i] = arr[i];
            minLeft[i] = arr[i];
            if (maxLeft[i - 1] > 0) {
                maxLeft[i] += maxLeft[i - 1];
            }
            if (minLeft[i - 1] < 0) {
                minLeft[i] += minLeft[i - 1];
            }
        }
        maxRight[arr.length - 1] = arr[arr.length - 1];
        minRight[arr.length - 1] = arr[arr.length - 1];
        for (int i = arr.length - 2; i >= 0; --i) {
            maxRight[i] = arr[i];
            minRight[i] = arr[i];
            if (maxRight[i + 1] > 0) {
                maxRight[i] += maxRight[i + 1];
            }
            if (minRight[i + 1] < 0) {
                minRight[i] += minRight[i + 1];
            }
        }
        long maxRange = 0;
        for (int i = 0; i < arr.length - 1; ++i) {
            long r1 = Math.abs(minLeft[i] - maxRight[i + 1]);
            long r2 = Math.abs(maxLeft[i] - minRight[i + 1]);
            maxRange = Math.max(maxRange, Math.max(r1, r2));
        }
        return maxRange;
    }

    static int maxGold(int[] piles) {
        int[][] dp = new int[piles.length][piles.length];
        //1,2 -> max(1+sum(2)-dp[2],2+sum(1)-dp[1])
        //2,3 -> max(2,3)
        //1,2,3 -> max(1+sum(2,3)-dp[2,3],3+sum(1,2)-dp[1,2])
        int[] prefSum = new int[piles.length + 1];
        for (int i = 0; i < piles.length; ++i) {
            dp[i][i] = piles[i];
            prefSum[i + 1] = prefSum[i] + piles[i];
        }
        for (int diff = 1; diff < piles.length; ++diff) {
            for (int start = 0; start < piles.length - diff; ++start) {
                int end = start + diff;
                int sum = prefSum[end + 1] - prefSum[start + 1];
                int optimal = Math.max(piles[start] + sum - piles[start] - dp[start + 1][end], piles[end] + sum - piles[end] - dp[start][end - 1]);
                dp[start][end] = optimal;
            }
        }
        return dp[0][piles.length - 1];
    }

    static List<Integer> fromArr(int[] arr) {
        List<Integer> l = new ArrayList<>(arr.length);
        for (int v : arr)
            l.add(v);
        return l;
    }

    static int minRange(List<List<Integer>> lists) {
        int[] nextInd = new int[lists.size()];
        PriorityQueue<Integer> q = new PriorityQueue<>((i1, i2) -> {
            return Integer.compare(lists.get(i1).get(nextInd[i1] - 1), lists.get(i2).get(nextInd[i2] - 1));
        });
        int[] lastSeenNumber = new int[lists.size()];
        for (int i = 0; i < lists.size(); ++i) {
            lastSeenNumber[i] = -1;
            nextInd[i]++;
            q.add(i);
        }
        int filled = 0;
        int minRangeLen = Integer.MAX_VALUE;
        while (!q.isEmpty()) {
            int ind = q.poll();
            int elPos = nextInd[ind] - 1;
            //            System.out.println(ind + " " + elPos);
            if (lastSeenNumber[ind] == -1) {
                filled++;
            }
            lastSeenNumber[ind] = lists.get(ind).get(elPos);
            if (filled == lists.size()) {
                int minInd = findMin(lastSeenNumber);
                int maxInd = findMax(lastSeenNumber);
                minRangeLen = Math.min(minRangeLen, lastSeenNumber[maxInd] - lastSeenNumber[minInd]);
                System.out.println(String.format("range:[%d : %d]", lastSeenNumber[maxInd], lastSeenNumber[minInd]));
                lastSeenNumber[minInd] = -1;
                filled--;
            }
            if (nextInd[ind] < lists.get(ind).size()) {
                nextInd[ind]++;
                q.add(ind);
            }
        }
        return minRangeLen;
    }

    static int findMin(int[] arr) {
        int min = Integer.MAX_VALUE;
        int minInd = 0;
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] < min) {
                min = arr[i];
                minInd = i;
            }
        }
        return minInd;
    }

    static int findMax(int[] arr) {
        int max = Integer.MIN_VALUE;
        int maxInd = 0;
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] > max) {
                max = arr[i];
                maxInd = i;
            }
        }
        return maxInd;

    }

    static String normalize(String path) {
        StringBuilder b = new StringBuilder();
        String[] parts = path.split("/");
        ArrayDeque<String> q = new ArrayDeque();
        int ind = -1;
        while (parts[++ind].equals("..")) {
            b.append("../");
        }
        while (ind < parts.length) {
            if (!parts[ind].equals("..")) {
                q.addFirst(parts[ind]);
            } else {
                if (q.isEmpty()) {
                    b.append("../");
                } else {
                    q.removeFirst();
                }
            }
            ind++;
        }
        while (!q.isEmpty()) {
            b.append(q.removeLast());
            b.append("/");
        }
        return b.toString();
    }
}
