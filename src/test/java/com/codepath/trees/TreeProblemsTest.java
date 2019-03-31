package test.java.com.codepath.trees;

import main.java.com.codepath.objects.TreeNode;
import main.java.com.codepath.trees.TreeProblems;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TreeProblemsTest {

    public TreeProblems testObj = null;

    @Before
    public void setUp() {
        testObj = new TreeProblems();
    }


    @Test
    public void testMergeBst() {
        TreeNode<Integer> aRoot = getATree();
        TreeNode<Integer> bRoot = getAnotherTree();

        testObj.inOrderRecTraversal(aRoot);
        System.out.println();
        testObj.inOrderRecTraversal(bRoot);
        System.out.println();

        TreeNode<Integer> root = testObj.mergeBSTs(aRoot, bRoot);
        testObj.inOrderRecTraversal(root);
        System.out.println();
        assertTrue("This tree should be a binary search tree", testObj.isBstInOrderPrevious(root));

    }

    @Test
    public void testCreateBTreefromInorderAndPreOrder() {
        TreeNode<Character>[] preOrder = new TreeNode[]{
            new TreeNode<Character>('A'),
            new TreeNode<Character>('B'),
            new TreeNode<Character>('D'),
            new TreeNode<Character>('E'),
            new TreeNode<Character>('C'),
            new TreeNode<Character>('F')};

        TreeNode<Character>[] inOrder = new TreeNode[]{
            new TreeNode<Character>('D'),
            new TreeNode<Character>('B'),
            new TreeNode<Character>('E'),
            new TreeNode<Character>('A'),
            new TreeNode<Character>('F'),
            new TreeNode<Character>('C')};

        TreeNode<Character> root = testObj.createBTreeFromInorderAndPreOrder(preOrder, inOrder);
        TreeNode[] inOrderResult = testObj.getInOrderNodes(root);
        TreeNode[] preOrderResult = testObj.getPreOrderNodes(root);

        testBstCreation(inOrderResult, inOrder);
        testBstCreation(preOrderResult, preOrder);
    }

    @Test
    public void testCreateBTreefromInorderAndPostOrder() {
        TreeNode<Character>[] postOrder = new TreeNode[]{
            new TreeNode<Character>('D'),
            new TreeNode<Character>('F'),
            new TreeNode<Character>('E'),
            new TreeNode<Character>('B'),
            new TreeNode<Character>('G'),
            new TreeNode<Character>('L'),
            new TreeNode<Character>('J'),
            new TreeNode<Character>('K'),
            new TreeNode<Character>('H'),
            new TreeNode<Character>('C'),
            new TreeNode<Character>('A')
        };

        TreeNode<Character>[] inOrder = new TreeNode[]{
            new TreeNode<Character>('D'),
            new TreeNode<Character>('B'),
            new TreeNode<Character>('F'),
            new TreeNode<Character>('E'),
            new TreeNode<Character>('A'),
            new TreeNode<Character>('G'),
            new TreeNode<Character>('C'),
            new TreeNode<Character>('L'),
            new TreeNode<Character>('J'),
            new TreeNode<Character>('H'),
            new TreeNode<Character>('K')
        };

        TreeNode<Character> root = testObj.createBTreeFromInorderAndPostOrder(postOrder, inOrder);
        TreeNode[] inOrderResult = testObj.getInOrderNodes(root);
        TreeNode[] postOrderResult = testObj.getPostOrderNodes(root);

        testBstCreation(inOrderResult, inOrder);
        testBstCreation(postOrderResult, postOrder);
    }

    @Test
    public void testCreateBTreefromPostOrderAndPreOrder() {
        TreeNode<Character>[] postOrder = new TreeNode[]{
            new TreeNode<Character>('G'),
            new TreeNode<Character>('K'),
            new TreeNode<Character>('H'),
            new TreeNode<Character>('D'),
            new TreeNode<Character>('B'),
            new TreeNode<Character>('E'),
            new TreeNode<Character>('F'),
            new TreeNode<Character>('C'),
            new TreeNode<Character>('A')
        };

        TreeNode<Character>[] preOrder = new TreeNode[]{
            new TreeNode<Character>('A'),
            new TreeNode<Character>('B'),
            new TreeNode<Character>('D'),
            new TreeNode<Character>('G'),
            new TreeNode<Character>('H'),
            new TreeNode<Character>('K'),
            new TreeNode<Character>('C'),
            new TreeNode<Character>('E'),
            new TreeNode<Character>('F')
        };

        TreeNode<Character> root = testObj.createBTreeFromPreOrderAndPostOrder(preOrder, postOrder);
        TreeNode[] preOrderNodes = testObj.getPreOrderNodes(root);
        TreeNode[] postOrderNodes = testObj.getPostOrderNodes(root);

        testBstCreation(preOrderNodes, preOrder);
        testBstCreation(postOrderNodes, postOrder);
    }

    private void testBstCreation(TreeNode[] array1, TreeNode[] array2) {
        if (array1 == null || array2 == null) {
            throw new IllegalArgumentException("both arrays should be non null");
        }

        if (array1.length != array2.length) {
            throw new IllegalArgumentException("both arrays should be equal");
        }

        if (!array1[0].getClass().equals(array2[0].getClass())) {
            throw new IllegalArgumentException("both arrays should be the same type");
        }

        for (int i = 0; i < array1.length; i++) {
            assertTrue(String.format("Values should be equal %s, %s, %s", array1[i].getValue(), array2[i].getValue(), String.valueOf(i)), array1[i].getValue().equals(array2[i].getValue()));
        }
    }


    @Test
    public void testTurnUpsideDown() {
        TreeNode<Integer> aRoot = new TreeNode<Integer>(1);
        TreeNode<Integer> two = new TreeNode<Integer>(2);
        TreeNode<Integer> three = new TreeNode<Integer>(3);

        aRoot.setLeft(two);
        aRoot.setRight(three);

        TreeNode<Integer> four = new TreeNode<Integer>(4);
        TreeNode<Integer> five = new TreeNode<Integer>(5);

        two.setLeft(four);
        two.setRight(five);

        TreeNode<Integer> six = new TreeNode<Integer>(6);
        TreeNode<Integer> seven = new TreeNode<Integer>(7);

        four.setLeft(six);
        four.setRight(seven);

        TreeNode<Integer> result = testObj.turnTreeUpsideDown(aRoot);
        testObj.inOrderRecTraversal(result);
    }

    @Test
    public void testTurnUpsideDownIter() {
        TreeNode<Integer> aRoot = new TreeNode<Integer>(1);
        TreeNode<Integer> two = new TreeNode<Integer>(2);
        TreeNode<Integer> three = new TreeNode<Integer>(3);

        aRoot.setLeft(two);
        aRoot.setRight(three);

        TreeNode<Integer> four = new TreeNode<Integer>(4);
        TreeNode<Integer> five = new TreeNode<Integer>(5);

        two.setLeft(four);
        two.setRight(five);

        TreeNode<Integer> six = new TreeNode<Integer>(6);
        TreeNode<Integer> seven = new TreeNode<Integer>(7);

        four.setLeft(six);
        four.setRight(seven);

        TreeNode<Integer> result = testObj.turnTreeUpsideDownIter(aRoot);
        testObj.inOrderRecTraversal(result);
    }

    private TreeNode<Integer> getATree() {
        TreeNode<Integer> aRoot = new TreeNode<Integer>(4);
        TreeNode<Integer> aRootTwo = new TreeNode<Integer>(2);
        TreeNode<Integer> aRootSix = new TreeNode<Integer>(6);

        aRoot.setLeft(aRootTwo);
        aRoot.setRight(aRootSix);

        TreeNode<Integer> aRootTwoLeft = new TreeNode<Integer>(1);
        TreeNode<Integer> aRootTwoRight = new TreeNode<Integer>(3);

        aRootTwo.setLeft(aRootTwoLeft);
        aRootTwo.setRight(aRootTwoRight);

        TreeNode<Integer> aRootSixLeft = new TreeNode<Integer>(5);
        TreeNode<Integer> aRootSixRight = new TreeNode<Integer>(7);

        aRootSix.setLeft(aRootSixLeft);
        aRootSix.setRight(aRootSixRight);
        return aRoot;
    }

    private TreeNode<Integer> getAnotherTree() {
        TreeNode<Integer> bRoot = new TreeNode<Integer>(20);
        TreeNode<Integer> bRootTwo = new TreeNode<Integer>(10);
        TreeNode<Integer> bRootSix = new TreeNode<Integer>(30);

        bRoot.setLeft(bRootTwo);
        bRoot.setRight(bRootSix);

        TreeNode<Integer> bRootTwoLeft = new TreeNode<Integer>(5);
        TreeNode<Integer> bRootTwoRight = new TreeNode<Integer>(15);

        bRootTwo.setLeft(bRootTwoLeft);
        bRootTwo.setRight(bRootTwoRight);

        TreeNode<Integer> bRootSixLeft = new TreeNode<Integer>(25);
        TreeNode<Integer> bRootSixRight = new TreeNode<Integer>(35);

        bRootSix.setLeft(bRootSixLeft);
        bRootSix.setRight(bRootSixRight);
        return bRoot;
    }
}


    /*
Suppose we have some input data describing a graph of relationships between parents and children over multiple generations. The data is formatted as a list of (parent, child) pairs, where each individual is assigned a unique integer identifier.

For example, in this diagram, 3 is a child of 1 and 2, and 5 is a child of 4:

1   2   4
 \ /   / \
  3   5   8
   \ / \   \
    6   7   10

Write a function that takes this data as input and returns two collections: one containing all individuals with zero known parents, and one containing all individuals with exactly one known parent.

Sample output (pseudodata):

findNodesWithZeroAndOneParents(parentChildPairs) => [
  [1, 2, 4],    // Individuals with zero parents
  [5, 7, 8, 10] // Individuals with exactly one parent
]

*/

    /*
     * To execute Java, please define "static void main" on a class
     * named Solution.
     *
     * If you need more classes, simply define them inline.
     */

