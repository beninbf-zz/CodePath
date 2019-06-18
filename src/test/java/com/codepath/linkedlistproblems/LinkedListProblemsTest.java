package test.java.com.codepath.linkedlistproblems;

import main.java.com.codepath.linkedlistproblems.LinkedListProblems;
import main.java.com.codepath.objects.LinkedListNode;
import main.java.com.codepath.objects.LinkedListNodeTuple;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * LinkedListProblems Unit tests
 */
public class LinkedListProblemsTest {

    private LinkedListProblems testObj;

    @Before
    public void setUp() {
        testObj = new LinkedListProblems();
    }

    @Test
    public void testLength0() {
        LinkedListNode head = null;
        assertEquals("Length should be 0", 0, testObj.length(head));
    }

    @Test
    public void testLength1() {
        LinkedListNode head = new LinkedListNode(2);
        assertEquals("Length should be 1", 1, testObj.length(head));
    }

    @Test
    public void testLength2() {
        LinkedListNode head = new LinkedListNode(2);
        LinkedListNode next = new LinkedListNode(2);
        head.next = next;
        assertEquals("Length should be 2", 2, testObj.length(head));
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
        assertEquals("Length should be 4", 4, testObj.length(head));
    }

    @Test
    public void testFastForward() {
        LinkedListNode node = testObj.fastForward(null, 1);
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
        LinkedListNode node = testObj.fastForward(head, 5);
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

        LinkedListNode node = testObj.fastForward(head, 1);
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

        LinkedListNode node = testObj.fastForward(head, 2);
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

        LinkedListNode node = testObj.fastForward(head, 3);
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

        LinkedListNode node = testObj.fastForward(head, 4);
        assertNull("node should be null", node);
    }

    @Test
    public void testCycleDetection() {
        LinkedListNode head = getCycleLinkedList();
        assertTrue("should be a cycle", testObj.detectCycle(head));

        LinkedListNode otherHead = getCycleLinkedList();
        assertTrue("should be a cycle", testObj.detectCycleEff(otherHead));
    }

    @Test
    public void testCycleDetection1() {
        LinkedListNode head = getLinkedList();
        assertFalse("should not be a cycle", testObj.detectCycle(head));

        LinkedListNode otherHead = getLinkedList();
        assertFalse("should not be a cycle", testObj.detectCycleEff(otherHead));
    }

    @Test
    public void testDutchSort() {
        LinkedListNode one = new LinkedListNode(1);
        LinkedListNode two = new LinkedListNode(2);
        LinkedListNode three = new LinkedListNode(3);
        LinkedListNode four = new LinkedListNode(4);
        LinkedListNode anotherFour = new LinkedListNode(4);
        LinkedListNode five = new LinkedListNode(5);
        LinkedListNode six = new LinkedListNode(6);
        LinkedListNode seven = new LinkedListNode(7);

        seven.next = two;
        two.next = five;
        five.next = three;
        three.next = six;
        six.next = four;
        four.next = one;
        one.next = anotherFour;

        print(seven);

        LinkedListNode head = testObj.dutchSort(4, seven);

        print(head);
    }

    @Test
    public void testFlattenLinkedList() {
        LinkedListNode head = getUnflattenedList();
        LinkedListNode newHead = testObj.flattenWithBFS(head);
        print(newHead);

        LinkedListNode anotherHead = getUnflattenedList();
        LinkedListNode anotherNewHead = testObj.flattenWithBFS(anotherHead);
        print(anotherNewHead);
    }

    private void print(LinkedListNode head) {
        LinkedListNode current = head;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }


    private LinkedListNode getCycleLinkedList() {
        LinkedListNode one = new LinkedListNode(1);
        LinkedListNode two = new LinkedListNode(2);
        LinkedListNode three = new LinkedListNode(3);
        LinkedListNode four = new LinkedListNode(4);

        one.next = two;
        two.next = three;
        three.next = four;
        four.next = two;
        return one;
    }

    private LinkedListNode getLinkedList() {
        LinkedListNode one = new LinkedListNode(1);
        LinkedListNode two = new LinkedListNode(2);
        LinkedListNode three = new LinkedListNode(3);
        LinkedListNode four = new LinkedListNode(4);

        one.next = two;
        two.next = three;
        three.next = four;
        return one;
    }

    private LinkedListNode getUnflattenedList() {

        LinkedListNode one = new LinkedListNode(1);
        LinkedListNode two = new LinkedListNode(2);
        LinkedListNode three = new LinkedListNode(3);
        LinkedListNode four = new LinkedListNode(4);
        LinkedListNode five = new LinkedListNode(5);
        LinkedListNode six = new LinkedListNode(6);
        LinkedListNode seven = new LinkedListNode(7);
        LinkedListNode eight = new LinkedListNode(8);
        LinkedListNode nine = new LinkedListNode(9);
        LinkedListNode ten = new LinkedListNode(10);
        LinkedListNode eleven = new LinkedListNode(11);
        LinkedListNode twelve = new LinkedListNode(12);
        LinkedListNode thirteen = new LinkedListNode(13);

        one.next = two;
        two.next = three;
        two.child = five;
        three.next = four;
        four.child = seven;

        five.next = six;
        five.child = ten;

        seven.next = eight;
        seven.child = eleven;
        eight.next = nine;
        eight.child = twelve;
        twelve.next = thirteen;

        return one;

    }

