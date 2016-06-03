package org.java.algorithms.trees;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 */
public class TreeUtils {

    public static TNode fromSortedList(List<Integer> lst) {
        return fromSortedList(lst, 0, lst.size() - 1);
    }

    public static TNode fromSortedArray(int[] arr) {
        return fromSortedArray(arr, 0, arr.length - 1);
    }

    private static TNode fromSortedArray(int[] lst, int l, int r) {
        if (l > r)
            return null;
        int mid = l + (r - l) / 2;
        TNode node = new TNode(lst[mid], fromSortedArray(lst, l, mid - 1), fromSortedArray(lst, mid + 1, r));
        return node;
    }

    private static TNode fromSortedList(List<Integer> lst, int l, int r) {
        if (l > r)
            return null;
        int mid = l + (r - l) / 2;
        TNode node = new TNode(lst.get(mid), fromSortedList(lst, l, mid - 1), fromSortedList(lst, mid + 1, r));
        return node;
    }

    static void printInc(TNode rt1, TNode rt2) {
        Stack<TNode> st1 = new Stack<>();
        Stack<TNode> st2 = new Stack<>();
        TNode c1 = rt1, c2 = rt2;
        while (c1 != null) {
            st1.push(c1);
            c1 = c1.left;
        }
        while (c2 != null) {
            st2.push(c2);
            c2 = c2.left;
        }
        c1 = inorder(st1);
        c2 = inorder(st2);

        while (true) {
            if (c1 == null) {
                c1 = inorder(st1);
            }
            if (c2 == null) {
                c2 = inorder(st2);
            }
            if (c1 == null || c2 == null) {
                break;
            }
            if (c1.v > c2.v) {
                System.out.println(c2.v);
                c2 = null;
            } else if (c1.v < c2.v) {
                System.out.println(c1.v);
                c1 = null;
            } else {
                System.out.println(c1.v);
                System.out.println(c1.v);
                c1 = null;
                c2 = null;
            }
        }
        while (c1 != null) {
            System.out.println(c1.v);
            c1 = inorder(st1);
        }
        while (c2 != null) {
            System.out.println(c2.v);
            c2 = inorder(st2);
        }
    }

    static TNode inorder(Stack<TNode> st) {
        if (st.isEmpty())
            return null;
        TNode el = st.pop();
        if (el.right != null) {
            TNode r = el.right;
            while (r != null) {
                st.push(r);
                r = r.left;
            }
        }
        return el;
    }

    public static void main(String[] args) {
        //        TNode r = bstFromPreorder(new int[] { 6, 4, 1, 2, 9, 88, 1, 42 });
        //        Traversals.inOrderTraversal(r, 0);
        //        postOrder(fromSortedArray(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 }));
        System.out.println(search(new int[] { 1, 1, 1, 2, 3, 3, 3, 4, 5 }, -1));
    }

    public static void postOrder(TNode node) {
        if (node == null)
            return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.v);
    }

    public static TNode treeFromInOrderAndPostOrder(int[] in, int[] post) {
        return treeFromInOrderAndPostOrder(in, post, 0, in.length - 1, 0, post.length - 1);
    }

    public static TNode treeFromInOrderAndPostOrder(int[] in, int[] post, int inS, int inE, int pS, int pE) {
        if (inS > inE || pS > pE)
            return null;
        TNode node = new TNode(post[pE], null, null);
        int i = 0;
        for (i = 0; i < in.length; ++i) {
            if (in[i] == node.v) {
                break;
            }
        }
        int len = i - inS;
        node.left = treeFromInOrderAndPostOrder(in, post, inS, i - 1, pS, pS + len - 1);
        node.right = treeFromInOrderAndPostOrder(in, post, i + 1, inE, pS + len, pE);
        return node;
    }

    public static TNode treeFromInorderAndPreorder(int[] in, int[] pre) {
        return treeFromInorderAndPreorder(in, pre, 0, in.length - 1, new Index());
    }

    static TNode treeFromInorderAndPreorder(int[] in, int[] pre, int inSt, int inEnd, Index preInd) {
        if (inSt > inEnd)
            return null;
        TNode node = new TNode(pre[preInd.index], null, null);
        int ind = search(in, inSt, inEnd, pre[preInd.index]);
        preInd.index++;
        node.left = treeFromInorderAndPreorder(in, pre, inSt, ind - 1, preInd);
        node.right = treeFromInorderAndPreorder(in, pre, ind + 1, inEnd, preInd);
        return node;
    }

    static int search(int[] a, int el) {
        return search(a, 0, a.length - 1, el);
    }

    static int search(int[] arr, int l, int r, int el) {
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (arr[mid] == el)
                return mid;
            else if (arr[mid] > el) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }

    public static TNode bstFromPreorder(int[] arr) {
        return bstFromPreorder(arr, new Index(), Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    static TNode bstFromPreorder(int[] arr, Index ind, int min, int max) {
        if (ind.index >= arr.length)
            return null;
        int key = arr[ind.index];
        if (key < min || key > max) {
            return null;
        }
        ind.index += 1;
        TNode node = new TNode(key, null, null);
        node.left = bstFromPreorder(arr, ind, min, key);
        node.right = bstFromPreorder(arr, ind, key, max);
        return node;
    }

    private static class Index {
        int index;
    }
}
