package main.java.com.codepath.objects;

import java.util.Vector;

public class NTreeNode<T> {
    private T value;
    private Vector<NTreeNode<T>> children;

    public NTreeNode() {}

    public NTreeNode(T value) {
        this.value = value;
        this.children = new Vector<>();
    }

    public NTreeNode(T value, Vector<NTreeNode<T>> children) {
        this.value = value;
        this.children = children;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Gets left node.
     *
     * @return the left node
     */
    public Vector<NTreeNode<T>> getChildren() {
        return this.children;
    }

    /**
     * Sets the children.
     *
     * @param children a vector of child tree nodes
     */
    public void setChildren(Vector<NTreeNode<T>> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
            "value=" + value +
            '}';
    }
}
