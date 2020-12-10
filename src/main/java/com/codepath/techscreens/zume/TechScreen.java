package main.java.com.codepath.techscreens.zume;

import java.util.*;

public class TechScreen {
    /**
     * Karat by way of the Zume company
     *
     * Suppose we have some input data describing a graph of relationships between parents and children over multiple generations.
     * The data is formatted as a list of (parent, child) pairs, where each individual is assigned a unique integer identifier.
     * For example, in this diagram, 3 is a child of 1 and 2, and 5 is a child of 4:
     *
     *   1   2   4
     *    \ /   / \
     *     3   5   8
     *      \ / \   \
     *       6   7   10
     *
     * Write a function that takes this data as input and returns two collections: one containing all individuals with zero known parents,
     * and one containing all individuals with exactly one known parent.
     *
     * Sample output (pseudodata):
     * findNodesWithZeroAndOneParents(parentChildPairs) => [
     * [1, 2, 4],    // Individuals with zero parents
     * [5, 7, 8, 10]] // Individuals with exactly one parent
     *
     * This problem looks much more complicated that it actually is. It just involves writing two functions, one for finding nodes
     * with zero parents and the other for finding nodes with only one parent.
     *
     * The graph is in the format of
     *
     * int[][] parentChildPairs = new int[][] {
     *   {1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7},
     *   {4, 5}, {4, 8}, {8, 10}
     * };
     *
     *
     * @param parentChildPairs object we Wish to serialize
     * @return List<List<Integer>>
     */
    public List<List<Integer>> findNodesWithZeroAndOneParents(int[][] parentChildPairs) {
        List<List<Integer>> results = new ArrayList<>();
        Set<Integer> nodesWithZeroParents = findNodesWithZeroParents(parentChildPairs);

        results.add(Arrays.asList(nodesWithZeroParents.toArray(new Integer[0])));
        Set<Integer> nodesWithExactlyOneParent = findNodesWithOneParent(parentChildPairs);

        results.add(Arrays.asList(nodesWithExactlyOneParent.toArray(new Integer[0])));
        return results;
    }

    /**
     * Finding nodes with Zero parents, just means, taking each parent, from the 0th
     * column, and then checking to see if they are also present in the child column.
     *
     * This could have been made a little more efficient by keeping track nodes
     * that are present in the child column. If a node is present in the child column,
     * then we we get to that node in the parent column, then we can skip that parent
     * in our loop.
     *
     * @param parentChildPairs int[][]
     * @return set of nodes with zero parents
     */
    public Set<Integer> findNodesWithZeroParents(int[][] parentChildPairs) {
        Set<Integer> results = new HashSet<>();
        for (int i = 0; i < parentChildPairs.length; i++) {
            int parent = parentChildPairs[i][0];
            for (int j = 0; j < parentChildPairs.length; j++) {
                if (parent == parentChildPairs[j][1]) {
                    break;
                } else if (j == parentChildPairs.length - 1) {
                    results.add(parent);
                }
            }
        }
        return results;
    }


    /**
     * findNodesWithOneParent - to find nodes with one parent, we just have to
     * moved down the child column, checking each row for parents that occur
     * more than once.
     *
     * We do this by using to sets. One set for parents that are only present
     * once, and another set for repeat parents. If we find a parent, that is
     * already in our oneChildParents set, then we remove it and add it to the
     * repeatSet.
     *
     * If we find a parent currently not in the oneChild set, we check to see
     * if its been added to the repeat set, if not, then we can add it to our
     * one Child set.
     *
     * We simply return the contents of our oneChild set.
     * @param parentChildPairs int[][]
     * @return set of one child parents
     */
    public Set<Integer> findNodesWithOneParent(int[][] parentChildPairs) {
        Set<Integer> oneChildParents = new HashSet<>();
        Set<Integer> repeatParents = new HashSet<>();

        for (int i = 0; i < parentChildPairs.length; i++) {
            if (oneChildParents.contains(parentChildPairs[i][1])) {
                oneChildParents.remove(parentChildPairs[i][1]);
                repeatParents.add(parentChildPairs[i][1]);
            } else {
                if (!repeatParents.contains(parentChildPairs[i][1])) {
                    oneChildParents.add(parentChildPairs[i][1]);
                }
            }
        }
        return oneChildParents;
    }

    /**
     * Tech screen from Zume:
     *
     * This tech screen was the follow up to my Zume tech screen. It went well,
     * I had one bug, and one hint from the interview managed to help me fix it.
     *
     * Imagine we have an image. We'll represent this image as a simple 2D array where every pixel is a 1 or a 0.
     * The image you get is known to have a single rectangle of 0s on a background of 1s.
     *
     * Write a function that takes in the image and returns the coordinates of the rectangle
     * of 0's -- either top-left and bottom-right; or top-left, width, and height.
     *
     * findRectangle(image1) =>
     *    x: 3, y: 2, width: 3, height: 2
     *    2,3 3,5 -- row,column of the top-left and bottom-right corners
     *
     * @param image 2 - D array of zeros and ones
     * @return dimensions and start of zeros rectangle
     */
    public ZerosRectangle findRectangleOfZeros(int[][] image) {
        ZerosRectangle result = new ZerosRectangle();
        int rows = image.length;
        int cols = image[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (image[i][j] == 0 && result.beginning == null) {
                    result.createBeginningCell(i, j);
                } else if (image[i][j] == 0) {
                    result.width += 1;
                    if ( ((j + 1) < cols && image[i][j + 1] == 1) || (j + 1) == cols) {
                        countColumnZeros(image, i + 1, j, result);
                        return result;
                    }
                }
            }
        }
        return result;
    }

    public void countColumnZeros(int[][] image, int row, int col, ZerosRectangle result) {
        int length = image.length;
        for (int i = row; i < length; i++) {
            if (image[i][col] == 0) {
                result.height += 1;
            } else {
                return;
            }
        }
    }

}
