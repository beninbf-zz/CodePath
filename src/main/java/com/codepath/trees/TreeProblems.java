package main.java.com.codepath.trees;

import main.java.com.codepath.objects.NTreeNode;
import main.java.com.codepath.objects.TreeNode;
import main.java.com.codepath.util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class TreeProblems {
    public boolean isBSTMinMax(TreeNode<Integer> root) {
        return isBSTMinMax(root, null, null);
    }

    /**
     * Is bst boolean. The most efficient way to do this problem
     * <p>
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

    public boolean isBstInOrderPrevious(TreeNode<Integer> root) {
        TreeNode<Integer>[] previous = new TreeNode[1];
        return isBstInOrderPreviousHelper(root, previous);
    }


    /**
     * isBstInOrderPreviousHelper - In this algorithm we are determining if a tree is
     * actually an Binary Search Tree, by keeping track of the previous node. Previously,
     * I did the algorithm in correctly, by trying to pass the previous node from
     * call stack to call, declaring the "root" as the previous node, as one tried to recurse.
     * <p>
     * The meaning of the "previous" node, in my first in correct approach, was literally taking
     * "previous" as the previous node in the call stack. This is a mistake, because the "previous"
     * node in the call stack, or the root at each call stack, is not the actual in order predecessor.
     * <p>
     * We I actually should have done, is keep track of the in order predecessor of every node that
     * I visit. To Do this, I need to declare the previous node as static, so that i can keep track of
     * it through recursive calls. I don't want the previous node changing values to null, AFTER it is
     * set and moves back through call stack frames. Once it is set, I want to reset the previous, or
     * in order sucessor node, only after returning from the left node in the recursion.
     * <p>
     * In this way, once I actually make way back up the tree, through the call stack, to the actual
     * root of the tree, the in order predecessor is actually the right most child from the left
     * subtree. Which is the same as saying its the max of the left subtree. At this point, I simply
     * check to see if this in order predecessor is greater than the current root. If it is, I will
     * return false.
     *
     * @param root
     * @return
     */

    private boolean isBstInOrderPreviousHelper(TreeNode<Integer> root, TreeNode<Integer>[] previous) {
        if (root == null) return true;

        if (!isBstInOrderPreviousHelper(root.getLeft(), previous)) {
            return false;
        }

        if (previous[0] != null && previous[0].getValue().compareTo(root.getValue()) > 0) {
            return false;
        }
        previous[0] = root;

        if (!isBstInOrderPreviousHelper(root.getRight(), previous)) {
            return false;
        }
        return true;
    }

    public boolean isBstInOrder1(TreeNode<Integer> root) {
        return isBstInOrderPrevious1(root, null);
    }


    /**
     * isBstInOrderPrevious1 - This approach is INCORRECT because, all it is actually doing,
     * is checking that each individual subtree is a BST. Every subtree within a BST could
     * be a BST, but that doesn't mean the entire tree is a bst. Look at the following sub tree
     * <p>
     * 50
     * /   \
     * /     \
     * 40      60
     * /   \    /  \
     * /    \   /    \
     * 30    47 42    65
     * <p>
     * You can see that each individual sub tree is a BST, but this still violates the definition
     * of a BST because the in order successor of the root is less than the root. The grandchild
     * of the root must still satisfy the BST property.
     *
     * @param root
     * @return
     */

    private boolean isBstInOrderPrevious1(TreeNode<Integer> root, TreeNode<Integer> previousNode) {
        if (root == null) return true;

        TreeNode<Integer> leftChild = root.getLeft();
        if (!isBstInOrderPrevious1(leftChild, root)) {
            return false;
        }

        TreeNode<Integer> rightChild = root.getLeft();
        if (!isBstInOrderPrevious1(rightChild, root)) {
            return false;
        }

        if (root != null && leftChild != null && root.getValue().compareTo(leftChild.getValue()) < 0) {
            return false;
        }

        if (root != null && rightChild != null && root.getValue().compareTo(rightChild.getValue()) > 0) {
            return false;
        }
        return true;
    }

    /* isBstInOrderList - Will correctly determine if a BT is a BST, but it does
     *  so in a memory intensive way.
     *
     *  It first traverses through the tree. run time = O(n) because it has to visit each node.
     *  Because it does so recursively the call stack is then grows to O(n) for space complexity.
     *  (if its a balanced BST then that running time would be O(h) where h is the height of the tree
     *  or (log n).
     *  It then returns the list, which has a space complexity of O(n) to store all of the nodes.
     *  Then iterates through the list, which is a running time of O(n) to visit each node, checking
     *  if all the nodes are in order.
     *
     *  Although the running time is still O(n) and the complexity is O(n), it has O(n) twice
     *  and for running time because of the call stack, and then iterating through the list.
     *  The space complexity is O(2 * n) twice with the call stack and the list. This can be
     *  improved upon.
     *
     */
    public boolean isBstInOrderList(TreeNode<Integer> root) {
        ArrayList<TreeNode<Integer>> list = new ArrayList<>();
        isBstInOrderList(root, list);
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getValue() > list.get(i + 1).getValue()) {
                return false;
            }
        }
        return true;
    }

    private void isBstInOrderList(TreeNode<Integer> root, ArrayList<TreeNode<Integer>> list) {
        if (root == null) return;
        isBstInOrderList(root.getLeft(), list);
        list.add(root);
        isBstInOrderList(root.getRight(), list);
    }

    public int findMin(TreeNode<Integer> root) {
        if (root == null) {
            return Integer.MAX_VALUE;
        }
        if (root.getLeft() == null) {
            return root.getValue();
        }

        return findMin(root.getLeft());
    }

    public int findMax(TreeNode<Integer> root) {
        if (root == null) {
            return Integer.MIN_VALUE;
        }
        if (root.getRight() == null) {
            return root.getValue();
        }

        return findMin(root.getRight());
    }

    public TreeNode<Integer> findMaxNode(TreeNode<Integer> root) {
        if (root == null) {
            return null;
        }
        if (root.getRight() == null) {
            return root;
        }

        return findMaxNode(root.getRight());
    }

    public int findHeight(TreeNode<Integer> root) {
        if (root == null) {
            return 0;
        }

        int left = findHeight(root.getLeft());
        int right = findHeight(root.getRight());

        return Math.max(left, right) + 1;
    }

    public int countLeafNodes(TreeNode<Integer> root) {
        if (root == null) {
            return 0;
        }

        if (root.getLeft() == null && root.getRight() == null) {
            return 1;
        } else {
            return countLeafNodes(root.getLeft()) + countLeafNodes(root.getRight());
        }
    }

    public boolean compareTrees(TreeNode<Integer> root, TreeNode<Integer> other) {

        if (root == null && other == null) {
            return true;
        }

        if (root == null || other == null) {
            return false;
        }

        if (root.getValue().compareTo(other.getValue()) != 0) {
            return false;
        }

        return compareTrees(root.getLeft(), other.getLeft()) && compareTrees(root.getRight(), other.getRight());
    }

    class TreeCell {
        TreeNode<Integer> node;

        TreeCell parent;

        int sum;

        TreeCell(TreeNode node, int sum) {
            this.node = node;
            this.sum = sum;
        }

        public String toString() {
            String parentValue = (parent != null) ? parent.node.getValue().toString() : "";
            return node.toString() + " parent: " + parentValue + " :sum=" + sum;
        }

        public String getPathRepresentation() {
            return node.getValue().toString();
        }
    }

    ;

    /**
     * This is better done by using Depth First Search, but this is never the less a good
     * exercise for understanding Breadth First Search. The struggle with Breadth First search
     * is preserving the path as we move from node to node. We accomplish this by creating a
     * new Object, that stores the TreeNode and the accumulated sum along the path up to that
     * node. With this new object, that we call TreeCell, we can therefore accumulate the sum
     * up until that node. Every time we encounter a node, we create a new TreeCell node, and we store
     * the value for the accumulated sum, which is composed of roots accumulated value, and the current
     * value of the node. By doing this, we can accurately check that a given node is correctly holding
     * the sum from the nodes that came before in a path in a tree.
     *
     * @param root   tree node
     * @param target target to sum to;
     * @return
     */
    public boolean pathSumWithBfs(TreeNode<Integer> root, int target) {

        Queue<TreeCell> queue = new LinkedList<>();
        queue.add(new TreeCell(root, root.getValue()));

        while (!queue.isEmpty()) {
            TreeCell treeCell = queue.poll();

            if (treeCell.node.getLeft() == null && treeCell.node.getRight() == null && treeCell.sum == target) {
                return true;
            }

            if (treeCell.node.getLeft() != null) {
                TreeCell leftTreeCell = new TreeCell(treeCell.node.getLeft(), treeCell.sum + treeCell.node.getLeft().getValue());
                queue.add(leftTreeCell);

            }

            if (treeCell.node.getRight() != null) {
                TreeCell rightTreeCell = new TreeCell(treeCell.node.getRight(), treeCell.sum + treeCell.node.getRight().getValue());
                queue.add(rightTreeCell);
            }
        }
        return false;
    }

    public boolean pathSumWithDfs(TreeNode<Integer> root, int target) {
        if (root == null) {
            return false;
        }

        int result = target - root.getValue();
        if (root.getLeft() == null && root.getRight() == null && result == 0) {
            return true;
        } else {
            return pathSumWithDfs(root.getLeft(), target - root.getValue())
                || pathSumWithDfs(root.getRight(), target - root.getValue());
        }
    }

    public ArrayList<ArrayList<TreeNode<Integer>>> findPathsThatSumWithDfs(TreeNode<Integer> root, int target) {

        ArrayList<ArrayList<TreeNode<Integer>>> output = new ArrayList<>();
        ArrayList<TreeNode<Integer>> candidate = new ArrayList<>();

        findPathsThatSumWithDfs(root, target, output, candidate);
        return output;
    }


    public void findPathsThatSumWithDfs(TreeNode<Integer> root, int target, ArrayList<ArrayList<TreeNode<Integer>>> output, ArrayList<TreeNode<Integer>> candidate) {

        if (root == null) {
            return;
        }

        candidate.add(root);
        int result = target - root.getValue(); // if result 0, then we need to check if we are at leaf node

        // If we are at a leaf node then both children are null
        // two conditions for removing a node from candidate
        // 1st we are at a leaf node and target - leaf.value isn't 0
        // 2nd is when we are not at leaf node, and the target - leaf.value  <= 0

        boolean isLeafNode = root.getLeft() == null && root.getRight() == null;

        if (isLeafNode && (result == 0)) {
            output.add((ArrayList<TreeNode<Integer>>) candidate.clone()); // we want a copy added to output
        } else if (isLeafNode && (result != 0)) {
            candidate.remove(candidate.size() - 1);
        } else if (!isLeafNode && (result <= 0)) {
            candidate.remove(candidate.size() - 1);
        }

        findPathsThatSumWithDfs(root.getLeft(), result, output, candidate);
        /**
         * this happens after you have explored the left and are at the root
         */
        findPathsThatSumWithDfs(root.getRight(), result, output, candidate);

        /**
         * After exploring the right, there is no more tree, so any code after the line above
         * is what your code executes as it moves back up the tree.
         */
        int removeRoot = candidate.indexOf(root);
        if (removeRoot > 0) {
            candidate.remove(removeRoot);
        }
    }

    public ArrayList<ArrayList<TreeCell>> findPathsThatSumWithBFS(TreeNode<Integer> root, int target) {
        ArrayList<TreeCell> paths = new ArrayList<>();
        ArrayList<ArrayList<TreeCell>> output = new ArrayList<>();
        findPathsThatSumWithBFS(root, target, paths);

        for (TreeCell cell : paths) {
            ArrayList<TreeCell> path = new ArrayList<>();
            path.add(cell);
            while (cell.parent != null) {
                path.add(cell.parent);
                cell = cell.parent;
            }
            output.add(path);
        }
        return output;
    }

    public void findPathsThatSumWithBFS(TreeNode<Integer> root, int target, ArrayList<TreeCell> paths) {
        Queue<TreeCell> queue = new LinkedList<>();
        queue.add(new TreeCell(root, root.getValue()));

        while (!queue.isEmpty()) {
            TreeCell treeCell = queue.poll();

            boolean isLeafNode = treeCell.node.getLeft() == null && treeCell.node.getRight() == null;

            if (isLeafNode && treeCell.sum == target) {
                paths.add(treeCell);
            }

            if (treeCell.node.getLeft() != null) {
                TreeCell leftTreeCell = new TreeCell(treeCell.node.getLeft(), treeCell.sum + treeCell.node.getLeft().getValue());
                leftTreeCell.parent = treeCell;
                queue.add(leftTreeCell);
            }

            if (treeCell.node.getRight() != null) {
                TreeCell rightTreeCell = new TreeCell(treeCell.node.getRight(), treeCell.sum + treeCell.node.getRight().getValue());
                rightTreeCell.parent = treeCell;
                queue.add(rightTreeCell);
            }
        }
    }

    public int findLongestPath(TreeNode<Integer> root) {
        int[] maxLength = new int[1];
        findLongestPathUtil(root, maxLength);
        return maxLength[0];
    }

    public int findLongestPathUtil(TreeNode<Integer> root, int[] maxLength) {
        if (root == null) {
            return 0;
        }

        int left = findLongestPathUtil(root.getLeft(), maxLength);
        int right = findLongestPathUtil(root.getRight(), maxLength);

        int accumLeft = 0;
        int accumRight = 0;
        if (root.getLeft() != null && root.getLeft().getValue() == root.getValue()) {
            accumLeft = accumLeft + left + 1;
        }

        if (root.getRight() != null && root.getRight().getValue() == root.getValue()) {
            accumRight = accumRight + right + 1;
        }
        maxLength[0] = Math.max(maxLength[0], accumLeft + accumRight);
        return Math.max(accumLeft, accumRight);
    }


    int ans;

    public int leetCodeSolution(TreeNode<Integer> root) {
        ans = 0;
        arrowLength(root);
        return ans;
    }

    public int arrowLength(TreeNode<Integer> node) {
        if (node == null) return 0;
        int left = arrowLength(node.getLeft());
        int right = arrowLength(node.getRight());
        int arrowLeft = 0, arrowRight = 0;
        if (node.getLeft() != null && node.getLeft().getValue() == node.getValue()) {
            arrowLeft += left + 1;
        }
        if (node.getRight() != null && node.getRight().getValue() == node.getValue()) {
            arrowRight += right + 1;
        }
        ans = Math.max(ans, arrowLeft + arrowRight);
        return Math.max(arrowLeft, arrowRight);
    }

    /**
     * findSingleValueTrees
     *
     * @param n
     * @return
     */
    public int findSingleValueTrees(TreeNode n) {
        int[] count = new int[1];
        findSingleValueTreesHelper(n, count);
        return count[0];
    }


    /**
     * The algorithm outlined below is the incorrect solution, but provides a very good lesson regarding
     * traversing to through a tree.
     * <p>
     * For the unival subtree problem, the goal is to count all unival subtrees. A subtree is considered
     * a unival tree if all of the nodes in the tree, rooted at the "root" all share the same value.
     * <p>
     * By default, this means that the leaf nodes, whose left and right children are null, and only have one
     * node are therefore unival trees.
     * <p>
     * This also means that a tree with only one child, where the root and child match, is also a unival tree.
     * <p>
     * The algorithm below, goes wrong because it considers any tree, where the root, right, and left child's
     * match a unival tree...this doesn't necessarily constitute a unival subtree. Look at the example below
     * <p>
     * 6 <---- root
     * /     \
     * 6*      6
     * /   \    / \
     * 5    5   5   5
     * <p>
     * What are the number of unival sub trees in this tree? Its not 5! Its 4. Why is that?
     * <p>
     * Well, all of the leaf nodes, as explained earlier represent unival subtrees. Why? Because
     * the subtrees starting at the node 5, only have one node, so they are by definition unival trees.
     * <p>
     * What about the subtree rooted at 6*? Is it a unival tree? It clearly is not, because its value of 6
     * is not equal to its right and left node, which are both 5.
     * <p>
     * Now the important question is, is the tree root, 6, representative of a unival tree? No it is not!
     * Why? Well, the subtree of the root, is the entire tree itself. For that subtree, rooted at the "root"
     * of the tree to be counted as a unival tree, all* of the nodes below it would have to be of value 6,
     * not simply the left and right child of 6.
     * <p>
     * Another way of thinking about asking if a tree is a unival tree, is asking the following. Take
     * the value of the root of the tree. Now, does every node in its sub tree, have the same value?
     * <p>
     * If the answer is yes, then its a unival tree.
     * <p>
     * Example
     * <p>
     * 5&
     * /  \
     * 5^   5&
     * / \
     * 4   4
     * <p>
     * Ask your self, is the tree rooted at 5& a unival tree? Well, does every node in that tree have the value 5?
     * No it does not, so its not a unival tree.
     * <p>
     * What about the tree rooted at either one of the leaf nodes 4. Are those unival trees? Yes they are,
     * because all of the nodes in those trees (the trees rooted at 4) all have the same value of 4.
     * <p>
     * When we ask if a tree is a unival tree, then we are asking if, for the value of the root we give as an argument
     * does every node in that tree have the same argument. As such we have to traverse to each and every node in the
     * tree to check.
     * <p>
     * RUNTIME: O(N), where n is the number of nodes in the tree
     * SPACE: O(N), for the calls on stack...it not necessarily a BST, which would have the ordering imposed,
     * so the space would be O(log n). A binary tree could essentially be a link list, so its O(n).
     * <p>
     * The code below is incorrect, because it is not honoring what the actual definition of a unival tree is.
     * <p>
     * For the examples above, it improperly counts anything where the root matches the left and right as a unival
     * tree, that's not correct, because its not checking ALL of the nodes in the right and left subtrees. The approach
     * MUST take into account whether or not left and right subtrees are unival trees, because if a tree is only a
     * unival tree if, its left and right subtrees and unival trees AND its root matches the left and right nodes.
     * <p>
     * The method, isUnival tree is the correct implementation.
     *
     * @param root
     * @param count
     * @return
     */
    public TreeNode findSingleValueTreesHelper(TreeNode<Integer> root, int[] count) {
        if (root == null) {
            return null;
        }

        TreeNode left = findSingleValueTreesHelper(root.getLeft(), count);
        TreeNode right = findSingleValueTreesHelper(root.getRight(), count);

        // leaf node case
        if (left == null && right == null) {
            count[0]++;
        } else if (left != null && right == null) { // one child case, only left child present
            if (left.getValue().equals(root.getValue())) {
                count[0]++;
            }
        } else if (left == null && right != null) { // one child case, only right child present
            if (right.getValue().equals(root.getValue())) {
                count[0]++;
            }
        } else { // two child case
            if (left.getValue().equals(right.getValue()) && right.getValue().equals(root.getValue())) {
                count[0]++;
            }
        }

        return root;
    }

    /**
     * The following implementation is the correct way of checking if a tree is a unival tree,
     * which is another of saying that all nodes of the tree, have the same value as the root.
     * <p>
     * This accomplished by essentially doing a pre-order traversal, visiting nodes in the following fashion
     * <p>
     * root
     * left
     * right
     * <p>
     * RUNTIME: O(N), where n is the number of nodes in the tree
     * SPACE: O(N), for the calls on stack...it not necessarily a BST, which would have the ordering imposed,
     * so the space would be O(log n). A binary tree could essentially be a link list, so its O(n).
     * <p>
     * A full explanation is given above.
     *
     * @param root
     * @return boolean.
     */
    public boolean isUniValTree(TreeNode<Integer> root) {
        if (root == null) {
            return true;
        }
        return isUniValTreeHelper(root, root.getValue());
    }

    private boolean isUniValTreeHelper(TreeNode root, int val) {
        if (root == null) {
            return true;
        }

        boolean isCurrentNodeTheSame = root.getValue().equals(val);
        boolean isLeftTreeUnival = isUniValTreeHelper(root.getLeft(), val);
        boolean isRightTreeUnival = isUniValTreeHelper(root.getRight(), val);
        return isCurrentNodeTheSame && isLeftTreeUnival && isRightTreeUnival;
    }

    /**
     * This implementation is correct however inefficient. It operates in a top
     * down approach. For every node in the tree, was check to see if its a unival tree.
     * <p>
     * If its a node is the root of a unival subtree then we increment our count. We do this
     * for every tree node.
     * <p>
     * The cost of the isUnival method in terms of running time is O(n). If we do that for every
     * node, then thats a total running time of O(n^2).
     *
     * @param root
     * @return
     */
    public int countUnivalTrees(TreeNode<Integer> root) {
        if (root == null) {
            return 0;
        }

        int[] count = new int[1];
        countUnivalTreesHelper(root, count);
        return count[0];
    }

    private void countUnivalTreesHelper(TreeNode<Integer> root, int[] count) {
        if (root == null) {
            return;
        }

        if (isUniValTree(root)) {
            count[0]++;
        }
        ;

        countUnivalTreesHelper(root.getLeft(), count);
        countUnivalTreesHelper(root.getRight(), count);
    }

    /**
     * This method is a much more efficient way to determine the number of unival trees
     * within a tree.
     * <p>
     * Instead of using a top down approach, it uses a bottom up approach. By doing so,
     * as we back track up the call stack, and in turn move back up the tree, we determine
     * if each subtree is a unival tree.
     * <p>
     * From the nature of this problem, we have to remember, that a tree rooted at a given
     * root, is only a unival tree, if its data matches its current left and right node
     * AND its left and root subtrees are ALSO unival trees, OF THE SAME VALUE.
     * <p>
     * The important bit to remember is that for a tree to be a unival tree, the root
     * of that subtree must have left and right subtrees that are unival trees AND
     * the value at the root has to match the left and right subtrees. This property lends itself
     * quite well to traversing through each tree.
     * <p>
     * Because we have to keep track of whether or not the left or right subtrees are unival
     * trees we need to return a boolean.
     * <p>
     * However we also want to keep track of a count, so we can use a global parameter or
     * object for that as a parameter to the function.
     * <p>
     * RUNTIME: O(n) visiting each node in the tree once
     * SPACE: O(n) its a binary tree, not a BST, so it doesn't have to be ordered
     *
     * @param root
     * @return count of unival trees
     */
    public int countUnivalTreeEfficient(TreeNode<Integer> root) {
        if (root == null) {
            return 0;
        }
        int[] count = new int[1];
        countUnivalTreeEfficientHelper(root, count);
        return count[0];
    }

    private boolean countUnivalTreeEfficientHelper(TreeNode<Integer> root, int[] count) {
        if (root == null) {
            return true;
        }

        boolean isLeft = countUnivalTreeEfficientHelper(root.getLeft(), count);
        boolean isRight = countUnivalTreeEfficientHelper(root.getRight(), count);

        /* if either the left or right subtree is not a unival tree, then the subtree rooted at the
         * current node isn't a unival tree either
         */
        if (isLeft == false || isRight == false) {
            return false;
        }

        /* if the left subtree is a unival tree, and its non null, then lets
         * check it against the current root, to see if the root is also a unival tree
         * If it doesn't match the root, then return false, because the current root is
         * not a unival tree.
         */
        if (root.getLeft() != null && !root.getLeft().getValue().equals(root.getValue())) {
            return false;
        }

        /* if the right subtree is a unival tree, and its non null, then lets
         * check it against the current root, to see if the root is also a unival tree
         * If it doesn't match the root, then return false, because the current root is
         * not a unival tree.
         */
        if (root.getRight() != null && !root.getRight().getValue().equals(root.getValue())) {
            return false;
        }

        /**
         * If none of the conditions above are true, then we know then the tree rooted
         * at the current root is a unival tree, so we should then increment the count
         * and return true;
         */
        count[0]++;
        return true;
    }

    /**
     * Below is another implementation of counting unival trees, however the code is much
     * more complicated, given how we are choosing to return true.
     * <p>
     * The implementation returns false for several conditions, and the if none of those
     * conditions is met, the default behavior to return true and increment the count.
     * <p>
     * This method does the opposite. One thing to notice when trying to decide how to return
     * values, is too look at how many times you have to return "true".
     * <p>
     * Making that the default behavior and only returning false in other cases is easier.
     * this is something to pay attention to when coding methods like these.
     *
     * @param root
     * @param count
     * @return
     */
    public boolean countUnivalTreeEfficientHelperOther(TreeNode<Integer> root, int[] count) {
        if (root == null) {
            return true;
        }

        boolean isLeftUnival = countUnivalTreeEfficientHelperOther(root.getLeft(), count);
        boolean isRightUnival = countUnivalTreeEfficientHelperOther(root.getRight(), count);

        if (!isLeftUnival) {
            return false;
        }

        if (!isRightUnival) {
            return false;
        }

        // leaf node case
        if (root.getLeft() == null && root.getRight() == null) {
            count[0]++;
            return true;
        }

        if ((root.getLeft() != null && root.getRight() == null) && root.getLeft().getValue().equals(root.getValue())) {
            count[0]++;
            return true;
        }

        if ((root.getRight() != null && root.getLeft() == null) && root.getRight().getValue().equals(root.getValue())) {
            count[0]++;
            return true;
        }

        if ((root.getLeft() != null && root.getLeft().getValue().equals(root.getValue()))
            && (root.getRight() != null && root.getRight().getValue().equals(root.getValue()))
        ) {
            count[0]++;
            return true;
        }

        return false;
    }


    /**
     * postOrderRecTraversal - classic post order traversal. Visit the children nodes first
     * and then the root.
     * <p>
     * left
     * right
     * root
     *
     * @param root
     */
    public void postOrderRecTraversal(TreeNode<Integer> root) {
        if (root == null) {
            return;
        }
        postOrderRecTraversal(root.getLeft());
        postOrderRecTraversal(root.getRight());
        System.out.print(root.getValue() + " ");
    }

    /**
     * postOrderIterTraversal2Stacks - This is an iterative implementation of the post order traversal
     * of a tree. This will work by using 2 stacks, 1 stack for visited roots, and the other stack for
     * children nodes. We will essentially be using one of the stacks, the "rootStack" to accumulate
     * the nodes of this tree in reverse post order traversal. But putting these nodes in the stack,
     * in reverse post order traversal, then all we have to do is pop each item from the stack
     * and print them (because stacks are Last In First Out).
     * <p>
     * The normal post order traversal is
     * <p>
     * left
     * right
     * root
     * <p>
     * Using the stack object, instead what we will do the exact opposite order
     * <p>
     * root
     * right
     * left
     * <p>
     * as we move nodes into our "rootStack". What this looks like is the following
     * <p>
     * rightChild
     * root               leftChild
     * ---------          --------
     * root stack         child stack
     * <p>
     * we first add our root of the tree to the root stack, we then add its children to the child
     * stack. ****ITS IMPORTANT**** to add the left child to the child stack before the right child,
     * because when the top element is popped off, it will give us our, root-->right-->left reversal
     * that we are looking for.
     * <p>
     * We then, pop the top element off of the child stack, in this case the roots right child,
     * and then we add it to the root stack. We then check the children of the node we just popped,
     * and then add them to the child stack.
     * <p>
     * Again, we pop off of the top node, which if there is a right child present will be whichever roots
     * right child. We keep doing this, and by the time we get to leaf nodes, we will have no more children
     * to add. We keep popping off the top node, add it to the root stack, until our child stack is empty.
     * Each element we pop off of the top, will go on the top of the root stack, in reverse order.
     * <p>
     * When the child stack is empty, we then just pop off each element from the root stack, and print each
     * one.
     *
     * @param root
     */
    public void postOrderIterTraversal2Stacks(TreeNode<Integer> root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode<Integer>> rootStack = new Stack<>();
        Stack<TreeNode<Integer>> childrenStack = new Stack<>();

        childrenStack.push(root);
        while (!childrenStack.isEmpty()) {
            TreeNode<Integer> current = childrenStack.pop();
            if (current.getLeft() != null) {
                childrenStack.push(current.getLeft());
            }

            if (current.getRight() != null) {
                childrenStack.push(current.getRight());
            }
            rootStack.push(current);
        }

        while (!rootStack.isEmpty()) {
            TreeNode<Integer> node = rootStack.pop();
            System.out.print(node.getValue() + " ");
        }
    }

    public void printAllPaths(TreeNode<Integer> root) {
        List<TreeNode<Integer>> path = new ArrayList<>();
        printAllPathsHelper(root, path);
    }

    private void printAllPathsHelper(TreeNode<Integer> root, List<TreeNode<Integer>> path) {
        if (root == null) {
            return;
        }

        path.add(root);
        printAllPathsHelper(root.getLeft(), path);
        printAllPathsHelper(root.getRight(), path);

        // leaf node
        if (root.getLeft() == null && root.getRight() == null) {
            StringBuffer sb = new StringBuffer();
            for (TreeNode<Integer> node : path) {
                sb.append(node.getValue());
                sb.append(" ");
            }
            System.out.println(sb.toString().trim());
        }
        path.remove(path.size() - 1);
    }

    /**
     * Below is the most slick implementation of how to iteratively print a tree in post order.
     * I wouldn't have thought of this, but it is very intuitive.
     * <p>
     * For normal recursion, remember what happens
     * <p>
     * left
     * right
     * root.
     * <p>
     * So this means if the root is null, then stop and do nothing.
     * <p>
     * If the node is a leaf node, then just print the root.
     * <p>
     * If its not a leaf node, then recurse to the left subtree and print
     * Then recurse to the right subtree and print
     * then print the current nodes value
     * <p>
     * Because we are using a stack object, we simply reverse the order
     * <p>
     * root
     * right
     * left.
     * <p>
     * That's the order we add things to the stack in this case.
     * <p>
     * The only slick thing about this implementation, is that we are modifying
     * the left and right pointer of the node, after we add it to the stack.
     * <p>
     * We set the left and right pointer to null, so that when we get to
     * our "base" case, i.e. a leaf node, we print it.
     * <p>
     * Wow.
     * <p>
     * RUNTIME: O(n)
     * SPACE: O(n)
     *
     * @param root
     */
    public void postOrderIterUsingOneStack(TreeNode<Integer> root) {
        if (root == null) {
            return;
        }
        TreeNode<Integer> current = root;
        Stack<TreeNode<Integer>> stack = new Stack<>();
        stack.add(current);

        while (!stack.isEmpty()) {
            TreeNode<Integer> node = stack.peek();
            // its a leaf node
            if (node.getLeft() == null && node.getRight() == null) {
                stack.pop();
                System.out.print(node.getValue() + " ");
            } else {
                if (node.getRight() != null) {
                    stack.push(node.getRight());
                    node.setRight(null);
                }
                if (node.getLeft() != null) {
                    stack.push(node.getLeft());
                    node.setLeft(null);
                }
            }
        }
    }

    /**
     * IsBstInOrderIterStack - The idea for the inOrder traversal using a stack is as follows.
     * If we are doing an in order traversal we must first move all the way to the left in the tree
     * using a stack to store the nodes. Because we are doing this iteratively as opposed to recursiou
     * we will mimic recursion, suing an object stack as opposed to the call stack. The call stack helps
     * because it allows us to remember where we previously were, and then back track. To accomplish
     * backtracking without recursion we will back use of a stack.
     * <p>
     * So, move all they way to the left, adding each node to a stack. When you can't any further left,
     * we must then start back tracking. How do we back track without using recursion, and instead using the
     * object Stack. Well, we start popping nodes off of the stack. When we start doing this, popping our left
     * nodes off of the stack, we have to keep in mind, that to get the next in order successor, we have to look
     * at the right subtree. But we don't simply want the first right child. The immediate right child, is ONLY
     * the next in order successor if the right node has no children. If the right child does indeed have children
     * then we find the left most descendant of the right subtree, for that is the actual next in order successor.
     * <p>
     * Another way of putting that, is once we have popped off a left node, we must find the minimum of the right
     * subtree. The minimum of the right subtree is the next in order successor of our current root. Its also the
     * left most descendant of our right subtree. So min of right subtree == in order successor of root ==
     * left most descendant of our right subtree.
     * <p>
     * Once we find this in order sucessor, we add it to the stack, and then continue popping our "left" most nodes
     * off of the stack, then immediately checking the right child, and then again finding the min of the right subtree
     * OR the left most descendant of our right subtree OR finding the next in order successor.
     *
     * @param root
     * @return
     */
    public boolean IsBstInOrderIterStack(TreeNode<Integer> root) {
        Stack<TreeNode<Integer>> stack = new Stack<TreeNode<Integer>>();

        TreeNode<Integer> current = root;
        // Find the minimum of the left subtree
        while (current != null) {
            stack.push(current);
            current = current.getLeft();
        }

        TreeNode<Integer> previous = null;

        while (!stack.isEmpty()) {
            TreeNode<Integer> node = stack.pop();
            if (previous != null && previous.getValue().compareTo(node.getValue()) > 0) {
                return false;
            }
            previous = node;
            // We must now check the right subtree
            if (node.getRight() != null) {
                current = node.getRight();
                // if the right subtree has children we must find the next in Order successor
                // in other words we must find the minimum of the right subtree
                // another way to say this is that we are finding the left most descendant of
                // right sub tree
                while (current != null) {
                    stack.push(current);
                    current = current.getLeft();
                }
            }
        }

        return true;
    }

    /**
     * This algorithm represents a way to determine if a BST is actually a BST.
     * <p>
     * The definition of a BST is that all node values of the left subtree must be
     * less than or equal to the root. All right node values have to be greater than or equal to the root.
     * <p>
     * All left subtrees and right subtrees must be valid BSTs.
     * <p>
     * A null root is a BST.
     * <p>
     * And a leaf node is a BST.
     * <p>
     * This approach works but is inefficient. We are essentially, verifying for every node of the tree
     * that the BST property is maintained. However we are doing so by checking that the max of the left
     * subtree is <= to the root, and that the min of the Right subtree is >= to the root.
     * <p>
     * This incurs the cost of doing findMin and findMax for every node in this tree. FindMin and FindMax both have
     * run times of O(n). And we of course execute these for every node on the tree so the runtime of the full solution
     * is O(n^2).
     * <p>
     * Space complexity is also O(n) for the call stack
     *
     * @param root
     * @return boolean.
     */
    public boolean isBstInefficient(TreeNode<Integer> root) {
        if (root == null) {
            return true;
        }

        int maxOfLeft = findMax(root.getLeft());
        int minOfRight = findMin(root.getRight());

        boolean isCurrentRootBst = (root.getValue().intValue() >= maxOfLeft && root.getValue().intValue() <= minOfRight);
        boolean isLeftBst = isBstInefficient(root.getLeft());
        boolean isRightBst = isBstInefficient(root.getRight());

        return isCurrentRootBst && isLeftBst && isRightBst;
    }

    /**
     * inOrderRecTraversal - classic in order recursive traversal. Visit the left
     * the root, and then the right.
     * <p>
     * left
     * root
     * right
     *
     * @param root
     */
    public void inOrderRecTraversal(TreeNode<Integer> root) {
        if (root == null) {
            return;
        }

        inOrderRecTraversal(root.getLeft());
        System.out.print(root.getValue() + " ");
        inOrderRecTraversal(root.getRight());
    }

    public TreeNode[] getPostOrderNodes(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        getPostOrderNodesHelper(root, nodes);
        return nodes.toArray(new TreeNode[0]);
    }

    private void getPostOrderNodesHelper(TreeNode root, List<TreeNode> nodes) {
        if (root == null) {
            return;
        }
        getPostOrderNodesHelper(root.getLeft(), nodes);
        getPostOrderNodesHelper(root.getRight(), nodes);
        nodes.add(root);
    }

    public TreeNode[] getPreOrderNodes(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        getPreOrderNodesHelper(root, nodes);
        return nodes.toArray(new TreeNode[0]);
    }

    private void getPreOrderNodesHelper(TreeNode root, List<TreeNode> nodes) {
        if (root == null) {
            return;
        }
        nodes.add(root);
        getPreOrderNodesHelper(root.getLeft(), nodes);
        getPreOrderNodesHelper(root.getRight(), nodes);
    }

    public TreeNode[] getInOrderNodes(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        getInOrderNodesHelper(root, nodes);
        return nodes.toArray(new TreeNode[0]);
    }

    private void getInOrderNodesHelper(TreeNode root, List<TreeNode> nodes) {
        if (root == null) {
            return;
        }

        getInOrderNodesHelper(root.getLeft(), nodes);
        nodes.add(root);
        getInOrderNodesHelper(root.getRight(), nodes);
    }

    public void inOrderRecTraversalChar(TreeNode<Character> root) {
        if (root == null) {
            return;
        }

        inOrderRecTraversalChar(root.getLeft());
        System.out.print(root.getValue() + " ");
        inOrderRecTraversalChar(root.getRight());
    }

    /**
     * InOrder Traversal without using recursion.
     * <p>
     * Just like in the actual recursion of the in order traversal, we should print
     * the node, as soon as it is poped from the track...aka, as soon as we start to back track.
     *
     * @param root
     */
    public void inOrderIterTraversal(TreeNode<Integer> root) {
        Stack<TreeNode<Integer>> stack = new Stack<TreeNode<Integer>>();

        TreeNode<Integer> current = root;
        while (current != null) {
            stack.push(current);
            current = current.getLeft();
        }

        while (!stack.isEmpty()) {
            TreeNode<Integer> node = stack.pop();
            System.out.print(node.getValue() + " ");
            if (node.getRight() != null) {
                current = node.getRight();
                while (current != null) {
                    stack.push(current);
                    current = current.getLeft();
                }
            }
        }
    }

    /**
     * preOrderRecTraversal - Classic pre order traversal. Visit and process the root node
     * then visit the children nodes, usually starting with the left.
     * <p>
     * root
     * left
     * right
     *
     * @param root
     */
    public void preOrderRecTraversal(TreeNode<Integer> root) {
        if (root == null) {
            return;
        }

        System.out.print(root.getValue() + " ");
        preOrderRecTraversal(root.getLeft());
        preOrderRecTraversal(root.getRight());
    }

    /**
     * its nearly identical to in order traversal without recursion algorithm.
     * The only difference is when we choose to print the node.
     * <p>
     * In the actual recursion of the pre order traversal, we should print
     * the node right before we push it on to the stack, i.e. before we start to recurse
     * <p>
     * preOrderIterTraversal - To perform a pre-order traversal iteratively, we need to simulate
     * the call stack that occurs when done recursively. Instead of using the call stack we will use
     * the object stack. Just like the recursive implementation, before we make subsequent calls,
     * i.e. adding to our call stack, we will print the root, then visit the children.
     * <p>
     * The iterative solution using a stack performs in the same way. Before we add a left node to
     * the stack we will print the node, in this case initially the root, and then left child,
     * and then that node's, left child, and so on and so on.
     * <p>
     * In the recursive solution, at this point, we now have nodes in the call stack. As the recursion
     * starts to back track, we must then check if the nodes at each stack frame have right children
     * print.
     * <p>
     * In the iterative solution, we simulate this, but popping our previously added "left" nodes
     * from the stack, and immediately check the right child. If a right child is found, we must then
     * "print" it, as this is our "root" in "root, left, right". We will print this "right" node,
     * before we push its left children onto the stack.
     *
     * @param root
     */
    public void preOrderIterTraversal(TreeNode<Integer> root) {
        Stack<TreeNode<Integer>> stack = new Stack<>();
        TreeNode<Integer> current = root;
        while (current != null) {
            System.out.print(current.getValue() + " ");
            stack.push(current);
            current = current.getLeft();
        }

        while (!stack.isEmpty()) {
            TreeNode<Integer> node = stack.pop();
            if (node.getRight() != null) {
                current = node.getRight();
                while (current != null) {
                    /*
                     * print the left children before you push them on to the stack! just like
                     * in the beginning of the algorithm
                     */
                    System.out.print(current.getValue() + " ");
                    stack.push(current);
                    current = current.getLeft();
                }
            }
        }
    }

    /**
     * This problem is a simple iteration on the in order traversal.
     * We perform the inorder traversal, however, what we pass an integer that represents the kth
     * element we are looking for.
     * <p>
     * as we perform the inorder traversal, every time we pass the root, we decrement k, as finding
     * the kth smallest, is the equivalent of simply going in increasing order up to k.
     * <p>
     * When k is 0, we have found our element, so we save it in our container object and return.
     *
     * @param root of tree
     * @param k    int the kth element we are looking for
     * @return int the kth smallest element
     */
    public int findKthSmallest(TreeNode<Integer> root, int k) {
        int[] count = new int[1];
        count[0] = k;
        int[] ans = new int[1];
        findKthSmallestHelper(root, count, ans);
        return ans[0];
    }

    /**
     * Helper for findKthSmallest
     *
     * @param root  root of tree
     * @param count array that is a container object for our count
     * @param ans   array that is container object for our ans
     */
    private void findKthSmallestHelper(TreeNode<Integer> root, int[] count, int[] ans) {
        if (root == null) {
            return;
        }

        findKthSmallestHelper(root.getLeft(), count, ans);
        count[0]--;

        if (count[0] == 0) {
            ans[0] = root.getValue();
        }
        findKthSmallestHelper(root.getRight(), count, ans);
    }

    /**
     * Builds a BST from a sorted array by using binary search.
     *
     * @param a the sorted array
     * @return the root of a binary search tree
     */
    public TreeNode<Integer> buildBst(int[] a) {
        return buildBstHelper(a, 0, a.length);
    }

    /**
     * Helper for buildBst
     *
     * @param a     the sorted array
     * @param start int the left boundary
     * @param end   int the right boundary
     * @return the root of the binary search tree
     */
    public TreeNode<Integer> buildBstHelper(int[] a, int start, int end) {
        if (start >= end) {
            return null;
        }
        int mid = (end + start) / 2;
        TreeNode<Integer> root = new TreeNode<>(a[mid]);
        root.setLeft(buildBstHelper(a, start, mid));
        root.setRight(buildBstHelper(a, mid + 1, end));
        return root;
    }

    /**
     * Finding the height of an n-ary tree. This algorithm proceeds
     * in the same manner as the normal find height of a binary tree.
     * In stead of looking merely recursing on the left and right node,
     * we recurse on all child nodes in a for loop, constantly computing
     * maximum height.
     *
     * @param root N-ary tree root
     * @return int height of tree
     */
    public int findHeightOfNaryTree(NTreeNode<Integer> root) {
        if (root == null) {
            return 0;
        }

        if (root.getChildren().size() == 0) {
            return 0;
        }

        int maxHeight = 0;
        for (NTreeNode node : root.getChildren()) {
            int height = findHeightOfNaryTree(node) + 1;
            maxHeight = Math.max(maxHeight, height);
        }
        return maxHeight;
    }

    /**
     * Convert a Binary search tree into a circular doubly linked list. The list should be created in increasing order
     * This problem may look quite complicated, but its actually quite simple.
     * <p>
     * For this problem, when we return the new head of our double linked list, we will still be returning TreeNodes
     * its just that our left pointers will be "previous" nodes and our right pointers will be "next nodes".
     * <p>
     * The difficult I originally with this problem was the following. Given the tree
     * <p>
     * 4
     * /    \
     * 2     6
     * /   \  /  \
     * 1    3  5   7
     * <p>
     * Its obvious how to convert the left subtree. 1 <- 2 -> 3 to a list, but how do we connect the 3 with the root node 4?
     * I previously got confused about this, but the point is to remember, that when doing an in order traversal. Connecting
     * the 3 node, with the root 4, is as simple as keeping track of the "previous" node visited. And remember we do this
     * with an object, which will store the previous node. In this case a simply array with only one spot will suffice.
     * <p>
     * Because we are modifying the object, the object will maintain the value as we see fit, throughout the call stack,
     * essentially acting like a global variable.
     * <p>
     * At that point the majority of the problem is completed, we just need to tie up a few loose ends. The first being,
     * returning our newhead. Again, just use another container object, in this case an array and store the newhead there.
     * <p>
     * But how do we realize what our new head is? Well remember, that when doing an in order traversal, using this
     * array container object, when we get to the left most node, and then start to move back up the call stack,
     * our new head should be that left most node. At this point the previous pointer is null, so we set it to
     * the node 1.
     * <p>
     * This represents the ONLY time the previous pointer will be null. Since we are moving in increasing order and this
     * is a binary search tree, then there is NO value smaller than this left most node. So using a simply if statement
     * checking if the previous node is null is all that needs to be done.
     * <p>
     * Now if the previous node is NOT null, then we know we have something to work with, and now, we need to set
     * pointers linking up the nodes.
     * <p>
     * One might ask ourselves "But before we reset the previous pointer, and set previous.next = root, and
     * root.left = previous. How do we set the "next" or right pointer for the root"? Remember that will be
     * taken care of with the subsequent recursive calls. The calls henceforth will do that job.
     * <p>
     * The last thing to do, to make this a truly circular linked list, is to set the "next" or right pointer
     * of the last node in the double linked list, to head, and then set the "previous" or left pointer of
     * the head node to the last element. Again...this is because our recursion ended without being
     * able to do this for the last node in the list, or the right most node of the tree.
     * <p>
     * We could also just have one more container object, storing the last node in the traversal. The last
     * time this container object has its node reset will represent the right most node.
     * <p>
     * <p>
     * RUNTIME: O(n) due to visiting every node in the tree
     * SPACE: O(n) because of the call stack.
     *
     * @param root
     * @return
     */
    public TreeNode<Integer> bstToLLusingInOrder(TreeNode<Integer> root) {
        TreeNode<Integer>[] newHead = new TreeNode[1];
        TreeNode<Integer>[] newTail = new TreeNode[1];
        TreeNode<Integer>[] previous = new TreeNode[1];
        BSTtoLLHelper(root, previous, newHead, newTail);

        newHead[0].setLeft(newTail[0]);
        newTail[0].setRight(newHead[0]);
        return newHead[0];
    }

    private void BSTtoLLHelper(TreeNode<Integer> root, TreeNode<Integer>[] previous, TreeNode<Integer>[] newHead, TreeNode<Integer>[] newTail) {
        if (root == null) {
            return;
        }

        BSTtoLLHelper(root.getLeft(), previous, newHead, newTail);

        if (previous[0] == null) {
            previous[0] = root;
            newHead[0] = previous[0];
        } else {
            previous[0].setRight(root);
            root.setLeft(previous[0]);
            previous[0] = root;
        }

        newTail[0] = root;
        BSTtoLLHelper(root.getRight(), previous, newHead, newTail);
    }

    /**
     * The implementation below is extremely. Not at all my first approach as I think its
     * more complicated. I think it would have been easier to just do inorder traversal.
     * <p>
     * Nevertheless its worth discussing. Essentially we are going to use post order
     * traversal to traverse the tree.
     * <p>
     * So first traverse down the left subtree, and build a double linked list. Then traverse
     * down the right, and build a double linked list. Return the heads of boths lists.
     * <p>
     * Then create a double linked list with just the root.
     * <p>
     * The merge the left and the root. Take the head of that result, and merge it with the right.
     * <p>
     * The tricky thing to do, is to make a separate method for merging the lists. I also would
     * have never thought of turning the root into a double link list by itself.
     * <p>
     * This solution uses post-order traversal approach of a tree and creates a circular doubly linked list. Process are:
     * <p>
     * Traverse left subtree and create a circular doubly linked list. Let’s address it as leftCDLL
     * Traverse right subtree and create a doubly linked list. Let’s address is as rightCDLL.
     * Created a circular doubly linked list using the root node only. Let’s call it rootCDLL.
     * Connect leftCDLL and rootCDLL. Let’s address it as leftRootCDLL. Head will be head of leftCDLL.
     * Connect leftRootCDLL and rightCDLL and let’s call it resultCDLL. Head will be the head of leftRootCDLL.
     * <p>
     * <p>
     * Tricky part of this approach is to connect two CDLL. Approach to connect two CDLL is described below. For convenience, we will use leftCDLL as first CDLL and rightCDLL as second CDLL to connect.
     * <p>
     * If one of them is null return other one.
     * Find out tail of leftCDLL. Let’s denote it as leftTail.
     * Find out tail of rightCDLL. Let’s denote it as rightTail.
     * Connect leftTail and head of rightCDLL.
     * Connect rightTail and head of leftCDLL.
     * Mark head of leftCDLL as the head of resultant CDLL.
     *
     * @param root
     * @return
     */
    public TreeNode<Integer> bstToLLUsingPostOrder(TreeNode<Integer> root) {
        if (root == null) {
            return root;
        }

        TreeNode<Integer> leftList = bstToLLUsingPostOrder(root.getLeft());
        TreeNode<Integer> rightList = bstToLLUsingPostOrder(root.getRight());

        root.setLeft(root);
        root.setRight(root);

        root = merge(leftList, root);
        root = merge(root, rightList);
        return root;
    }

    /**
     * Merge - the tricky part here is the pointer assignment.
     * Remember this, that the first node of the "a" argument is the one
     * we are returning
     * <p>
     * Also remember, and EVEN MORE IMPORTANT, when we do aEnd=a.left, this this value
     * is actually the "end", i mean literally the "end" of a's list.
     * <p>
     * Thats why the line, aEnd.right = b, is correctly linking the two lists.
     * <p>
     * Because, for the example of a= 1 <->2, b = 3
     * <p>
     * While a is 1, a.left is 2 or the end of a's list. so aEnd.right, means, take
     * 2's right pointer and connect it with b, or in this case 3.
     *
     * @param a
     * @param b
     * @return
     */
    private TreeNode<Integer> merge(TreeNode<Integer> a, TreeNode<Integer> b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }

        TreeNode<Integer> aEnd = a.getLeft();
        TreeNode<Integer> bEnd = b.getLeft();

        /* First we link the outer most nodes, as in the beginning of a with the b node */
        a.setLeft(bEnd);
        /* then link the right pointer of b with a, this give us our circle */
        bEnd.setRight(a);

        /* now begin linking the interior, the end of a's list points to b*/
        aEnd.setRight(b);
        /* the "previous" pointer of b, i.e. its left pointer, points to the end of a's list */
        b.setLeft(aEnd);
        return a;
    }

    public Integer lowestCommonAncestorOfBinaryTree(TreeNode<Integer> root, TreeNode<Integer> a, TreeNode<Integer> b) {

        List<TreeNode<Integer>> candidatePath = new ArrayList<>();
        List<TreeNode<Integer>> actualPathOfA = new ArrayList<>();
        List<TreeNode<Integer>> actualPathOfB = new ArrayList<>();

        lowestCommonAncestorOfBinaryTreeHelper(root, candidatePath, actualPathOfA, a);
        lowestCommonAncestorOfBinaryTreeHelper(root, candidatePath, actualPathOfB, b);

        return findLowestCommonAncestor(actualPathOfA, actualPathOfB);
    }

    private void lowestCommonAncestorOfBinaryTreeHelper(TreeNode<Integer> root, List<TreeNode<Integer>> candidatePath, List<TreeNode<Integer>> actualPath, TreeNode<Integer> target) {
        if (root == null) {
            return;
        }

        if (root.getValue().equals(target.getValue())) {

            actualPath.addAll(new ArrayList<>(candidatePath));
            actualPath.add(target);
            return;
        }

        candidatePath.add(root);
        lowestCommonAncestorOfBinaryTreeHelper(root.getLeft(), candidatePath, actualPath, target);
        lowestCommonAncestorOfBinaryTreeHelper(root.getRight(), candidatePath, actualPath, target);

        candidatePath.remove(candidatePath.size() - 1);
    }

    private Integer findLowestCommonAncestor(List<TreeNode<Integer>> pathOfA, List<TreeNode<Integer>> pathOfB) {

        Set<Integer> set = new HashSet<>();
        Integer lowestCommonAncestor = null;
        for (TreeNode<Integer> a : pathOfA) {
            set.add(a.getValue());
        }

        for (TreeNode<Integer> b : pathOfB) {
            if (set.contains(b.getValue())) {
                lowestCommonAncestor = b.getValue();
            }
        }
        return lowestCommonAncestor;
    }
    // Look at range minimum query, Euler tour

    /**
     * Merge two binary search trees
     * <p>
     * - This is an excellent problem as it tests several different algorithms. The problem is there for solved
     * in several steps
     * <p>
     * First we create a list of all of the nodes in the first tree, by doing an in order traversal
     * Then we create a list of all of the nodes in the second three by doing another in order traversal
     * <p>
     * The running time of doing this is O(n1) and then O(n2)
     * <p>
     * We know that both lists will already be sorted, because we are given two binary search trees, and
     * by doing the in order traversal, we know the lists are sorted.
     * <p>
     * Then we will merge the two lists created from the trees. This merge portion is similiar to the merge
     * portion of mergeSort.
     * <p>
     * We create a results array that has space enough for elements from both trees, and then we insert the
     * nodes into the array in order.
     * <p>
     * We should wind up with a list of nodes of both trees in sorted order.
     * <p>
     * Now we have to use this sorted array of nodes, and create a binary search tree. Because the array is
     * already sorted, we simply run binary search on the sorted array, where we will take the
     * node from the middle of array as the root, the node from the left half as the left node, and the
     * node from right half as the right node.
     * <p>
     * Remember the boundary checking for binary search. What that means is, to calculate the mid we should NOT
     * do (right - left) / 2. as this gives us the magnitude from a boundary, as opposed to the actual index
     * of the array.
     * <p>
     * We can get the actual index of the array by doing (left + right) / 2, to get the correct index in whichever
     * half of the array.
     * <p>
     * Also remember the base case for binary search. This left and right boundary are the bound of the sub array
     * that we are spitting in half. If the left and right index are the same, then we know there is no more sub
     * array to search into. So you can either do, if (left < right) then proceed. Or if left == right, return.
     * <p>
     * The running time for this is O(n1 + n2). The inorder traversals are linear. The merge is linear and the binary
     * search is O(log (n1 + n2)).
     * <p>
     * Space complexity: O(n1 + n2) because we make an auxilary array to store all of the sorted nodes
     * <p>
     * Now that we have this sorted list
     *
     * @param a root of tree
     * @param b root of tree
     * @return root from merge trees
     */
    public TreeNode<Integer> mergeBSTs(TreeNode<Integer> a, TreeNode<Integer> b) {
        List<TreeNode<Integer>> nodesOfA = new ArrayList<>();
        List<TreeNode<Integer>> nodesOfB = new ArrayList<>();

        inOrderGetList(a, nodesOfA); // O(n)
        inOrderGetList(b, nodesOfB); // O(n)

        // O(n)
        TreeNode[] allNodes = mergeLists(nodesOfA.toArray(new TreeNode[0]), nodesOfB.toArray(new TreeNode[0]));

        Util.print(allNodes);
        //O(log n)
        TreeNode<Integer> root = createBstFromSortedArray(allNodes);
        return root;
    }

    /**
     * Gets a sorted list of nodes from a binary search tree by performing an inorder traversal.
     *
     * @param root binary search tree
     * @param list list of sorted nodes
     */
    private void inOrderGetList(TreeNode<Integer> root, List<TreeNode<Integer>> list) {
        if (root == null) {
            return;
        }

        inOrderGetList(root.getLeft(), list);
        list.add(root);
        inOrderGetList(root.getRight(), list);
    }

    /**
     * Merges two sorted arrays of TreeNodes. Similar to the merge portion
     * of merge sort. Only thing that's different is there is no temp array to copy
     * back to the original array.
     *
     * @param aNodes Nodes from BST a
     * @param bNodes Nodes from BST b
     * @return TreeNode[] sorted array of TreeNodes
     */
    private TreeNode[] mergeLists(TreeNode[] aNodes, TreeNode[] bNodes) {
        int length = aNodes.length + bNodes.length;
        TreeNode[] results = new TreeNode[length];
        int resultsCtr = 0;

        int begA = 0;
        int endA = aNodes.length - 1;
        int begB = 0;
        int endB = bNodes.length - 1;

        while (begA <= endA && begB <= endB) {
            if (aNodes[begA].getValue().compareTo(bNodes[begB].getValue()) <= 0) {
                results[resultsCtr] = aNodes[begA];
                begA++;
                resultsCtr++;
            } else {
                results[resultsCtr] = bNodes[begB];
                begB++;
                resultsCtr++;
            }
        }

        while (begA <= endA) {
            results[resultsCtr] = aNodes[begA];
            begA++;
            resultsCtr++;
        }

        while (begB <= endB) {
            results[resultsCtr] = bNodes[begB];
            begB++;
            resultsCtr++;
        }

        return results;
    }

    /**
     * Creates a binary search tree from a sorted array of nodes. Does this
     * by performing a binary search on the sorted array.
     * <p>
     * Now we have to use this sorted array of nodes, and create a binary search tree. Because the array is
     * already sorted, we simply run binary search on the sorted array, where we will take the
     * node from the middle of array as the root, the node from the left half as the left node, and the
     * node from right half as the right node.
     * <p>
     * Remember the boundary checking for binary search. What that means is, to calculate the mid we should NOT
     * do (right - left) / 2. as this gives us the magnitude from a boundary, as opposed to the actual index
     * of the array.
     * <p>
     * We can get the actual index of the array by doing (left + right) / 2, to get the correct index in whichever
     * half of the array.
     * <p>
     * Also remember the base case for binary search. This left and right boundary are the bound of the sub array
     * that we are spitting in half. If the left and right index are the same, then we know there is no more sub
     * array to search into. So you can either do, if (left < right) then proceed. Or if left == right, return.
     *
     * @param allNodes sorted array of TreeNodes
     * @return root of Binary Search Tree
     */
    public TreeNode<Integer> createBstFromSortedArray(TreeNode[] allNodes) {
        return createBstFromSortedArrayHelper(allNodes, 0, allNodes.length);
    }

    /**
     * Helper function for createBstFromSortedArray
     *
     * @param allNodes Sorted array of BST nodes
     * @param left     int left boundary
     * @param right    int right boundary
     * @return root of binary search tree
     */
    private TreeNode<Integer> createBstFromSortedArrayHelper(TreeNode<Integer>[] allNodes, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            TreeNode<Integer> root = allNodes[mid];
            root.setLeft(createBstFromSortedArrayHelper(allNodes, left, mid));
            root.setRight(createBstFromSortedArrayHelper(allNodes, mid + 1, right));
            return root;
        }
        return null;
    }

    /**
     * This problem deals with turning a binary tree, upside down.
     * <p>
     * First we take a tree where all of the right nodes are leaf nodes, like the following
     * <p>
     * 1
     * / \
     * 2  3
     * / \
     * 4  5
     * / \
     * 6  7
     * <p>
     * Then trans form the tree, by turning it upside down, and making it a tree, where all of the left nodes
     * are leaf nodes
     * <p>
     * 1
     * /
     * 2---3
     * /
     * 4---5
     * /
     * 6---7
     * <p>
     * The final tree should look like
     * <p>
     * 6
     * / \
     * 7  4
     * / \
     * 5  2
     * / \
     * 3  1
     * <p>
     * This problem may seem tricky but its actually not.
     * <p>
     * The first thing we have to do is perform an inorder traversal. We perform the inorder traversal, because this allows
     * us to first find the leaf most node in the tree, which will be the root of our new tree.
     * <p>
     * Then, from the bottom up, we will simply build the tree, by reassigning the pointers.
     * <p>
     * The one thing to watch out for, is that, as we remove call stacks from the stack frame, we end up
     * resetting the left and right pointer of the "root" in every call stack (as we build the new tree),
     * after said call stack frame is removed from the stack.
     * <p>
     * The question is, how do we reset the left and right pointer of the original root node, when all of the
     * call stack frames have been removed?
     * <p>
     * One thing we can do, is as we reset the pointers of the new "root", we should also unlink the left
     * and right pointer of the "old" root, as those will be reset the by subsequent call stack, as we
     * move back up the tree, from bottom to top.
     * We are able to rebuild this tree, without using extra pace.
     * <p>
     * The recursive solution incurs the cost of using the call stack.
     * <p>
     * Running time: O(n)
     * Space Complexity: O(n) for the call stack
     * <p>
     * The question remains,
     *
     * @param root
     * @return
     */
    public TreeNode<Integer> turnTreeUpsideDown(TreeNode<Integer> root) {
        TreeNode<Integer>[] newRootContainer = new TreeNode[1];
        turnTreeUpsideDownHelper(root, newRootContainer);
        return newRootContainer[0];
    }

    /**
     * Helper for turnTreeUpsideDown
     *
     * @param root             root of tree
     * @param newRootContainer new root of tree
     */
    private void turnTreeUpsideDownHelper(TreeNode<Integer> root, TreeNode<Integer>[] newRootContainer) {
        if (root == null) {
            return;
        }

        turnTreeUpsideDownHelper(root.getLeft(), newRootContainer);

        if (newRootContainer[0] == null) {
            newRootContainer[0] = root;
        }

        turnTreeUpsideDownHelper(root.getRight(), newRootContainer);

        if (root.getLeft() != null && newRootContainer[0] != null) {
            root.getLeft().setLeft(root.getRight());
            root.getLeft().setRight(root);
            root.setLeft(null);
            root.setRight(null);
        }
    }

    public TreeNode<Integer> turnTreeUpsideDownIter(TreeNode<Integer> root) {

        // the children for the old root
        TreeNode<Integer> newLeftChildOfOldRoot = null;
        TreeNode<Integer> newRightChildOfOldRoot = null;

        while (root != null) {
            /**
             * This will be our new Root. So lets save it before we update it.
             */
            TreeNode<Integer> nextRoot = root.getLeft();

            /**
             * Get the next children for the new root
             */
            TreeNode<Integer> leftChildOfNextRoot = root.getRight();
            TreeNode<Integer> rightChildOfNextRoot = root;

            /*
             * At first unlink the old root, this will then update the children for the old root values
             * Effectively updating the current root for its new children. After first this will make the
             * original node, have left and right children that are null
             */
            root.setLeft(newLeftChildOfOldRoot);
            root.setRight(newRightChildOfOldRoot);

            if (nextRoot != null) {
                root = nextRoot;
                newLeftChildOfOldRoot = leftChildOfNextRoot;
                newRightChildOfOldRoot = rightChildOfNextRoot;
            } else {
                return nextRoot;
            }
        }
        return root;
    }

    /**
     * createBTreeFromInorderAndPreOrder - To do this problem we must understand the nature of both the PreOrder
     * and Inorder traversals.
     * <p>
     * If want to build a Binary Tree, then we have to find the root of the tree. How do we find the root?
     * Well we know that the root of a tree in a preOrder traversal, is always the first node. So we merely
     * have to look at the first node of the PreOrder Traversal.
     * <p>
     * Now that we have the root, we then have to figure out what nodes makes up the left and a right subtrees
     * of the root node. This is where the inOrder traversal comes in.
     * <p>
     * We take the root, that we found from the preOrder traversal, and then we find it in the array of the
     * InOrder traversal. Because the InOrder traversal is such that the nodes are all in order, we then know
     * that all of the nodes to the left of the root in the Inorder Traversal array must make up the left subtree.
     * All of the nodes to the right of the root node, in the Inorder traversal, make up the right subtree.
     * <p>
     * So, this is where we recurse, this time, looking for the next root from the PreOrder traversal
     * So just increment the counter in the preOrder traversal by 1, moving it to the right. This next root
     * will end up being the root of the left sub tree of the BST. We then search for the next root,
     * in the left portion of the InOrder Traversal.
     * <p>
     * We continue to do so, until we hit our base case, which is when either the leftBoundary of the
     * InOrder array is greater than the right boundary, in which case we return null,
     * or when the InOrder index is equal to the right boundary, in which case we should return the
     * root from the preOrder array.
     * <p>
     * As the recursion returns and travels up the call stack, it will then look to the right of the root in
     * the Inorder traversal array. As the recursion removes frames from the call stack, it will set the left and right
     * child nodes of every root in the given stack frame as it moves back up the tree.
     * <p>
     * RUNTIME: we have to move through the entire preOrder array, so we know its at least O(n).
     * But at every call, we have to move through at least half of the inOrder traversal array,
     * which would make that O(log n). So in total thats a runtime of O(n log n).
     * <p>
     * SPACE COMPLEXITY: The extra memory taken up is entirely from the call stack, which is the
     * depth of the recursion tree. Because this is not a BST, the binary tree created could just
     * be a long list, continually moving to the left. So this means at most O(n)
     *
     * @param preOrder array of nodes from preOrder traversal
     * @param inOrder  array of nodes from inOrder traversal
     * @return root of Binary Tree
     */
    public TreeNode<Character> createBTreeFromInorderAndPreOrder(TreeNode<Character>[] preOrder, TreeNode<Character>[] inOrder) {
        int[] preOrderIndex = new int[1];
        if (preOrder.length != inOrder.length) {
            throw new IllegalArgumentException("Both inorder and preorder arrays should be of the same length");
        }
        return createBTreeFromInorderAndPreOrderHelper(preOrder, preOrderIndex, inOrder, 0, inOrder.length - 1);
    }

    private TreeNode<Character> createBTreeFromInorderAndPreOrderHelper(TreeNode<Character>[] preOrder, int[] preIndex, TreeNode<Character>[] inOrder, int inIndex, int rightBoundary) {
        if (inIndex > rightBoundary) {
            return null;
        }

        TreeNode<Character> root = preOrder[preIndex[0]];
        preIndex[0]++;
        if (inIndex == rightBoundary) {
            return root;
        }

        int right = inIndex;
        for (int i = inIndex; i <= rightBoundary; i++) {
            if (root.getValue().equals(inOrder[i].getValue())) {
                right = i;
                break;
            }
        }

        root.setLeft(createBTreeFromInorderAndPreOrderHelper(preOrder, preIndex, inOrder, inIndex, right - 1));
        root.setRight(createBTreeFromInorderAndPreOrderHelper(preOrder, preIndex, inOrder, right + 1, rightBoundary));
        return root;
    }

    /**
     * createBTreeFromInorderAndPostOrder - This algorithm proceeds exactly in the same manner as the algorithm
     * for createBTreeFromInorderAndPreOrder. The only difference is the manner in which we determine what
     * the root is in the postOrder traversal. We know the postOrder traversal visits the root last, so we
     * simply start at the end of the array to find the root.
     * <p>
     * We then find that root in the InOrder traversal, and proceed to look in the subsequent halves of the
     * Inorder array, this time building the right subtree first, before we move to the left. This makes sense
     * because the post Order traversal is such that, we visit left, right, root.
     * <p>
     * After building the right, then we recurse to the left. We continuously find our root, by decrementing by
     * one each time in the PostOrder array, moving to zero.
     * <p>
     * RUNTIME: we have to move through the entire preOrder array, so we know its at least O(n).
     * But at every call, we have to move through at least half of the inOrder traversal array,
     * which would make that O(log n). So in total thats a runtime of O(n log n).
     * <p>
     * <p>
     * SPACE COMPLEXITY: The extra memory taken up is entirely from the call stack, which is the
     * depth of the recursion tree. Because this is not a BST, the binary tree created could just
     * be a long list, continually moving to the left. So this means at most O(n)
     *
     * @param postOrder array of nodes from postOrder traversal
     * @param inOrder   array of nodes from inOrder traversal
     * @return root of Binary Tree
     */
    public TreeNode<Character> createBTreeFromInorderAndPostOrder(TreeNode<Character>[] postOrder, TreeNode<Character>[] inOrder) {
        int[] postOrderIndex = new int[1];
        postOrderIndex[0] = postOrder.length - 1;

        if (postOrder.length != inOrder.length) {
            throw new IllegalArgumentException("Both inOrder and postOrder arrays should be of the same length");
        }
        return createBTreeFromInorderAndPostOrderHelper(postOrder, postOrderIndex, inOrder, 0, inOrder.length - 1);
    }

    private TreeNode<Character> createBTreeFromInorderAndPostOrderHelper(TreeNode<Character>[] postOrder, int[] postOrderIndex, TreeNode<Character>[] inOrder, int inIndex, int rightBoundary) {
        if (inIndex > rightBoundary) {
            return null;
        }

        TreeNode<Character> root = postOrder[postOrderIndex[0]];
        postOrderIndex[0]--;
        if (inIndex == rightBoundary) {
            return root;
        }

        int right = inIndex;
        for (int i = inIndex; i <= rightBoundary; i++) {
            if (root.getValue().equals(inOrder[i].getValue())) {
                right = i;
                break;
            }
        }

        root.setRight(createBTreeFromInorderAndPostOrderHelper(postOrder, postOrderIndex, inOrder, right + 1, rightBoundary));
        root.setLeft(createBTreeFromInorderAndPostOrderHelper(postOrder, postOrderIndex, inOrder, inIndex, right - 1));
        return root;
    }

    /**
     * createBTreeFromPreOrderAndPostOrder - This problem was a real bitch.
     *
     * It was difficult to come up with a plain english description of how to solve the problem.
     *
     * It was then even more difficult to then write the code described by my plain english description.
     *
     * First the description.
     *
     * You have an array of the nodes of the tree from a PreOrder Traversal and you have the nodes of the
     * tree from a PostOrder Traversal.
     *
     * Pre:  A, B, D, G, H, K, C, E, F
     * Post: G, K, H, D, B, E, F, C, A
     *
     * First lets just understand how to build the tree, given the two traversals.
     *
     * We know we need to start with the root. So we know we can begin with the PreOrder Traversal array.
     * The first node in that list is the root of the tree. So we will call our helper function with this
     * node as the root.
     *
     * We will build this tree top down.
     *
     * To understand the solution we say the following. Once we have our root, we look at the next node, which is
     * B.
     *
     * We then use our next "potential" root, and we look it up in the post Order traversal array. We ask ourselves
     * if this next potential root, comes before our current root in the post order traversal. If it does, then we know the next root,
     * is a child (of some sort) of the current root. We then ask ourselves the following.
     *
     * Does our root node have any children?
     *
     * If our root has no children, then we will add our node to the left child of the root. If it has a left child,
     * we will add the node to the right. Because the postOrder traversal, goes in the order of left, right, root,
     * we are building this tree top down, by adding to the left, then the right.
     *
     * If we find that the "next" root, doesn't come before the current root, then we know that the "next"
     * root is not a child of the current root, so we then need to move back up the tree, and check each node
     * until we find one in which our next potential root, does actually come before the root.
     * if that node has a left child, then set this "next" root as its right child, increment the index in the preOrder
     * array, and then continue to recurse again.
     *
     * Writing code for the explanation above was very difficult. The first thing to understand was that, given our
     * nextRoot, if its position in the postOrder array is to the left of the "root" in the post Order array, then
     * that means we should check the children of the root.
     *
     * If there are no children, then set the left child. Increment the preOrder array index, then recurse with our
     * next root, and the index of that root. On the next call stack, these values will represent the root, and root index,
     * and the incremented preOrder index (from the prior call stack), will be the index of the "next" potential root.
     *
     * We will then find that "next" root in the PostOrder array, check to see if its to the left of the current root (the
     * "next" root of the prior call stack), and then proceed to do the same thing as before. Check the children, set the
     * appropriate pointer, and then recurse.
     *
     * The difficult thing for me, was when the "next" potential root, was NOT to the left of the current root in the postOrder
     * array. I knew that meant, that the "next" root wasn't a child of the current root. So I knew I needed to start returning
     * and moving back up the call stack, in turn moving back up the tree, but I wasn't sure how to keep track of the "next" node.
     *
     * I also wasn't sure how to keep checking if the "next" node was to the left of the current node. Originally I was trying to
     * recurse within the loop, but this was a mistake. Essentially I needed to find the node, check the position, set the child, if it was good, then i would recurse. When back tracking,
     * find the node, check the position, set the children, then keep recursing.
     *
     * Using the preOrderIndex array container helps, because as I remove calls off of the call stack, The original
     * "next" root, will still be sitting in the stack frame. But what I need is not that "next" root, but the
     * next root, that wasn't to the left from the call stack that was just removed!
     *
     * That's why I select the nextRoot again using the preOrder array with the preOrderIndex, as the preOrderIndex is properly
     * update to date, while the nextRoot variable has just been sitting in the stack frame, so I can grab that nextNode
     * and try to set it on the new parent. Once I see that this nextRoot is now to the left of the current root in
     * the postOrder array, I can set the right child. And begin recursing again by incrementing the right index.
     *
     * @param preOrder - array of tree nodes from pre order traversal
     * @param postOrder - array of tree nodes from post order traversal
     * @return root of tree
     */
    public TreeNode<Character> createBTreeFromPreOrderAndPostOrder(TreeNode<Character>[] preOrder, TreeNode<Character>[] postOrder) {
        int[] preOrderIndex = new int[1];
        TreeNode<Character> root = preOrder[preOrderIndex[0]];
        preOrderIndex[0]++;

        int postOrderRootIndex = postOrder.length - 1;
        createBTreeFromPreAndPostHelper(preOrder, preOrderIndex, postOrder, root, postOrderRootIndex);
        return root;
    }

    private void createBTreeFromPreAndPostHelper(TreeNode<Character>[] preOrder, int[] preOrderIndex, TreeNode<Character>[] postOrder, TreeNode<Character> root, int postOrderRootIndex) {
        if (preOrderIndex[0] == postOrder.length) {
            return;
        }

        TreeNode<Character> nextRoot = preOrder[preOrderIndex[0]];
        int nextRootPostIndex = findIndexOfTreeNode(postOrder, nextRoot);
        if (nextRootPostIndex < postOrderRootIndex) {
            if (root.getLeft() == null && root.getRight() == null) {
                root.setLeft(nextRoot);
                preOrderIndex[0]++;
                createBTreeFromPreAndPostHelper(preOrder, preOrderIndex, postOrder, nextRoot, nextRootPostIndex);
            }

            nextRoot = preOrder[preOrderIndex[0]];
            nextRootPostIndex = findIndexOfTreeNode(postOrder, nextRoot);
            if (nextRootPostIndex < postOrderRootIndex) {
                if (root.getRight() == null) {
                    root.setRight(nextRoot);
                    preOrderIndex[0]++;
                    createBTreeFromPreAndPostHelper(preOrder, preOrderIndex, postOrder, nextRoot, nextRootPostIndex);
                }
            }
        }
    }

    /**
     * findIndexOfTreeNode - given an array this returns the index of the node within the array
     *
     * @param array - array of tree nodes
     * @param treeNode the node to find in the array
     * @return index of the node in the array
     */
    private int findIndexOfTreeNode(TreeNode<Character>[] array, TreeNode<Character> treeNode) {
        for (int i = 0; i < array.length; i++) {
            TreeNode<Character> postOrderNode = array[i];
            if (postOrderNode.getValue().equals(treeNode.getValue())) {
                return i;
            }
        }
        throw new IllegalArgumentException("the treeNode has to be in the array");
    }

    /**
     * deleteBstNode - Deleting a node from a binary search tree means removing the node from the
     * tree while maintaining the ordered property of the binary search tree.
     *
     * To do this we need to handle 3 cases.
     *
     * 1) when we delete a leaf node
     * 2) when we delete a node with one child
     * 3) when we delete a node with 2 children
     *
     * 1) when we delete a leaf node - this is the simplest case. That means, when we see that our
     * root is equal to our target value, we just set root = null, and there for we will have
     * disappeared this node
     *
     * 2) When we delete a node with one child, that is also simple. That just means we return that one child
     * which will effectively disappear that root.
     *
     * 3) This is the most complicated case: We need to be sure that we maintain the binary search property.
     * And we don't want to lose any of the right or left subtrees of the node that we are trying to delete.
     * To keep those sub trees, those descendant, and maintain the BST property, we have have 2 choices.
     * We can either take the maximum node of the left subtree, and overrwite the node we wish to delete
     * OR we can take the minimum node of the left subtree, and overrwrite the node we wish to delete.
     *
     * But we are not done there. Once we have copied the value to the current root we wish to delete, we
     * then have to delete that max or min node that we originally copied from, so that we don't have
     * duplicate nodes. In order to do this, we simply call our delete function again, because we have now
     * reduced this problem to case 1, becaue the max or min nodes for any subtree, will always be a leaf
     * node.
     *
     * One more thing, remember to return the root with each call stack so that we keep the tree intact.
     * @param root root of tree
     * @param target the value we wish to delete
     * @return root
     */
    public TreeNode<Integer> deleteBstNode(TreeNode<Integer> root, Integer target) {
        if (root == null) {
            return root;
        }

        if (target.compareTo(root.getValue()) < 0) {
            root.setLeft(deleteBstNode(root.getLeft(), target));
        } else if (target.compareTo(root.getValue()) > 0) {
            root.setRight(deleteBstNode(root.getRight(), target));
        } else {
            if (root.getLeft() == null && root.getRight() == null) {
                root = null;
            } else if (root.getLeft() != null && root.getRight() == null) {
                root = root.getLeft();
            } else if (root.getRight() != null && root.getLeft() == null) {
                root = root.getRight();
            } else {
                TreeNode<Integer> node = findMaxNode(root.getLeft());
                root.setValue(node.getValue());
                root.setLeft(deleteBstNode(root.getLeft(), node.getValue()));
            }
        }
        return root;
    }

    /**
     * insertNodeIntoBST - For this implementation of a Binary Search Tree
     * we are going to use an AVL tree. An AVL tree is a height balanced tree.
     * It works by consistently checking the height of each left and right subtrees.
     *
     * When the height of the left and right subtrees is greater than 1, then we most rebalance.
     *
     * We we balance the tree, by looking at 4 cases
     *
     * 1) RR tree, where
     *
     *      Node
     *         \
     *          Node
     *             \
     *              Node
     *
     * 2) RL tree, where
     *
     *      Node
     *         \
     *          Node
     *          /
     *       Node
     *
     * 3) LL tree, where
     *
     *      Node
     *     /
     *   Node
     *   /
     * Node
     *
     * 4) LR tree, where
     *
     *      Node
     *      /
     *    Node
     *       \
     *       Node
     *
     * We rotate the trees as necessary, preserving the BST property.
     * @param root root of tree
     * @param value value we wish to insert
     * @return root of tree
     */
    public TreeNode<Integer> insertNodeIntoBST(TreeNode<Integer> root, Integer value) {
        if (root == null) {
            root = new TreeNode<Integer>(value);
        } else if (value.compareTo(root.getValue()) < 0) {
            root.setLeft(insertNodeIntoBST(root.getLeft(), value));
        } else if (value.compareTo(root.getValue()) > 0) {
            root.setRight(insertNodeIntoBST(root.getRight(), value));
        } else {
            // equals values so do nothing. try not to allow duplicates in a bst
        }

        int leftHeight = findHeight(root.getLeft());
        int rightHeight = findHeight(root.getRight());

        int diff = Math.abs(leftHeight - rightHeight);
        if (diff > 1) { // need to rotate tree
            /*
             * First we need to identify the kinds of rotations
             * There are 4 kinds, and we only perform three root rotations
             * RR, LL, RL, LR
             */
            if (root.getLeft() != null && root.getLeft().getLeft() != null) { // LL tree
                root = rotateLL(root);
            } else if (root.getRight() != null && root.getRight().getRight() != null) { // RR tree
                root = rotateRR(root);
            } else if (root.getRight() != null && root.getRight().getLeft() != null) {
                root = rotateLR(root);
            } else { // must be RL tree
                root = rotateRL(root);
            }
        }
        return root;
    }

    private TreeNode<Integer> rotateRR(TreeNode<Integer> root) {
        TreeNode<Integer> newRoot = root.getRight();
        newRoot.setLeft(root);
        root.setRight(null);
        return newRoot;
    }

    private TreeNode<Integer> rotateLL(TreeNode<Integer> root) {
        TreeNode<Integer> newRoot = root.getLeft();
        newRoot.setRight(root);
        root.setLeft(null);
        return newRoot;
    }

    private TreeNode<Integer> rotateLR(TreeNode<Integer> root) {
        TreeNode<Integer> newRoot = root.getLeft().getRight();
        newRoot.setLeft(root.getLeft());
        newRoot.setRight(root);
        root.getLeft().setRight(null);
        root.setLeft(null);
        return newRoot;
    }

    private TreeNode<Integer> rotateRL(TreeNode<Integer> root) {
        TreeNode<Integer> newRoot = root.getRight().getLeft();
        newRoot.setLeft(root);
        newRoot.setRight(root.getRight());
        root.getRight().setLeft(null);
        root.setRight(null);
        return newRoot;
    }

    /**
     * IsBalanced - this method checks to see if a Binary Tree is balanced. In order to do
     * this is must check every left and right subtree until it finds one where the left height
     * and right height differ by more than one.
     *
     * It performs this check bottom up. We recurse all the way to the bottom of the tree to the left,
     * then the right. When we reach the bottom and begin to move back up the tree (and call stack)
     * we check the height of the left and right subtree of every root.
     *Td
     * The difference between the heights is greater than one, then we know the tree is not balanced,
     * so we should return false.
     *
     * @param root root of the tree
     * @return boolean
     */
    public boolean isBalanced(TreeNode<Integer> root) {
        if (root == null) {
            return true;
        }

        boolean isLeftBalanced = isBalanced(root.getLeft());
        boolean isRightBalanced = isBalanced(root.getRight());

        int leftHeight = findHeight(root.getLeft());
        int rightHeight = findHeight(root.getRight());

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return false;
        }

        return isLeftBalanced && isRightBalanced;
    }


}
