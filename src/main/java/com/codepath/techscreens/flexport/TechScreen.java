package main.java.com.codepath.techscreens.flexport;

import main.java.com.codepath.techscreens.objects.Cell;
import main.java.com.codepath.util.Util;

public class TechScreen {
    /**
     * FlexPort Tech Screen - Print zig zag
     *
     * Given a sequence of letters and number of levels. Print it in a zig zag fashion as shown below.
     *
     * ABCDEFGHIJ, 3
     *
     * A E I
     * BDFHJ
     * C G
     *
     * ABCDEFGHIJKLMNOPQ, 4
     *
     * A  G  M
     * B FH LN
     * CE IK OQ
     * D  J  P
     *
     *
     * print_zigzag(sequence, num_levels)
     */
    public void printZigZag(String input, int numLevels) {
        char[][] grid = new char[numLevels][input.length()];
        int length = input.length();
        Cell cell = new Cell(0, 0, 0);
        while(cell != null && cell.row < grid.length && cell.col < grid[0].length && cell.index < length) {
            cell = fillColumn(grid, input, length, cell);
            cell = fillDiagonal(grid, input, length, cell);
        }
        Util.print(grid);
    }

    public Cell fillColumn(char[][] grid, String input, int length, Cell cell) {
        for (int i = cell.row; i < grid.length; i++) {
            if (cell.index < length) {
                grid[i][cell.col] = input.charAt(cell.index);
                cell.index++;
            } else {
                break;
            }
        }
        return new Cell(grid.length - 1, cell.col, cell.index);
    }

    public Cell fillDiagonal(char[][] grid, String input, int length, Cell cell) {
        int rowSub = 1;
        for (int j = cell.col + 1; j < grid[0].length; j++) {
            if (cell.index < length) {
                grid[cell.row - rowSub][j] = input.charAt(cell.index);
                cell.index++;
                rowSub++;
            } else {
                break;
            }

            if ((cell.row - rowSub) == 0) {
                break;
            }
        }
        return new Cell(0, cell.col + 2, cell.index);
    }
}
