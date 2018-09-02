package main.java.com.codepath.objects;

public class TreeNode<T> {
    private T value;
    private TreeNode<T> left;
    private TreeNode<T> right;

    public TreeNode() {}

    public TreeNode(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public TreeNode(T value, TreeNode<T> left, TreeNode<T> right) {
        this.left = left;
        this.right = right;
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
    public TreeNode<T> getLeft() {
        return left;
    }

    /**
     * Sets left node.
     *
     * @param left the left
     */
    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    /**
     * Gets right node.
     *
     * @return the right
     */
    public TreeNode<T> getRight() {
        return right;
    }

    /**
     * Sets right node.
     *
     * @param right the right
     */
    public void setRight(TreeNode<T> right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
            "value=" + value +
            '}';
    }
}
