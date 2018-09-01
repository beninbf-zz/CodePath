package main.java.com.codepath.objects;

public class BTreeNode<Key extends Comparable<Key>, Value> {

    private Key key;

    private Value value;

    private BTreeNode<Key, Value> left;

    private BTreeNode<Key, Value> right;

    BTreeNode(Key key, Value value) {
        this.key = key;
        this.value = value;

        this.left = null;
        this.right = null;
    }


}
