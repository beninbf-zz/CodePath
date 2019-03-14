package main.java.com.codepath.recursion.nqueens;

import main.java.com.codepath.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <code>Nqueens</code> is a class containing solutions for the N queens problem.
 *
 * The problem is stated as such. For an N X N chess board, how many ways can N queens
 * be placed on the board such that the queens do not attack each other.
 *
 * This problem is meant to solve the exhaustive search problems that recursion is often
 * used for.
 *
 * @author Benin Bryant
 * @since Feb 28, 2019
 */
public class Nqueens {

    private static final String RESTRICT = "-";

    private static final String QUEEN = "q";


    public Boolean[][] solve(Boolean[][] grid) {
        if (grid.length < 4) {
            throw new IllegalArgumentException("Board has to be at least 4 x 4");
        }
        if (grid.length != grid[0].length) {
            throw new IllegalArgumentException("Board must be a square");
        }
        Boolean[][] answer = new Boolean[grid.length][grid[0].length];
        solveHelper(grid, 0, answer, grid.length);
        return answer;
    }

    /**
     * solverHelper
     * <p>
     * With this particular solution I am using a grid of Boolean objects, where the values
     * of each cell can either be NULL, TRUE, or FALSE.
     * <p>
     * Once a queen is set, the grid will be copied to a new 2-d array, and then all of the
     * requisite restrictions will be applied. We copy all of the values and restrictions to
     * a new 2-d array because, that is what we will recurse on, the newly copied 2-D array.
     * In this case that's the variable "gridTemp". We do this because, when the recursion
     * starts to back track, and calls are removed from the call stack, we want to return
     * to the "state" that was when the call was first made. To do that, we shouldn't overwrite
     * values in "grid". We should only choose the grid to set to "true", and then copy the values
     * to a new grid and apply the restrictions.
     * <p>
     * After the recursion returns we need to "unchoose" the grid[r][c] that we set to true.
     *
     * @param grid
     * @param column
     * @param answer
     * @param queens
     */
    private void solveHelper(Boolean[][] grid, int column, Boolean[][] answer, int queens) {
        if (queens == 0) {
            Util.copy(grid, answer);
            Util.print2DArray(answer);
            return;
        }

        if (column == grid[0].length) {
            return;
        }

        Boolean[][] gridTemp = new Boolean[grid.length][grid[0].length];
        // move down column
        for (int i = 0; i < grid[0].length; i++) {
            Boolean value = grid[i][column];
            if (grid[i][column] == null) {
                grid[i][column] = true;
                Util.copy(grid, gridTemp); // O(n^2)
                restrictCol(gridTemp, column); // O(n)
                restrictRow(gridTemp, i); // O(n)
                restrictUpperLeftDiagonal(gridTemp, i, column); // O(n)
                restrictUpperRightDiagonal(gridTemp, i, column); // O(n)
                restrictBottomLeftDiagonal(gridTemp, i, column); // O(n)
                restrictBottomRightDiagonal(gridTemp, i, column); // O(n)
                solveHelper(gridTemp, column + 1, answer, queens - 1);
                grid[i][column] = false;
            }
        }
    }

    private void restrictRow(Boolean[][] dimension, int row) {
        for (int i = 0; i < dimension[0].length; i++) {
            if (dimension[row][i] == null) {
                dimension[row][i] = Boolean.FALSE;
            }
        }
    }

    private void restrictRow(String[][] dimension, int row) {
        for (int i = 0; i < dimension[0].length; i++) {
            if (dimension[row][i] == null) {
                dimension[row][i] = RESTRICT;
            }
        }
    }

    private void restrictCol(Boolean[][] dimension, int column) {
        for (int i = 0; i < dimension.length; i++) {
            if (dimension[i][column] == null) {
                dimension[i][column] = Boolean.FALSE;
            }
        }
    }

    private void restrictCol(String[][] dimension, int column) {
        for (int i = 0; i < dimension.length; i++) {
            if (dimension[i][column] == null) {
                dimension[i][column] = RESTRICT;
            }
        }
    }

    private void restrictUpperLeftDiagonal(Boolean[][] grid, int row, int col) {
        row--;
        col--;
        while (row >= 0 && col >= 0) {
            if (grid[row][col] == null) {
                grid[row][col] = Boolean.FALSE;
            }
            row--;
            col--;
        }
    }

    private void restrictUpperLeftDiagonal(String[][] grid, int row, int col) {
        row--;
        col--;
        while (row >= 0 && col >= 0) {
            if (grid[row][col] == null) {
                grid[row][col] = RESTRICT;
            }
            row--;
            col--;
        }
    }

