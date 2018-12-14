package main.java.com.codepath.trees;

import main.java.com.codepath.objects.NTreeNode;
import main.java.com.codepath.objects.TreeNode;
import org.omg.CORBA.TRANSACTION_MODE;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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

    public boolean isBstInOrderPrevious(TreeNode<Integer> root) {
        return isBstInOrderPreviousHelper(root);
    }


    /**
     * isBstInOrderPreviousHelper - In this algorithm we are determining if a tree is
     * actually an Binary Search Tree, by keeping track of the previous node. Previously,
     * I did the algorithm in correctly, by trying to pass the previous node from
     * call stack to call, declaring the "root" as the previous node, as one tried to recurse.
     *
     * The meaning of the "previous" node, in my first in correct approach, was literally taking
     * "previous" as the previous node in the call stack. This is a mistake, because the "previous"
     * node in the call stack, or the root at each call stack, is not the actual in order predecessor.
     *
     * We I actually should have done, is keep track of the in order predecessor of every node that
     * I visit. To Do this, I need to declare the previous node as static, so that i can keep track of
     * it through recursive calls. I don't want the previous node changing values to null, AFTER it is
     * set and moves back through call stack frames. Once it is set, I want to reset the previous, or
     * in order sucessor node, only after returning from the left node in the recursion.
     *
     * In this way, once I actually make way back up the tree, through the call stack, to the actual
     * root of the tree, the in order predecessor is actually the right most child from the left
     * subtree. Which is the same as saying its the max of the left subtree. At this point, I simply
     * check to see if this in order predecessor is greater than the current root. If it is, I will
     * return false.
     *
     *
     * @param root
     * @return
     */

    private boolean isBstInOrderPreviousHelper(TreeNode<Integer> root) {
        if (root == null) return true;

        if (!isBstInOrderPreviousHelper(root.getLeft())) {
            return false;
        }

        if (previous != null && previous.getValue().compareTo(root.getValue()) > 0) {
            return false;
        }
        previous = root;

        if(!isBstInOrderPreviousHelper(root.getRight())) {
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
     *
     *                   50
     *                 /   \
     *                /     \
     *              40      60
     *            /   \    /  \
     *           /    \   /    \
     *          30    47 42    65
     *
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
        if(!isBstInOrderPrevious1(rightChild, root)) {
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
    *  The space complexity is O(n) twice with the call stack and the list. This can be
    *  improved upon.
    *
    */
    public boolean isBstInOrderList(TreeNode<Integer> root) {
        ArrayList<TreeNode<Integer>> list = new ArrayList<>();
        isBstInOrderList(root, list);
        //System.out.println("From In order: "  + list);
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
        TreeCell parent;
        int sum;

        TreeCell(TreeNode node, int sum) {
            this.node = node;
            this.sum = sum;
        }

        public String toString() {
            String parentValue = (parent != null) ? parent.node.getValue().toString() : "";
            return node.toString() + " parent: " + parentValue + " :sum="+sum;
        }

        public String getPathRepresentation() {
            return node.getValue().toString();
        }
    };

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
     * @param root tree node
     * @param target target to sum to;
     * @return
     */
    public boolean pathSumWithBfs(TreeNode<Integer> root, int target) {

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

    public ArrayList<ArrayList<TreeNode<Integer>>> findPathsThatSumWithDfs(TreeNode<Integer> root, int target) {

        ArrayList<ArrayList<TreeNode<Integer>>> output = new ArrayList<>();
        ArrayList<TreeNode<Integer>> candidate = new ArrayList<>();

        findPathsThatSumWithDfs(root,  target,  output, candidate);
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
        } else if (isLeafNode &&  (result != 0)) {
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

        for(TreeCell cell: paths) {
            ArrayList<TreeCell> path = new ArrayList<>();
            path.add(cell);
            while(cell.parent != null) {
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

        while(!queue.isEmpty()) {
            TreeCell treeCell = queue.poll();

            boolean isLeafNode = treeCell.node.getLeft() == null && treeCell.node.getRight() == null;

            if(isLeafNode && treeCell.sum == target) {
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

    /**
     * findSingleValueTrees - Count the number of unival trees. The strategy will
     * be to get to the bottom of the tree, and then start counting as we work our
     * way back up the call stack. This is the approach because we know that all
     * leaf nodes represent unival trees. So as the recursion moves back up the stack
     * we will simply check for a leaf node and implement our counter.
     *
     * If we get to a node that only has one child, we just have to check to see of the root
     * matches the value in its one child. If it does, then increment the count. For one
     * child trees that means checking to see if the one child is a left child or right child.
     *
     * For cases when there is two children, we only increment the count of the right and left
     * children are the same as the root.
     * @param n
     * @return
     */
    public int findSingleValueTrees(TreeNode n) {
        int[] count = new int[1];
        findSingleValueTreesHelper(n, count);
        return count[0];
    }

    public TreeNode findSingleValueTreesHelper(TreeNode<Integer> root, int[] count) {
        if (root == null) {
            return null;
        }

        TreeNode left = findSingleValueTreesHelper(root.getLeft(), count);

        int val = root.getValue();

        TreeNode right = findSingleValueTreesHelper(root.getRight(), count);

        //child case
        if (left == null && right == null) {
            count[0]++;
        } else if (left != null && right == null) { // one child case
            if (left.getValue().equals(root.getValue())) {
                count[0]++;
            }
        } else if (left == null && right != null) {
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
     * postOrderRecTraversal - classic post order traversal. Visit the children nodes first
     * and then the root.
     *
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
     *
     * The normal post order traversal is
     *
     * left
     * right
     * root
     *
     * In stead what we will do, is effectively
     *
     * root
     * right
     * left
     *
     * as we move nodes into our "rootStack". What this looks like is the following
     *
     *                    rightChild
     * root               leftChild
     * ---------          --------
     * root stack         child stack
     *
     * we first add our root of the tree to the root stack, we then add its children to the child
     * stack. ****ITS IMPORTANT**** to add the left child to the child stack before the right child,
     * because when the top element is popped off, it will give us our, root-->right-->left reversal
     * that we are looking for.
     *
     * We then, pop the top element off of the child stack, in this case the roots right child,
     * and then we add it to the root stack. We then check the children of the node we just popped,
     * and then add them to the child stack.
     *
     * Again, we pop off of the top node, which if there is a right child present will be whichever roots
     * right child. We keep doing this, and by the time we get to leaf nodes, we will have no  more children
     * to add. We keep popping off the top node, add it to the root stack, until our child stack is empty.
     * Each element we pop off of the top, will go on the top of the root stack, in reverse order.
     *
     * When the child stack is empty, we then just pop off each element from the root stack, and print each
     * one.
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

        while(!rootStack.isEmpty()) {
            TreeNode<Integer> node = rootStack.pop();
            System.out.print(node.getValue() + " ");
        }
    }

    /**
     * IsBstInOrderIterStack - The idea for the inOrder traversal using a stack is as follows.
     * If we are doing an in order traversal we must first move all the way to the left in the tree
     * using a stack to store the nodes. Because we are doing this iteratively as opposed to recursiou
     * we will mimic recursion, suing an object stack as opposed to the call stack. The call stack helps
     * because it allows us to remember where we previously were, and then back track. To accomplish
     * backtracking without recursion we will back use of a stack.
     *
     * So, move all they way to the left, adding each node to a stack. When you can't any further left,
     * we must then start back tracking. How do we back track without using recursion, and instead using the
     * object Stack. Well, we start popping nodes off of the stack. When we start doing this, popping our left
     * nodes off of the stack, we have to keep in mind, that to get the next in order successor, we have to look
     * at the right subtree. But we don't simply want the first right child. The immediate right child, is ONLY
     * the next in order successor if the right node has no children. If the right child does indeed have children
     * then we find the left most descendant of the right subtree, for that is the actual next in order successor.
     *
     * Another way of putting that, is once we have popped off a left node, we must find the minimum of the right
     * subtree. The minimum of the right subtree is the next in order successor of our current root. Its also the
     * left most descendant of our right subtree. So min of right subtree == in order successor of root ==
     * left most descendant of our right subtree.
     *
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
        while(current != null) {
            stack.push(current);
            current = current.getLeft();
        }

        TreeNode<Integer> previous = null;

        while(!stack.isEmpty()) {
            TreeNode<Integer> node = stack.pop();
            if (previous != null && previous.getValue().compareTo(node.getValue()) > 0) {
                return false;
            }
            previous = node;
            // We must now check the right subtree
            if (node.getRight() != null) {
                current = node.getRight();
                // if the right subtree has children is must find the next in Order successor
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
     * inOrderRecTraversal - classic in order recursive traversal. Visit the left
     * the root, and then the right.
     *
     * left
     * root
     * right
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
    public void inOrderIterTraversal(TreeNode<Integer> root) {
        Stack<TreeNode<Integer>> stack = new Stack<TreeNode<Integer>>();

        TreeNode<Integer> current = root;
        while(current != null) {
            stack.push(current);
            current = current.getLeft();
        }

        while(!stack.isEmpty()) {
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
     *
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
     * preOrderIterTraversal - To perform a pre-order traversal iteratively, we need to simulate
     * the call stack that occurs when done recursively. Instead of using the call stack we will use
     * the object stack. Just like the recursive implementation, before we make subsequent calls,
     * i.e. adding to our call stack, we will print the root, then visit the children.
     *
     * The iterative solution using a stack performs in the same way. Before we add a left node to
     * the stack we will print the node, in this case intially the root, and then left child,
     * and then that nodes, left child, so on and so on.
     *
     * In the recursive solution, at this point, we now have nodes in the call stack. As the recursion
     * starts to back track, we must then check if the nodes at each stack frame have right children
     * print.
     *
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
                System.out.print(node.getValue() + " ");
                current = node.getRight();
                while (current != null) {
                    System.out.print(current.getValue());
                    stack.push(current);
                    current = current.getLeft();
                }
            }
        }
    }

    public int findKthSmallest(TreeNode<Integer> root, int k) {
        int[] count = new int[1];
        count[0] = k;
        int[] ans = new int[1];
        findKthSmallestHelper(root, count, ans);
        return ans[0];
    }

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

    public TreeNode<Integer> buildBst(int[] a) {
        return buildBstHelper(a, 0, a.length);
    }

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

    public int findHeightOfNaryTree(NTreeNode<Integer> root) {
        if (root == null) {
            return 0;
        }

        if (root.getChildren().size() == 0) {
            return 0;
        }

        int maxHeight = 0;
        for (NTreeNode node: root.getChildren()) {
            int height = findHeightOfNaryTree(node) + 1;
            maxHeight = Math.max(maxHeight, height);
        }
        return maxHeight;
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
