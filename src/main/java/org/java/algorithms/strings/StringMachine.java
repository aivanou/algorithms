package org.java.algorithms.strings;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

/**
 */
public class StringMachine {
    public static void main(String[] args) throws Exception {

//        String[] patterns = new String[801];
//        patterns[0] = "1";
//        BigInteger two = BigInteger.valueOf(2);
//        BigInteger res = BigInteger.valueOf(1);
//        for (int i = 1; i < 801; ++i) {
//            res = res.multiply(two);
//            patterns[i] = res.toString();
//        }
//        int alpthSize = 10;
//        StringMachine sm = new StringMachine(patterns, alpthSize);
//        Scanner sc = new Scanner(System.in);
//        int t = sc.nextInt();
//        while (t-- > 0) {
//            String text = sc.next();
//            System.out.println(sm.findNHits(text));
//        }
        String[] p = new String[] { "2", "32" };
        int l = 10;
        StringMachine m = new StringMachine(p, l);
        System.out.println(m.findNHits("02322232"));
    }

    private int[][] moves;
    private int[] fallback;
    public int[] finalStates;

    public StringMachine(String[] patterns, int alphSize) {
        int states = 0;
        for (String p : patterns)
            states += p.length();
        this.moves = new int[states + 1][alphSize];
        this.fallback = new int[states + 1];
        for (int i = 0; i < moves.length; ++i) {
            for (int j = 0; j < moves[i].length; ++j) {
                moves[i][j] = -1;
            }
            fallback[i] = 0;
        }
        this.finalStates = new int[states + 1];
        constructTrie(patterns);
        constructFallbackEdges(alphSize);
    }

    private void constructFallbackEdges(int alphSize) {
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < alphSize; ++i) {
            if (moves[0][i] == -1)
                moves[0][i] = 0;
        }
        for (int i = 0; i < alphSize; ++i) {
            if (moves[0][i] != 0) {
                q.add(moves[0][i]);
                fallback[moves[0][i]] = 0;
            }
        }
        while (!q.isEmpty()) {
            int state = q.poll();
            for (int i = 0; i < alphSize; ++i) {
                if (moves[state][i] == -1)
                    continue;
                int fstate = fallback[state];
                while (moves[fstate][i] == -1) {
                    fstate = fallback[fstate];
                }
                fstate = moves[fstate][i];
                fallback[moves[state][i]] = fstate;
                finalStates[moves[state][i]] += finalStates[fstate];
                q.add(moves[state][i]);
            }
        }
    }

    private void constructTrie(String[] patterns) {
        int state = 1;
        for (int i = 0; i < patterns.length; ++i) {
            int currState = 0;
            for (char ch : patterns[i].toCharArray()) {
                int ind = ch - '0';
                if (moves[currState][ind] == -1) {
                    moves[currState][ind] = state++;
                }
                currState = moves[currState][ind];
            }
            finalStates[currState] = 1;
        }
    }

    public int findNHits(String text) {
        int hits = 0;
        int state = 0;
        for (char ch : text.toCharArray()) {
            state = nextState(ch, state);
            hits += finalStates[state];
        }
        return hits;
    }

    public int nextState(char symb, int currState) {
        int el = symb - '0';
        int state = currState;
        while (moves[state][el] == -1) {
            state = fallback[state];
        }
        return moves[state][el];
    }
}