//    class Solution {
//
//        public static List<List<Integer>> findNodesWithZeroAndOneParents(int[][] parentChildPairs) {
//
//
//            List<List<Integer>> results = new ArrayList<>();
//            Set<Integer> nodesWithZeroParents = findNodesWithZeroParents(parentChildPairs);
//
//            results.add(Arrays.asList(nodesWithZeroParents.toArray(new Integer[0])));
//            Set<Integer> nodesWithExactlyOneParent = findNodesWithOneParent(parentChildPairs);
//
//            results.add(Arrays.asList(nodesWithExactlyOneParent.toArray(new Integer[0])));
//
//            return results;
//        }
//
//        public static Set<Integer> findNodesWithZeroParents(int[][] parentChildPairs) {
//
//            Set<Integer> results = new HashSet<>();
//            for (int i = 0; i < parentChildPairs.length; i++) {
//                int parent = parentChildPairs[i][0];
//                for (int j = 0; j < parentChildPairs.length; j++) {
//                    if (parent == parentChildPairs[j][1]) {
//                        break;
//                    } else if (j == parentChildPairs.length - 1) {
//                        results.add(parent);
//                    }
//                }
//            }
//
//            return results;
//
//        }
//
//
//        public static Set<Integer> findNodesWithOneParent(int[][] parentChildPairs) {
//            Set<Integer> oneChildParents = new HashSet<>();
//            Set<Integer> repeatParents = new HashSet<>();
//
//            for (int i = 0; i < parentChildPairs.length; i++) {
//
//                if (oneChildParents.contains(parentChildPairs[i][1])) {
//                    oneChildParents.remove(parentChildPairs[i][1]);
//                    repeatParents.add(parentChildPairs[i][1]);
//                } else {
//                    oneChildParents.add(parentChildPairs[i][1]);
//                }
//            }
//
//            return oneChildParents;
//        }
//
//
//
//
//        public static void main(String[] args) {
//            int[][] parentChildPairs = new int[][] {
//                {1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7},
//                {4, 5}, {4, 8}, {8, 10}
//            };
//
//            System.out.println(findNodesWithZeroAndOneParents(parentChildPairs));
//
//        }
//    }






