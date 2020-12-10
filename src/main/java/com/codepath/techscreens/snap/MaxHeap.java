package main.java.com.codepath.techscreens.snap;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Class for Snap tech screen
 */
public class MaxHeap {
    private List<Message> messageList = null;
    private PriorityQueue<Message> maxHeap = null;
    final static long MIN = 60000;

    public MaxHeap() {
        Comparator<Message> cmp = new Comparator<Message>() {
            @Override
            public int compare(Message message1, Message message2) {
                if (message1.getValue() < message2.getValue()) {
                    return 1;
                } else if (message1.getValue() > message2.getValue()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        this.maxHeap = new PriorityQueue(100, cmp);
        this.messageList = new LinkedList<>();
    }

    public void addMessage(Message message) {
        while (this.messageList.size() > 0) {
            Message oldestMessage = this.messageList.get(0);
            if (message.getTimeStamp() - oldestMessage.getTimeStamp() > 10 * MIN) {
                this.messageList.remove(0);
                this.maxHeap.remove(oldestMessage);
            } else {
                break;
            }
        }

        this.messageList.add(message);
        this.maxHeap.add(message);
    }

    public Message extractMax() {
        return this.maxHeap.peek();
    }

    public boolean isEmpty() {
        return this.maxHeap.isEmpty();
    }
}