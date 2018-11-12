package test.java.com.codepath.linkedlistproblems;

import main.java.com.codepath.linkedlistproblems.LinkedListProblems;
import main.java.com.codepath.objects.LinkedListNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * LinkedListProblems Unit tests
 */
public class LinkedListProblemsTest {

    private LinkedListProblems linkedListProblems;

    @Before
    public void setUp() {
        linkedListProblems = new LinkedListProblems();
    }

    @Test
    public void testLength0() {
        LinkedListNode head = null;
        assertEquals("Length should be 0", 0, linkedListProblems.length(head));
    }

    @Test
    public void testLength1() {
        LinkedListNode head = new LinkedListNode(2);
        assertEquals("Length should be 1", 1, linkedListProblems.length(head));
    }

    @Test
    public void testLength2() {
        LinkedListNode head = new LinkedListNode(2);
        LinkedListNode next = new LinkedListNode(2);
        head.next = next;
        assertEquals("Length should be 2", 2, linkedListProblems.length(head));
    }

    @Test
    public void testLength4() {
        LinkedListNode head = new LinkedListNode(2);
        LinkedListNode next = new LinkedListNode(2);
        LinkedListNode next1 = new LinkedListNode(2);
        LinkedListNode next2 = new LinkedListNode(2);
        head.next = next;
        next.next = next1;
        next1.next = next2;
        assertEquals("Length should be 4", 4, linkedListProblems.length(head));
    }

    @Test
    public void testFastForward() {
        LinkedListNode node = linkedListProblems.fastForward(null, 1);
        assertNull("node should be null", node);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFastForward_IllegalArgumentException() {
        LinkedListNode head = new LinkedListNode(2);
        LinkedListNode next = new LinkedListNode(2);
        LinkedListNode next1 = new LinkedListNode(2);
        LinkedListNode next2 = new LinkedListNode(2);
        head.next = next;
        next.next = next1;
        next1.next = next2;
        LinkedListNode node = linkedListProblems.fastForward(head, 5);
        assertNull("node should be null", node);
    }

    @Test
    public void testFastForward1() {
        LinkedListNode head = new LinkedListNode(1);
        LinkedListNode next = new LinkedListNode(2);
        LinkedListNode next1 = new LinkedListNode(3);
        LinkedListNode next2 = new LinkedListNode(4);

        head.next = next;
        next.next = next1;
        next1.next = next2;

        LinkedListNode node = linkedListProblems.fastForward(head, 1);
        assertEquals("node should be 2", 2, node.val);
        assertEquals("node should be 2", next, node);
    }

    @Test
    public void testFastForward2() {
        LinkedListNode head = new LinkedListNode(1);
        LinkedListNode next = new LinkedListNode(2);
        LinkedListNode next1 = new LinkedListNode(3);
        LinkedListNode next2 = new LinkedListNode(4);

        head.next = next;
        next.next = next1;
        next1.next = next2;

        LinkedListNode node = linkedListProblems.fastForward(head, 2);
        assertEquals("node should be 3", 3, node.val);
        assertEquals("node should be 3", next1, node);
    }

    @Test
    public void testFastForward3() {
        LinkedListNode head = new LinkedListNode(1);
        LinkedListNode next = new LinkedListNode(2);
        LinkedListNode next1 = new LinkedListNode(3);
        LinkedListNode next2 = new LinkedListNode(4);

        head.next = next;
        next.next = next1;
        next1.next = next2;

        LinkedListNode node = linkedListProblems.fastForward(head, 3);
        assertEquals("node should be 4", 4, node.val);
        assertEquals("node should be 4", next2, node);
    }

    @Test
    public void testFastForward4() {
        LinkedListNode head = new LinkedListNode(1);
        LinkedListNode next = new LinkedListNode(2);
        LinkedListNode next1 = new LinkedListNode(3);
        LinkedListNode next2 = new LinkedListNode(4);

        head.next = next;
        next.next = next1;
        next1.next = next2;

        LinkedListNode node = linkedListProblems.fastForward(head, 4);
        assertNull("node should be null", node);
    }
}