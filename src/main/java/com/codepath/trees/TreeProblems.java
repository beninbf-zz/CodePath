package main.java.com.codepath.trees;

import main.java.com.codepath.objects.TreeNode;

public class TreeProblems {

    public static TreeNode<Integer> previous= null;
    public boolean isBSTMinMax(TreeNode<Integer> root) {
        return isBSTMinMax(root, null, null);
    }

    /**
     * Is bst boolean. The most efficient way to do this problem
     *
     * isBstMinMaxUtil - determines whether or not a binary tree is a binary search tree
     * it does this by knowing that the left node of the root, can at max have a value
     * of root. I.e. if the root = 7, then the left node can at max of a value of 7,
     * and at minimum negative infinity. Likewise the value in the right node of the root,
     * can have a value no less than that of the root (at minimum). i.e. if root is 7 then
     * right node must be 8 or greater.
     * All subtrees must also satisfy this as well
     *
     * @param root the root
     * @param min  the min
     * @param max  the max
     * @return the boolean
     */
    private boolean isBSTMinMax(TreeNode<Integer> root, Integer min, Integer max) {
        if (root == null) return true;

        if (min != null && root.getValue().compareTo(min) < 0) {
            return false;

        }

        if (max != null && root.getValue().compareTo(max) > 0) {
            return false;
        }


        return isBSTMinMax(root.getLeft(), min, root.getValue()) && isBSTMinMax(root.getRight(), root.getValue(), max);
    }


    public boolean isBstInOrder(TreeNode<Integer> root) {
        return isBstInOrderPrevious(root);
    }


    /**
     * Using the inorder traversal, without an array, will ONLY work if our previous
     * node is a static variable, meaning its value will persist throughout the call
     * stack. If we DON'T have the variable declared as static, the previous pointer
     * will change at each call stack, and we won't reliably be comparing the previous
     * value with the current, because the previous pointer will be different with
     * every function call stack.
     *
     * @param root
     * @return
     */
    private boolean isBstInOrderPrevious(TreeNode<Integer> root) {
        if (root == null) return true;

        if (!isBstInOrderPrevious(root.getLeft())) {
            return false;
        }

        if (previous != null && previous.getValue().compareTo(root.getValue()) > 0) {
            return false;
        }
        previous = root;

        if(!isBstInOrderPrevious(root.getRight())) {
            return false;
        }
        return true;
    }

    public int findMin(TreeNode<Integer> root) {
        if (root.getLeft() == null) {
            return root.getValue();
        }

        return findMin(root.getLeft());
    }

    public int findMax(TreeNode<Integer> root) {
        if (root.getRight() == null) {
            return root.getValue();
        }

        return findMin(root.getRight());
    }

    public int findHeight(TreeNode<Integer> root) {
        if (root == null) {
            return 0;
        }

        int left = findHeight(root.getLeft());
        int right = findHeight(root.getRight());

        return Math.max(left, right) + 1;
    }

}
