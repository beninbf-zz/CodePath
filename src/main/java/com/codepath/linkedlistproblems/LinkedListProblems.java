package main.java.com.codepath.linkedlistproblems;

import main.java.com.codepath.objects.LinkedListNode;

/**
 * The class LinkedListProblems is a class for functions regarding Linked Lists.
 */
public class LinkedListProblems {

    /**
     * Returns the length of a linked list.
     *
     * @param node the node
     * @return int
     */
    public int length(LinkedListNode node) {
        int length = 0;
        if (node == null) {
            return length;
        }

       while(node != null) {
           node = node.next;
           length++;
       }
       return length;
    }


    /**
     * Returns a linked list node k steps from the head.
     * If k = 2 then fastForward will return the linked list node 2 nodes
     * away from the head node.
     *
     * Only used for singly linked lists
     *
     * @param head the head
     * @param k    the number of linked list nodes to move.
     * @return the linked list node k steps away from head
     */
    public LinkedListNode fastForward(LinkedListNode head, int k) {
        if (head == null || k < 0) {
            return head;
        }

        int len = length(head);
        if (k > len) {
            throw new IllegalArgumentException(String.format("Your linked list has %s nodes left and you cant move k=%s steps", len, k));
        }

        int counter = 0;

        while(head != null && counter < k) {
            head = head.next;
            counter++;
        }

        return head;
    }
}
