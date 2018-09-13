package main.java.com.codepath.trees;

import main.java.com.codepath.objects.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
        int sum;

        TreeCell(TreeNode node, int sum) {
            this.node = node;
            this.sum = sum;
        }

        public String toString() {
            return node.toString() + ":sum="+sum;
        }
    };

    /**
     * This is better done by using Depth First Search, but this is never the less a good
     * exercise for with Breadth First Search. The struggle with Breadth First search
     * is preserving the path. We accomplish this by creating a new Object, that stores
     * the TreeNode and the accumulated sum at that node. Each node, therefore presents
     * a given path, and the length of that given path.
     *
     * @param root tree node
     * @param target target to sum to;
     * @return
     */
    public boolean pathSumWithBFS(TreeNode<Integer> root, int target) {

        Queue<TreeCell> queue = new LinkedList<>();
        queue.add(new TreeCell(root, root.getValue()));

        while(!queue.isEmpty()) {
            TreeCell treeCell = queue.poll();

            if(treeCell.node.getLeft() == null && treeCell.node.getRight() == null && treeCell.sum == target) {
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

    public ArrayList<ArrayList<TreeNode<Integer>>> pathWithDfsWrapper(TreeNode<Integer> root, int target) {

        ArrayList<ArrayList<TreeNode<Integer>>> output = new ArrayList<>();
        ArrayList<TreeNode<Integer>> candidate = new ArrayList<>();

        pathWithDfs(root,  target,  output, candidate);
        return output;
    }


    public void pathWithDfs(TreeNode<Integer> root, int target, ArrayList<ArrayList<TreeNode<Integer>>> output, ArrayList<TreeNode<Integer>> candidate) {

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
        } else if (isLeafNode &&  (result != 0)) {
            candidate.remove(candidate.size() - 1);
        } else if (!isLeafNode && (result <= 0)) {
            candidate.remove(candidate.size() - 1);
        }

        pathWithDfs(root.getLeft(), result, output, candidate);
        /**
         * this happens after you have explored the left and are at the root
         */
        pathWithDfs(root.getRight(), result, output, candidate);

        /**
         * After exploring the right, there is no more tree, so any code after the line above
         * is what your code executes as it moves back up the tree.
         */
        int removeRoot = candidate.indexOf(root);
        if (removeRoot > 0) {
            candidate.remove(removeRoot);
        }
    }

    public ArrayList<ArrayList<TreeNode<Integer>>> pathSumWithBFSOutput(TreeNode<Integer> root, int target) {
        ArrayList<ArrayList<TreeNode<Integer>>> output = new ArrayList<>();
        ArrayList<TreeNode<Integer>> candidate = new ArrayList<>();
         pathSumWithBFS( root, target,  output, candidate);
         return output;
    }

    public void pathSumWithBFS(TreeNode<Integer> root, int target, ArrayList<ArrayList<TreeNode<Integer>>> output, ArrayList<TreeNode<Integer>> candidate) {

        Queue<TreeCell> queue = new LinkedList<>();
        queue.add(new TreeCell(root, root.getValue()));

        while(!queue.isEmpty()) {
            TreeCell treeCell = queue.poll();
            candidate.add(treeCell.node);

            boolean isLeafNode = treeCell.node.getLeft() == null && treeCell.node.getRight() == null;

            if(isLeafNode && treeCell.sum == target) {
                output.add((ArrayList<TreeNode<Integer>>) candidate.clone());
            } else if (isLeafNode && (treeCell.sum != 0)) {
                candidate.remove(candidate.size() - 1);
            } else if (!isLeafNode && (treeCell.sum <= 0)) {
                candidate.remove(candidate.size() - 1);
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
    }

    public class MatchCounter {
        int count = 0;
        int max = 0;
        TreeNode<Integer> matchingNode = null;

        public void increment() {
            count++;
            if (count > max) {
                max = count;
            }
        }
    }

    public int longestUniValue(TreeNode<Integer> root) {
        MatchCounter counter = new MatchCounter();
        counter.matchingNode = root;
        longestUniValue(root, counter);
        return counter.max;
    }

    public void longestUniValue(TreeNode<Integer> root, MatchCounter counter) {
        if (root == null) {
            return;
        }

        if (root.getLeft() != null && root.getLeft().getValue() == root.getValue()) {
            if (counter.matchingNode.getValue() != root.getValue()) {
                counter.matchingNode = root;
                counter.count = 0;
                counter.increment();
            } else {
                counter.increment();
            }
        }

        longestUniValue(root.getLeft(), counter);

        if (root.getRight() != null && root.getRight().getValue() == root.getValue()) {
            if (counter.matchingNode.getValue() != root.getValue()) {
                counter.matchingNode = root;
                counter.count = 0;
                counter.increment();
            } else {
                counter.increment();
            }
        }
        longestUniValue(root.getRight(), counter);
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

//    class Solution {
//
//        public int longestUnivaluePath(TreeNode root) {
//            int[] max = new int[1];
//            longestPath(root, max);
//            return max[0];
//        }
//
//        private int longestPath(TreeNode root, int[] max){
//            if(root == null){
//                return 0;
//            }
//            int left = longestPath(root.left, max);
//            int right = longestPath(root.right, max);
//            if(root.left != null && root.left.val == root.val && root.right != null && root.right.val == root.val){
//                max[0] = Math.max(max[0], left + right + 2);
//                left = Math.max(left, right) + 1;
//            }else if(root.left != null && root.left.val == root.val){
//                left = left + 1;
//            }else if(root.right != null && root.right.val == root.val){
//                left = right + 1;
//            }else{
//                return 0;
//            }
//            max[0] = Math.max(max[0], left);
//            return left;
//        }
//    }




    //               5
//              / \
//             4   5
//            / \   \
//           1   1   5
// output: 2

//               5
//              / \
//             4   5
//            / \   \
//           1   4   5
// output: 2


    //               5
    //             /
    //             5
    //           /  \
    //           1   4
    //          /
    //         1
//output: 1

//               1
//              / \
//             4   5
//            / \   \
//           4   4   5
// output: 2

//               5
//              / \
//             4   5
//            / \   \
//           4   4   5
// output: 2

//               5
//              / \
//             4   5
//            / \   \
//           4   4   5
//                   / \
//                  5  5
// output: 4
}
