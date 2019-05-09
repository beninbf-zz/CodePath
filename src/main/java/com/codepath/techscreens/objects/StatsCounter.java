package main.java.com.codepath.techscreens.objects;

/**
 * Class for Snap tech screen
 */
public class StatsCounter {
    private MaxHeap maxHeap = null;
    public StatsCounter(){
        this.maxHeap = new MaxHeap();
    }

    public void register(Message message) {
        this.maxHeap.addMessage(message);
    }

    public long getMax() {
        Message maxMessage = this.maxHeap.extractMax();
        return maxMessage.getValue();
    }
}
