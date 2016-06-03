package org.java.algorithms.arrays;

import java.util.Arrays;
import java.util.Stack;

/**
 */
public class InfixToPostfix {

    public static void main(String[] args) {
        //        System.out.println(toPostfix("a+b*c"));
        //        printNextGreaterElement(new int[] { 4, 2, 5, 7, 88, 3, 123, 43, 5 });
        System.out.println(Arrays.toString(stockPlan(new int[] { 1, 2, 3, 4, 3, 2, 1 })));
    }

    public static int[] stockPlan(int[] arr) {
        int[][] tb = new int[arr.length][2];
        Stack<Integer> st = new Stack<>();
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            int num = 0;
            while (!st.isEmpty() && tb[st.peek()][0] <= arr[i]) {
                num += tb[st.peek()][1];
                st.pop();
            }
            res[i] = num + 1;
            tb[i][0] = arr[i];
            tb[i][1] = num + 1;
            st.push(i);
        }
        return res;
    }

    public static void sortStack(Stack<Integer> stack) {
        if (stack.isEmpty())
            return;
        int el = stack.pop();
        sortStack(stack);
        insert(stack, el);
    }

    public static void insert(Stack<Integer> st, int el) {
        if (st.isEmpty() || st.peek() >= el) {
            st.push(el);
        } else {
            int temp = st.pop();
            insert(st, el);
            st.push(temp);
        }
    }

    public static void printNextGreaterElement(int[] arr) {
        if (arr.length == 0) {
            return;
        }
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[arr.length];
        res[arr.length - 1] = -1;
        stack.push(arr[arr.length - 1]);
        for (int i = arr.length - 2; i >= 0; --i) {
            while (!stack.isEmpty() && stack.peek() < arr[i]) {
                stack.pop();
            }
            if (stack.isEmpty()) {
                res[i] = -1;
            } else {
                res[i] = stack.peek();
            }
            stack.push(arr[i]);
        }
        System.out.println(Arrays.toString(res));
    }

    public static String toPostfix(String expr) {
        StringBuilder out = new StringBuilder();

        for (char ch : expr.toCharArray()) {
            if (isOperand(ch)) {
                out.append(ch);
            } else if (ch == '(') {
                push(ch);
            } else if (ch == ')') {
                while (top != null && top.data != '(') {
                    out.append((char) pop());
                }
            } else {
                while (top != null && getOrder((char) top.data) > getOrder(ch)) {
                    out.append((char) pop());
                }
                push(ch);
            }
        }
        while (top != null) {
            out.append((char) pop());
        }
        return out.toString();
    }

    public static boolean isOperand(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

    public static int getOrder(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '/':
            case '*':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    public static SNode top;

    public static int pop() {
        int d = top.data;
        top = top.next;
        return d;
    }

    public static void push(int data) {
        SNode node = new SNode(data, top);
        top = node;
    }

    private static class SNode {
        int data;
        SNode next;

        public SNode(int data) {
            this.data = data;
        }

        public SNode(int data, SNode next) {
            this.data = data;
            this.next = next;
        }
    }
}
