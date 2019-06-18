package main.java.com.codepath.objects;

public class LinkedListNodeTuple {

    public LinkedListNode head;
    public LinkedListNode endOfPriorList;

    public LinkedListNodeTuple(LinkedListNode head, LinkedListNode endOfPriorList) {
        this.head = head;
        this.endOfPriorList = endOfPriorList;
    }

    @Override
    public String toString() {
        return String.format("tuple{head:%s, endOfPriorList:%s}", head, endOfPriorList);
    }
}
