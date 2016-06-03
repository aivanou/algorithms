package org.java.algorithms.lists;

/**
 */
public class LUtil {
    public static LNode fromArray(int[] arr) {
        return fromArray(arr, 0, null);
    }

    public static LNode fromArray(int[] arr, int i, LNode prev) {
        if (i >= arr.length)
            return null;
        LNode node = new LNode(arr[i], null, prev);
        LNode next = fromArray(arr, i + 1, node);
        node.next = next;
        if (next != null) {
            next.prev = node;
        }
        return node;
    }

    public static void printList(LNode node) {
        while (node != null) {
            System.out.print(node.data + " -> ");
            node = node.next;
        }
        System.out.println();
    }
}
