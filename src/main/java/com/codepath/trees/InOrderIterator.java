package main.java.com.codepath.trees;

import main.java.com.codepath.objects.TreeNode;

import java.util.Stack;

public class InOrderIterator {

    Stack<TreeNode> stack = new Stack<>();

    public InOrderIterator(TreeNode root) {
        TreeNode current = root;
        while (current != null) {
            stack.push(current);
            current = current.getLeft();
        }
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public TreeNode next() {
        TreeNode current = null;
        while(hasNext()) {
            current = stack.pop();
            if (current.getRight() != null) {
                TreeNode node = current.getRight();
                while(node != null) {
                    stack.push(node);
                    node = node.getLeft();
                }
                return current;
            } else {
                return current;
            }
        }
        return current;
    }
}
