package org.java.algorithms;

import java.util.Scanner;

/**
 */
public class VeeRoute {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int seed =+ sc.nextInt();
        int na = sc.nextInt();
        int ng = sc.nextInt();
        int no = sc.nextInt();
        int nd = sc.nextInt();
        Point[] ap = readPoints(sc, na);
        Point[] gp = readPoints(sc, ng);
        Point[] op = readPoints(sc, no);
    }

    private static Point[] readPoints(Scanner sc, int len) {
        Point[] points = new Point[len];
        for (int i = 0; i < len; ++i)
            points[i] = Point.read(sc);
        return points;
    }

    private static class Point {
        int x;
        int y;

        static Point read(Scanner sc) {
            return new Point(sc.nextInt(), sc.nextInt());
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Point point = (Point) o;

            if (x != point.x)
                return false;
            if (y != point.y)
                return false;

            return true;
        }

        @Override public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