    @Test
    public void testMergeLinkedList() {
        LinkedListNode six = new LinkedListNode(6);
        LinkedListNode nine = new LinkedListNode(9);
        LinkedListNode four = new LinkedListNode(4);
        LinkedListNode eight = new LinkedListNode(8);

        six.next = nine;
        nine.next = four;
        four.next = eight;

        LinkedListNode previous = new LinkedListNode(-1);
        LinkedListNode rightEnd = new LinkedListNode(-2);
        eight.next = rightEnd;

        LinkedListNodeTuple tuple = testObj.merge(previous, six, four, rightEnd);
        print(tuple.head);
    }

    @Test
    public void testMergeLinkedList1() {
        LinkedListNode six = new LinkedListNode(6);
        LinkedListNode nine = new LinkedListNode(9);
        LinkedListNode four = new LinkedListNode(4);
        LinkedListNode five = new LinkedListNode(5);

        six.next = nine;
        nine.next = four;
        four.next = five;

        LinkedListNode previous = new LinkedListNode(-1);
        LinkedListNode rightEnd = new LinkedListNode(-2);

        five.next = rightEnd;

        LinkedListNodeTuple tuple = testObj.merge(previous, six, four, rightEnd);

        print(tuple.head);
    }

    @Test
    public void testMergeLinkedList2() {
        LinkedListNode six = new LinkedListNode(6);
        LinkedListNode nine = new LinkedListNode(9);
        six.next = nine;
        LinkedListNode previous = new LinkedListNode(-1);
        LinkedListNode rightEnd = new LinkedListNode(-2);
        nine.next = rightEnd;
        LinkedListNodeTuple tuple = testObj.merge(previous, six, nine, rightEnd);
        print(tuple.head);
    }

    @Test
    public void testMergeLinkedList3() {
        LinkedListNode six = new LinkedListNode(6);
        LinkedListNode nine = new LinkedListNode(9);
        LinkedListNode nineteen = new LinkedListNode(19);
        LinkedListNode twenty = new LinkedListNode(20);


        six.next = nine;
        nine.next = nineteen;
        nineteen.next = twenty;

        LinkedListNode one = new LinkedListNode(1);
        LinkedListNode two = new LinkedListNode(2);
        LinkedListNode three = new LinkedListNode(3);
        LinkedListNode four = new LinkedListNode(4);

        twenty.next = one;
        one.next = two;
        two.next = three;
        three.next = four;

        LinkedListNode previous = new LinkedListNode(-1);
        LinkedListNode rightEnd = new LinkedListNode(-2);
        four.next = rightEnd;
        print(six);

        LinkedListNodeTuple tuple = testObj.merge(previous, six, one, rightEnd);

        print(tuple.endOfPriorList);
    }

    @Test
    public void testSort() {
        LinkedListNode six = new LinkedListNode(6);
        LinkedListNode nine = new LinkedListNode(9);
        LinkedListNode nineteen = new LinkedListNode(19);
        LinkedListNode twenty = new LinkedListNode(20);

        six.next = nine;
        nine.next = nineteen;
        nineteen.next = twenty;
        LinkedListNode one = new LinkedListNode(1);
        LinkedListNode two = new LinkedListNode(2);
        LinkedListNode three = new LinkedListNode(3);
        LinkedListNode four = new LinkedListNode(4);

        twenty.next = one;
        one.next = two;
        two.next = three;
        three.next = four;

        LinkedListNode head = testObj.sort(six);
        print(head);
    }

    @Test
    public void testSort1() {
        LinkedListNode one = new LinkedListNode(1);
        LinkedListNode two = new LinkedListNode(2);
        LinkedListNode three = new LinkedListNode(3);
        LinkedListNode four = new LinkedListNode(4);
        LinkedListNode five = new LinkedListNode(5);

        one.next = two;
        two.next = three;
        three.next = four;
        four.next = five;

        LinkedListNode head = testObj.sort(one);
        print(head);
    }

    @Test
    public void testSort2() {
        LinkedListNode four = new LinkedListNode(4);
        LinkedListNode one = new LinkedListNode(1);
        LinkedListNode five = new LinkedListNode(5);
        LinkedListNode three = new LinkedListNode(3);
        LinkedListNode two = new LinkedListNode(2);

        four.next = one;
        one.next = five;
        five.next = three;
        three.next = two;

        System.out.print("original list: ");
        print(four);
        LinkedListNode head = testObj.sort(four);
        System.out.print("output of sort: ");
        print(head);
    }

    @Test
    public void testSort3() {
        LinkedListNode five = new LinkedListNode(5);
        LinkedListNode four = new LinkedListNode(4);
        LinkedListNode three = new LinkedListNode(3);
        LinkedListNode two = new LinkedListNode(2);
        LinkedListNode one = new LinkedListNode(1);

        five.next = four;
        four.next = three;
        three.next = two;
        two.next = one;

        System.out.print("original list: ");
        print(five);
        LinkedListNode head = testObj.sort(five);
        System.out.print("output of sort: ");
        print(head);
    }

    @Test
    public void testSort4() {
        LinkedListNode six = new LinkedListNode(6);
        LinkedListNode nine = new LinkedListNode(9);

        six.next = nine;
        LinkedListNode head = testObj.sort(six);
        print(head);
    }

    @Test
    public void testSort5() {
        LinkedListNode nine = new LinkedListNode(9);
        LinkedListNode six = new LinkedListNode(6);

        nine.next = six;
        LinkedListNode head = testObj.sort(nine);
        print(head);
    }

    @Test
    public void testSort6() {
        LinkedListNode nine = new LinkedListNode(9);
        LinkedListNode six = new LinkedListNode(-6);

        nine.next = six;
        LinkedListNode head = testObj.sort(nine);
        print(head);
    }
}