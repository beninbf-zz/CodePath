package main.java.com.codepath.linkedlistproblems;

import main.java.com.codepath.objects.LinkedListNode;
import main.java.com.codepath.objects.LinkedListNodeTuple;
import sun.awt.image.ImageWatched;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

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
        if (head == null || k <= 0) {
            return head;
        }

        int counter = 0;

        while(head != null && counter < k) {
            head = head.next;
            counter++;
        }

        return head;
    }

    public boolean detectCycle(LinkedListNode head) {

        if (head == null) {
            return false;
        }

        Set<Integer> set = new HashSet<>();
        LinkedListNode temp = head;
        while (temp != null) {
            if (!set.contains(temp.val)) {
                set.add(temp.val);
            } else {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public boolean detectCycleEff(LinkedListNode head) {
        if (head == null) {
            return false;
        }

        LinkedListNode slow = head;
        LinkedListNode fast = head.next != null ? head.next : null;

        while (slow != null && fast != null) {
           if (slow.val == fast.val) {
                return true;
           }
           slow = slow.next;
           if (fast.next != null) {
               fast = fast.next.next;
           } else {
               fast = fast.next;
           }
        }
        return false;
    }

    public LinkedListNode dutchSort(int x, LinkedListNode head) {
        if (head.next == null) {
            return head;
        }

        LinkedListNode p1 = null;
        LinkedListNode p1End = null;

        LinkedListNode p2 = null;
        LinkedListNode p2End = null;

        LinkedListNode p3 = null;
        LinkedListNode p3End = null;

        LinkedListNode current = head;
        while (current != null) {
            if (current.val < x) {
                if (p1 == null && p1End == null) {
                    p1 = current;
                    p1End = current;
                } else {
                    p1End.next = current;
                    p1End = p1End.next;
                }
            } else if (current.val > x) {
                if (p3 == null && p3End == null) {
                    p3 = current;
                    p3End = current;
                } else {
                    p3End.next = current;
                    p3End = p3End.next;
                }
            } else {
                if (p2 == null && p2End == null) {
                    p2 = current;
                    p2End = current;
                } else {
                    p2End.next = current;
                    p2End = p2End.next;
                }
            }
            current = current.next;
        }
        p1End.next = p2;
        p2End.next = p3;
        p3End.next = null;
        return p1;
    }

    public LinkedListNode flattenWithBFS(LinkedListNode head) {
        if (head == null) {
            return head;
        }
        LinkedListNode temp = new LinkedListNode(-1);
        Queue<LinkedListNode> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            LinkedListNode node = queue.poll();
            temp.next = node;

            if (node.next != null) {
                queue.add(node.next);
            }

            if (node.child != null) {
                queue.add(node.child);
            }
            temp = temp.next;
        }
        return head;
    }

    public LinkedListNode flattenWithEff(LinkedListNode head) {
        LinkedListNode tail = getTail(head);

        LinkedListNode current = head;
        while (current != null) {
            if (current.child != null) {
                tail.next = current.child;
                tail = getTail(current.child);
            }
            current = current.next;
        }
        return head;
    }

    private LinkedListNode getTail(LinkedListNode head) {
        LinkedListNode current = head;
        while (current.next != null) {
            current = current.next;
        }
        return current;
    }


    /**
     * This is the ultimate linked list problem to me. It requires a great deal
     * of understanding regarding pointer arithmetic. Understanding that, with a
     * singly linked list, that if you want to change a given node, without
     * breaking the list, then you have to keep a pointer to the "previous" node
     * or the node pointing to the current node you are changing.
     *
     * I choose to use iteration to sort a linked list, because pointer arithmetic
     * is complicated enough and I don't want to also have to worry about what is
     * on the call stack.
     *
     * What we essentially do, is sort every sublist of size 1, merging sorted
     * linked list of size one.
     *
     * Then we move through the list again, merging sublist of size 2, then of size 4,
     * so on and so forth, until the size of the sub lists we are merging is bigger
     * than the size of the list.
     *
     * Considering we could potentially be resetting the head of the list, we are going
     * to make a "dummy" pointer, whose next field does nothing but point to the head of
     * the sorted list. When we return, we will return dummy.next;
     *
     * We will also have another pointer, "previous", where previous.next will initially point to
     * the head of the list. This previous pointer will be returned by our call to merge, and it
     * will move through the list, essentially returning us the end of the previously sorted list
     *
     * We have to move through the entire list for every size, k, of lists that we are merging.
     * That means moving through the entire list for sub lists of size 1, 2, 4, and so on, while
     * k < the size of the list. In order to get the left and right bounds of the sub lists to
     * merge, we employ a fastForward function which will move forward k steps.
     *
     * The important point to this problem was to keep our pointer arithmetic up to date.
     * That means that on our first merge, we have to update dummy.next, to keep our
     * head up to date, BUT only on that first merge. Successive merges for the next "k" size
     * sub lists will essentially just result in updating the end of the previously sorted
     * sub list. Because we cant return the node assignments from function calls, given that
     * Java is call by value, we return a Tuple of nodes, containing the "head" of the list
     * (only used for the first merge of any given "k" size) and the previous node from the
     * sorted list which at the end of merge is the end of the previously sorted list.
     *
     * We repeat this process for every size k, each time updating the arguments to
     * our merge call. The "next" pointers of the arguments to the merge call can
     * be safely updated, but for anything where the node is completely reassigned,
     * if we want to get that after the function returns, we must return that object.
     *
     * We update, previous, left, right, and right end, for every merge of sublists of
     * size k.
     *
     * We move throught he list merging sublists of size 1, until the right pointer
     * gets to the end. Then we multiple by 2, and repeat until k >= n.
     * @param head head of the list
     * @return the beginning of the sorted list
     */
    public LinkedListNode sort(LinkedListNode head) {
        if (head == null) {
            return head;
        }
        LinkedListNode dummy = new LinkedListNode("dummy");
        dummy.next = head;
        LinkedListNode previous = new LinkedListNode("previous");
        previous.next = head;

        int n = length(head);
        int k = 1;
        LinkedListNode left = head;
        LinkedListNode right = fastForward(head, k);
        LinkedListNode rightEnd = fastForward(right, k);

        while (k < n) {
            boolean firstMerge = true;
            while (right != null) {
                LinkedListNodeTuple tuple = merge(previous, left, right, rightEnd);
                if (firstMerge) {
                    dummy.next = tuple.head;
                    firstMerge = false;
                }
                previous = tuple.endOfPriorList;
                left = previous.next;
                right = fastForward(left, k);
                rightEnd = fastForward(right, k);
            }
            k = k * 2;
            previous = dummy;
            left = dummy.next;
            right = fastForward(left, k);
            rightEnd = fastForward(right, k);
        }

        return dummy.next;
    }

    /**
     * The heart of the merge sort function above. This function merges to sorted linked lists.
     * It does so by essentially mimicking the traditional merge operation use by arrays.
     *
     * We essentially take in 4 pointers, one pointer to the beginning of both sub lists
     * we want to sort (so that's 2 pointers). A "previous" pointer which points
     * to the beginning of the "left" sub list. This previous pointer is essential because
     * we must realize that moving that reordering a linked list essentially means, changing
     * what the prior node is pointing to, so we MUST have this previous pointer.
     *
     * The last pointer is the right boundary, the right end, which tells us when to stop merging.
     *
     * The code runs very closely to the traditional merge sort. Because we need to return the
     * head of the list for the first merge, and then the previous pointer for all merges,
     * we create a tuple so that we can capture the reassignments and return them to the caller
     * this proved critical to getting the problem correct.
     *
     * @param previous pointer to node prior to left
     * @param left beginning of left sub list
     * @param right beginning of right sub list
     * @param rightEnd marks the end of the right sublit
     * @return LinkedListNodeTuple
     */
    public LinkedListNodeTuple merge(LinkedListNode previous, LinkedListNode left, LinkedListNode right, LinkedListNode rightEnd) {
        LinkedListNode head = null;
        LinkedListNode leftEnd = right;
        while (left != leftEnd && right != rightEnd) {
            if (left.val <= right.val) {
                if (head == null) {
                    head = left;
                }
                previous.next = left;
                previous = previous.next;
                left = left.next;
            } else {
                if (head == null) {
                    head = right;
                }
                previous.next = right;
                previous = previous.next;
                right = right.next;
            }
        }

        while (left != leftEnd) {
            previous.next = left;
            previous = previous.next;
            left = left.next;
        }

        while (right != rightEnd) {
            previous.next = right;
            previous = previous.next;
            right = right.next;
        }

        previous.next = rightEnd;
        return new LinkedListNodeTuple(head, previous);
    }

    private String getListString(LinkedListNode head) {
        StringBuilder sb = new StringBuilder();
        LinkedListNode current = head;
        while (current != null) {
            sb.append(current);
            current = current.next;
        }
        sb.append("\n");
        return sb.toString();
    }

    private void print(LinkedListNode head) {
        LinkedListNode current = head;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }

}