    private void restrictBottomLeftDiagonal(Boolean[][] grid, int row, int col) {
        row++;
        col--;
        while (row < grid.length && col >= 0) {
            if (grid[row][col] == null) {
                grid[row][col] = Boolean.FALSE;
            }
            row++;
            col--;
        }
    }

    private void restrictBottomLeftDiagonal(String[][] grid, int row, int col) {
        row++;
        col--;
        while (row < grid.length && col >= 0) {
            if (grid[row][col] == null) {
                grid[row][col] = RESTRICT;
            }
            row++;
            col--;
        }
    }

    private void restrictUpperRightDiagonal(Boolean[][] grid, int row, int col) {
        row--;
        col++;
        while (row >= 0 && col < grid[0].length) {
            if (grid[row][col] == null) {
                grid[row][col] = Boolean.FALSE;
            }
            row--;
            col++;
        }
    }

    private void restrictUpperRightDiagonal(String[][] grid, int row, int col) {
        row--;
        col++;
        while (row >= 0 && col < grid[0].length) {
            if (grid[row][col] == null) {
                grid[row][col] = RESTRICT;
            }
            row--;
            col++;
        }
    }

    private void restrictBottomRightDiagonal(Boolean[][] grid, int row, int col) {
        row++;
        col++;
        while (row < grid.length && col < grid[0].length) {
            if (grid[row][col] == null) {
                grid[row][col] = Boolean.FALSE;
            }
            row++;
            col++;
        }
    }

    private void restrictBottomRightDiagonal(String[][] grid, int row, int col) {
        row++;
        col++;
        while (row < grid.length && col < grid[0].length) {
            if (grid[row][col] == null) {
                grid[row][col] = RESTRICT;
            }
            row++;
            col++;
        }
    }

    /**
     * Interview kick start solution. Returning a 2D array of strings representing the solution.
     * <p>
     * For the problem of 4 queens, the solution should be represented as
     * <p>
     * ret [0] =
     * <p>
     * [
     * <p>
     * "-q--",
     * <p>
     * "---q",
     * <p>
     * "q---",
     * <p>
     * "--q-"
     * <p>
     * ]
     * <p>
     * <p>
     * <p>
     * ret [1] =
     * <p>
     * <p>
     * <p>
     * [
     * <p>
     * "--q-",
     * <p>
     * "q---",
     * <p>
     * "---q",
     * <p>
     * "-q--"
     * <p>
     * ]
     *
     * @return
     */
    public String[][] find_all_arrangements(int n) {
        String[][] chessBoard = new String[n][n];

        if (n < 4) {
            switch(n) {
                case 1:
                    String[][] answer = new String[1][1];
                    answer[0][0] = QUEEN;
                    return answer;
                case 2:
                    return new String[][]{};
                case 3:
                    return new String[][]{};
                default:
                    break;
            }
        }

        List<String[]> results = new ArrayList<>();
        find_all_arrangementsHelper(chessBoard, 0, n, results);
        String[][] answer = new String[results.size()][n];

        for (int i = 0; i < results.size(); i++) {
            answer[i] = results.get(i);
        }
        return answer;
    }

    private void find_all_arrangementsHelper(String[][] grid, int column, int queens, List<String[]> results) {
        if (queens == 0) {
            String[] solution = new String[grid.length];
            for (int i = 0; i < grid.length; i++) {
                solution[i] = Util.getString(grid[i]);
            }
            results.add(solution);
            return;
        }

        if (column == grid[0].length) {
            return;
        }

        String[][] gridTemp = new String[grid.length][grid[0].length];
        // move down column
        for (int i = 0; i < grid[0].length; i++) {
            String value = grid[i][column];
            if (grid[i][column] == null) {
                grid[i][column] = QUEEN;
                Util.copy(grid, gridTemp); // O(n^2)
                restrictCol(gridTemp, column); // O(n)
                restrictRow(gridTemp, i); // O(n)
                restrictUpperLeftDiagonal(gridTemp, i, column); // O(n)
                restrictUpperRightDiagonal(gridTemp, i, column); // O(n)
                restrictBottomLeftDiagonal(gridTemp, i, column); // O(n)
                restrictBottomRightDiagonal(gridTemp, i, column); // O(n)
                find_all_arrangementsHelper(gridTemp, column + 1, queens - 1, results);
                grid[i][column] = RESTRICT;
            }
        }
    }
}
