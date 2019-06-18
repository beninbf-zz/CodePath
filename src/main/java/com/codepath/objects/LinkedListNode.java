package main.java.com.codepath.objects;

/**
 * The type LinkedListNode.
 */
public class LinkedListNode {

    /**
     * The value stored at the node.
     */
    public int val;

    /**
     * The Next.
     */
    public LinkedListNode next;

    /**
     * Generic label for the node.
     */
    public String label;

    /**
     * Optional child node;
     */
    public LinkedListNode child;

    /**
     * Instantiates a new List node.
     *
     * @param x the x
     */
    public LinkedListNode(int x) {
        val = x; next = null;
    }

    /**
     * Sets the label for this node.
     *
     * @param label
     */
    public LinkedListNode(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("node{val=%s}", val);
    }
}
