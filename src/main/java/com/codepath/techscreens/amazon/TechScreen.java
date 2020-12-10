package main.java.com.codepath.techscreens.amazon;

import main.java.com.codepath.techscreens.objects.Cell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TechScreen {
    /**
     * The following was a question from an online amazon assessment
     *
     * Write an algorithm to compute the minimum amount of time to put N items together
     * worker can only combine 2 items at a time. Time require to put two parts together is
     * equal to the sum of the part sizes. The size of the newly constructed part is equal to the sum
     * of the part sizes.
     *
     * I tried doing this problem using Dynamic Programming, and I think that was a mistake. DP
     * problems require overlapping sub problem and it took me too long to see, that this problem
     * DOESN"T have overlapping sub problems, because the problem space changes. We keep
     * collasping the iput array until we have two numbers, and then we return that up
     * the stack, taking the minimum sum.
     *
     * The recusion isn't even that hard, I simply spent too much time going down the incorrect approach.
     *
     * @param numOfParts int
     * @param parts list of integers
     * @return minimum time
     */
    public int minimumTime(int numOfParts, List<Integer> parts) {
        if (numOfParts == 2) {
            return parts.get(0).intValue() + parts.get(1).intValue();
        }

        int firstSmallest = findKthSmallest(parts);
        int firstSmallestValue = parts.get(firstSmallest).intValue();
        parts.set(firstSmallest, Integer.MAX_VALUE);

        int secondSmallest = findKthSmallest(parts);
        int secondSmallestValue = parts.get(secondSmallest).intValue();
        parts.set(secondSmallest, Integer.MAX_VALUE);
        int sum = firstSmallestValue + secondSmallestValue;

        List<Integer> newParts = new ArrayList<>();
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i) < Integer.MAX_VALUE) {
                newParts.add(parts.get(i));
            }
        }

        newParts.add(sum);
        return sum + minimumTime(newParts.size(), newParts);
    }

    /**
     * Finds the smallest Element.
     * @param list of integers
     * @return
     */
    public int findKthSmallest(List<Integer> list)  {
        int length = list.size();
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < length; i++) {
            Integer n = list.get(i);
            if (min > n) {
                min = n;
                index = i;
            }
        }
        return index;
    }

    /**
     * The second question from an online assessment from Amazon. A straight forward graphing
     * problem, an implementation of BFS.
     *
     * Just remember that, every neighbor cell we visit, the distance to that cell is
     * the parent distance + 1;
     *
     * When we find our destination cell, while visiting each neighbor, thats when we return
     * our shortest distance.
     * @param numRows
     * @param numCols
     * @param lot
     * @return
     */
    public int minDistanceToRemoveObstacle(int numRows, int numCols, List<List<Integer>> lot) {
        int[] rowMoves = {0, 1, 0, 1};
        int[] colMoves = {-1, 0, 1, 0};

        int endingCell = 9;
        int flatLand = 1;

        boolean[][] visited = new boolean[lot.size()][lot.get(0).size()];
        int[][] distance = new int[lot.size()][lot.get(0).size()];

        Queue<Cell> queue = new LinkedList<>();
        Cell startingCell = new Cell(0, 0);
        queue.add(startingCell);
        while (!queue.isEmpty()) {
            Cell parentCell = queue.poll();
            if (visited[parentCell.row][parentCell.col] == false) {
                visited[parentCell.row][parentCell.col] = true;
                for (int i = 0; i < 4; i++) {
                    if (isValidMove(parentCell.row + rowMoves[i], parentCell.col + colMoves[i], lot)) {
                        int nextRow = parentCell.row + rowMoves[i];
                        int nextCol = parentCell.col + colMoves[i];
                        if (lot.get(nextRow).get(nextCol) == flatLand) {
                            distance[nextRow][nextCol] = distance[parentCell.row][parentCell.col] + 1;
                            Cell neighborCell = new Cell(nextRow, nextCol);
                            queue.add(neighborCell);
                        } else if (lot.get(nextRow).get(nextCol) == endingCell) {
                            distance[nextRow][nextCol] = distance[parentCell.row][parentCell.col] + 1;
                            return distance[nextRow][nextCol];
                        }
                    }
                }
            }
        }
        return -1;
    }

    private boolean isValidMove(int row, int col, List<List<Integer>> lot) {
        return row >= 0 && row < lot.size() && col >= 0 && col < lot.get(0).size();
    }

}
