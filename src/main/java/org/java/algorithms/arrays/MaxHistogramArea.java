package org.java.algorithms.arrays;

import java.util.Stack;

/**
 */
public class MaxHistogramArea {
    public static void main(String[] args) {
        System.out.println(find(new int[] { 2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1 }));
    }

    static int find(int[] arr) {
        Stack<Integer> st = new Stack<>();
        int[] dist = new int[arr.length];
        st.push(0);
        int max = 0;
        for (int i = 0; i < arr.length; ++i) {
            if (st.isEmpty() || arr[st.peek()] < arr[i]) {
                st.push(i);
                continue;
            }
            int ind = st.pop();
            int area = (st.empty() ? i : i - st.peek() - 1) * arr[ind];
            max = Math.max(max, area);
        }
        while (!st.empty()) {
            int ind = st.pop();
            max = Math.max(max, (st.empty() ? ind : ind - st.peek() - 1) * arr[ind]);
        }
        return max;
    }
}
