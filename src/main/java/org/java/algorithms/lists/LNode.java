package org.java.algorithms.lists;

/**
 */
public class LNode {
    public int data;
    public LNode next;
    public LNode prev;

    public LNode(int data, LNode next) {
        this.data = data;
        this.next = next;
    }

    public LNode(int data) {

        this.data = data;
    }

    public LNode(int data, LNode next, LNode prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}
