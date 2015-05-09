package HackerRank.GraphTheory;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by aliaksandr on 2015-05-07.
 */
public class HackerCountry {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] graph = new int[n + 1][n + 1];
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                graph[i][j] = sc.nextInt();
            }
        }
        sc.close();
        int min = Integer.MAX_VALUE;
        int nmin = 0;
        int[][][] minWays = new int[n][n + 1][n + 1];
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                minWays[0][i][j] = graph[i][j];
                if(i==j)
                    continue;
                if(min>(graph[i][j]+graph[j][i])){
                    min = graph[i][j]+graph[j][i];
                    nmin = 2;
                }
            }
        }
        for (int v = 1; v < n - 1; ++v) {
            for (int i = 1; i <= n; ++i) {
                for (int j = 1; j <= n; ++j) {
                    if (i == j)
                        continue;
                    int minWay = Integer.MAX_VALUE;
                    for (int k = 1; k <= n; ++k) {
                        if (k == i || k == j)
                            continue;
                        if ((minWays[v - 1][i][k] + minWays[v - 1][k][j]) < minWay) {
                            minWay = minWays[v - 1][i][k] + minWays[v - 1][k][j];
                        }
                    }
                    if ((minWay + graph[j][i]) < min) {
                        min = minWay + graph[j][i];
                        nmin = v;
                    }
                    minWays[v][i][j] = minWay;
                }
            }
        }
        int g = gcd(min, nmin);
        System.out.println(min/g  + "/" + nmin/g );
    }

    static int gcd(int a, int b) {
        if (a == 0 || b == 0) return a + b; // base case
        return gcd(b, a % b);
    }

}
