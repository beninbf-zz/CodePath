package main.java.com.codepath.dynamicprogramming;

import java.util.Arrays;
import java.util.Set;

public class DynamicProgramming {

    public int findMinPath(int[][] grid) {

        if (grid == null) {
            return -1;
        }
        if(grid[0].length == 1 && grid.length == 1) {
            return grid[0][0];
        }
        return findMinPath(grid, 0, 0);
    }

    private int findMinPath(int[][] grid, int down, int right) {
        if (down >= grid.length || right >= grid[0].length) {
            return -1;
        }

        int downPath = findMinPath(grid, down + 1, right);
        int rightPath = findMinPath(grid, down, right + 1);

        if (downPath > 0 && rightPath > 0) {
            return Math.min(downPath + grid[down][right], rightPath + grid[down][right]);
        } else if (downPath > 0) {
            return downPath + grid[down][right];
        } else if (rightPath > 0) {
            return rightPath + grid[down][right];
        } else {
            return grid[down][right];
        }
    }

    public int findMinPathDP(int[][] grid) {
        if (grid == null) {
            return -1;
        }
        if(grid[0].length == 1 && grid.length == 1) {
            return grid[0][0];
        }

        int[][] aux = new int[grid.length][grid[0].length];

        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
               if (i - 1 < 0 && j - 1 < 0) {
                   aux[i][j] += grid[i][j]; // sum current value
               } else if (i - 1 < 0) {
                   aux[i][j] += grid[i][j] + aux[i][j - 1]; // sum value from above
               } else if (j - 1 < 0) {
                   aux[i][j] += grid[i][j] + aux[i - 1][j]; // sum value from left
               } else {
                   // take min between value from above and value from left
                   aux[i][j] += Math.min(aux[i][j - 1] + grid[i][j], aux[i - 1][j] + grid[i][j]);
               }
            }
        }
        print2DArray(aux);
        return aux[rows - 1][cols - 1];
    }

    public void print2DArray(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            System.out.print("[ ");
            for (int j= 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.print("]");
            System.out.println();
        }
    }

    public int findMaxScoreRec(int[][] A) {
        int[] numberOfCalls = new int[1];
        int result = findMaxScoreRecUtil(A, 0, numberOfCalls);
        System.out.println("Number of calls of naive recursion="+numberOfCalls[0]);
        return result;
    }

    private int findMaxScoreRecUtil(int[][] A, int start, int[] numberOfCalls) {
        if (start >= A[0].length) {
            return 0;
        }
        numberOfCalls[0]++;
        int cols = A[0].length;
        int maxScore = 0;
        int colMax = 0;
        for (int i = start; i < cols; i++) {
            maxScore = findMaxScoreRecUtil(A, start + 2, numberOfCalls);
            colMax = Math.max(A[0][start], A[1][start]);
            maxScore = Math.max(colMax + maxScore, maxScore);
        }

        return maxScore;
    }

    public int findMaxScoreDP(int[][] A) {
        int[] numberOfCalls = new int[1];
        int[] memo = new int[A[0].length];
        Arrays.fill(memo, -1);
        findMaxScoreRecUtilDP(A, 0, memo, numberOfCalls);
        System.out.println("Number of calls of recursion + memoization="+numberOfCalls[0]);
        return Math.max(memo[0], memo[1]);
    }

    private int findMaxScoreRecUtilDP(int[][] A, int start, int[] memo, int[] numberOfCalls) {
        if (start >= A[0].length) {
            return 0;
        }

        if (memo[start] > 0) {
            return memo[start];
        }

        numberOfCalls[0]++;
        int cols = A[0].length;
        int maxScore = 0;
        int colMax = 0;
        for (int i = start; i < cols; i++) {
            // maxScore is literally the lastSum
            maxScore = findMaxScoreRecUtilDP(A, start + 2, memo, numberOfCalls);
            colMax = Math.max(A[0][start], A[1][start]);
            maxScore = Math.max(colMax + maxScore, maxScore);
            memo[start] = maxScore;
        }
        return memo[start];
    }

    public int findMaxScoreBottomUp(int[][] A) {
        int[] numberOfCalls = new int[1];
        int[] memo = new int[A[0].length];
        Arrays.fill(memo, -1);
        return Math.max(memo[0], memo[1]);
    }

    public boolean wordBreak(String str, Set<String> dictionary) {
        wordBreakRec(str, dictionary);
        return dictionary.isEmpty();
    }

    public void wordBreakRec(String str, Set<String> dictionary) {
        if (str.isEmpty() || dictionary.isEmpty()) {
            return;
        }
        int length = str.length();
        for (int i = 1; i <= length; i++) {
            if (dictionary.contains(str.substring(0, i))) {
                dictionary.remove(str.substring(0, i));
            }
        }
        String remaining = str.substring(1, length);
        wordBreakRec(remaining, dictionary); // results
    }


    public void printSubString(String workTodo, String workDone) {
        if (workTodo.isEmpty()) {
            System.out.println(workDone);
        }

//        int
//        printSubString(workTodo.substring(0, workDone.length() - 1), workTodo.substring(0, ))
//        String remaining = s.substring(1, length);
//        return wordBreakRec(remaining, results);
    }


}
