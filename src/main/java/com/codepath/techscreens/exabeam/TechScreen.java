package main.java.com.codepath.techscreens.exabeam;

import java.util.List;

public class TechScreen {
    /**
     * This tech screen was from a company called Exabeam. The problem statement is as follows
     *
     * Casey has a square image made up of black and white pixels represented as 0 and 1 respectively.
     * As part of an image analysis process, Casey needs to determine the size of the largest square
     * area of white pixels.  Given a 2-dimensional square matrix that represents the image,
     * write a function to determine the length of a side of the largest square area made up
     * of white pixels.
     *
     * For example, the n x n = 5 x 5 matrix of pixels is represented as
     * arr = [[1,1,1,1,1], [1,1,1,0,0], [1,1,1,0,0], [1,1,1,0,0], [1,1,1,1,1].
     * A diagram of the matrix is:
     *
     * 1 1 1 1 1
     * 1 1 1 0 0
     * 1 1 1 0 0
     * 1 1 1 0 0
     * 1 1 1 1 1
     *
     * The largest square sub-matrix is 3 x 3 in size starting at position (0, 0), (1, 0) or (2, 0).
     * The expected return value is 3.
     *
     * So I had a correct idea on how to solve this problem. With the problem statement asking for
     * the largest sub matrix, I realized one approach was to find all sub matrices of square dimensions
     * and then check each one to see if they all contained 1. For the sub matrices that contained
     * all ones, I would simply record the dimension length, and update it if a subsequent dimension
     * was found to be longer.
     *
     * I could tell that this would not be optimal, but it should work. Considering it was an exhaustive
     * search, the problem called for recursion. This is where I fumbled, and I neglected to first
     * get a clear understanding of the recursive structure of the problem.
     *
     * I have to remember one thing for problems that are recursive in nature, and that is
     * TO DRAW THE FUCKING RECURSION TREE FIRST!!!!!!
     *
     * When you do, that, writing the code afterwards becomes much cleaner.
     *
     * Essentially this solution works, by starting at cell (0, 0), it then generates all square sub matrices
     * of length, 1, 2, 3, up to the bounds of the matrix.
     *
     * For each sub matrix a check is performed to determine if the sub matrix contains all ones or not.
     * Drawing the recursion tree, and understanding the base cases upon which to return, which is
     * if the startRow or startCol plus the dimension im checking is outside of the bounds, is all that
     * is necessary.
     *
     * In this problem we start with dimension 0, knowing it won't count, but that's no problem, as proceed
     * to dimension, as the cell (0,0) will be counted because 0 < 1.
     *
     * I'm pretty sure this problem is a prime candidate for a dynamic programming solution, because
     * there would appear to be a lot of overlapping sub problems.
     *
     * We will try to the problem again later, as DP problem.
     *
     * RUNTIME: the runtime of the problem is quite heavy. For every call, we check
     * the sub matrix given the dimension. That means a check of O(n^2) per recursive call.
     * Every recursive call, spawns n new calls, where n is the dimension of the square.
     * And the height of the recursion tree is n, where n is the dimension of the square matrix.
     * So I think the runtime is n^2 * n * n
     *
     * SPACE COMPLEXITY: O(n^2) to store the 2-D array
     *
     *
     * @param arr 2-D array of zeros and ones
     * @return int the largest dimension of a sub matrix containing all ones
     */
    public int largestSquareMatrix(List<List<Integer>> arr) {

        int rows = arr.size();
        int cols = arr.get(0).size();

        int[][] array = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = arr.get(i).get(j);
            }
        }
        int[] largestDimension = new int[1];
        largestMatrixHelper(array, 0, 0, 0, largestDimension);
        return largestDimension[0];
    }

    private void largestMatrixHelper(int[][] array, int row, int col, int dimension, int[] largestDimension) {
        if (row + dimension > array.length) {
            return;
        }

        if (col + dimension > array[0].length) {
            return;
        }

        if (hasAllOnes(array, row, col, dimension)) {
            if (dimension > largestDimension[0]) {
                largestDimension[0] = dimension;
            }
        }

        for (int i = row; i < array.length; i++) {
            for (int j = col; j < array[0].length; j++) {
                largestMatrixHelper(array, i, j,  dimension + 1, largestDimension);
            }
        }
    }

    public int largestSquareMatrixOptimal(int[][] input) {
        int[] max = new int[1];
        largestMatrixHelperEff(input, input.length - 1, input[0].length - 1, max);
        return max[0];
    }

    private int largestMatrixHelperEff(int[][] input, int row, int col, int[] max) {
        if (row == 0 || col == 0) {
            return input[row][col];
        }

        int left = largestMatrixHelperEff(input, row, col - 1, max);
        int up = largestMatrixHelperEff(input, row - 1, col, max);
        int diagonal = largestMatrixHelperEff(input, row - 1, col - 1, max);

        int temp = 0;
        if (input[row][col] == 1) {
            temp = 1 + Math.min(Math.min(left, up), diagonal);
        }
        max[0] = Math.max(max[0], temp);
        return temp;
    }

    public int largestSquareSubMatrixDp(int[][] input) {
        int[][] table = new int[input.length][input[0].length];

        for (int i = 0; i < table[0].length; i++) {
            table[0][i] = input[0][i];
        }

        for (int i = 0; i < table.length; i++) {
            table[i][0] = input[i][0];
        }

        int maxDimension = 0;
        int temp = 0;
        for (int i = 1; i < input.length; i++) {
            for (int j = 1; j < input[0].length; j++) {
                if (input[i][j] == 1) {
                    temp = 1 + Math.min(Math.min(table[i][j - 1], table[i - 1][j]), table[i - 1][j - 1]);
                    table[i][j] = temp;
                    maxDimension = Math.max(temp, maxDimension);
                } else {
                    table[i][j] = 0;
                }
            }
        }
        return maxDimension;
    }

    public int largestSubMatrixOfOnes(int[][] input) {
        int[] max = new int[1];
        largestSubMatrixOfOnesHelper(input, 0, 0, max);
        return max[0];
    }

    private int largestSubMatrixOfOnesHelper(int[][] array, int row, int col, int[] max) {
        if (row == array.length || col == array[0].length) {
            return Integer.MAX_VALUE;
        }

        if (row == array.length - 1 || col == array[0].length - 1) {
            return array[row][col];
        }
        int right = largestSubMatrixOfOnesHelper(array, row, col + 1, max);
        int down = largestSubMatrixOfOnesHelper(array, row  + 1, col, max);
        int diagonal = largestSubMatrixOfOnesHelper(array, row + 1, col + 1, max);

        int temp = 0;
        if (array[row][col] == 1) {
            temp = 1 + Math.min(Math.min(right, down), diagonal);
            max[0] = Math.max(temp, max[0]);
        }
        return temp;
    }

    private boolean hasAllOnes(int[][] array, int startRow, int startCol, int dimension) {
        for (int i = startRow; i < startRow + dimension; i++) {
            for (int j = startCol; j < startCol + dimension; j++) {
                if (array[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
