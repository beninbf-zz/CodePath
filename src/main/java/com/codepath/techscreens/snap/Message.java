package main.java.com.codepath.techscreens.snap;

/**
 * Class for Snap tech screen
 */
public class Message implements Comparable<Message> {
    private final Long timestamp;
    private final Long value;

    public Message(long ts, long v) {
        this.timestamp = ts;
        this.value = v;
    }

    public long getTimeStamp() { return timestamp; }

    public long getValue() { return value; }

    public int compareTo(Message message) {
        if (this.value < message.getValue()) {
            return -1;
        } else if (this.value > message.getValue()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return String.format("timestamp: %s, value: %s", this.timestamp, this.value);
    }

    @Override
    public int hashCode() {
        return this.timestamp.hashCode() + value.hashCode();
    }

    @Override
    public boolean equals(Object other) {

        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Message otherMessage = (Message) other;

        return this.timestamp.equals(otherMessage.getTimeStamp()) && this.value.equals(otherMessage.getValue());
    }
}
