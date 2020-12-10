package test.java.com.codepath.chime;

import main.java.com.codepath.techscreens.chime.Sudoku;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChimeTechScreenTest {
    @Test
    public void testSudoku() {

        Sudoku sudoku = new Sudoku();
        int[][] board = {
                {1,2,3, 4,5,6, 7,8,9},
                {4,5,6, 7,8,9, 1,2,3},
                {7,8,9, 1,2,3, 4,5,6},
                {2,3,4, 5,6,7, 8,9,1},
                {5,6,7, 8,9,1, 2,3,4},
                {8,9,1, 2,3,4, 5,6,7},
                {3,4,5, 6,7,8, 9,1,2},
                {6,7,8, 9,1,2, 3,4,5},
                {9,1,2, 3,4,5, 6,7,8}};

        assertTrue("Rows should be valid", sudoku.checkRows(board));
        assertTrue("Cols should be valid", sudoku.checkCols(board));
        assertTrue("Subs should be valid", sudoku.checkSubMatrices(board));

        int[][] board1 = {
                {1,1,1, 1,1,1, 1,1,1},
                {2,2,2, 2,2,2, 2,2,2},
                {3,3,3, 3,3,3, 3,3,3},
                {4,4,4, 4,4,4, 4,4,4},
                {5,5,5, 5,5,5, 5,5,5},
                {6,6,6, 6,6,6, 6,6,6},
                {7,7,7, 7,7,7, 7,7,7},
                {8,8,8, 8,8,8, 8,8,8},
                {9,9,9, 9,9,9, 9,9,9}};

        assertFalse("Rows should be invalid", sudoku.checkRows(board1));
        assertTrue("Cols should be invalid", sudoku.checkCols(board1));
        assertFalse("Subs should be invalid", sudoku.checkSubMatrices(board1));

        int[][] board2 = {
                {1,2,3, 4,5,6, 7,8,9},
                {1,2,3, 4,5,6, 7,8,9},
                {1,2,3, 4,5,6, 7,8,9},
                {1,2,3, 4,5,6, 7,8,9},
                {1,2,3, 4,5,6, 7,8,9},
                {1,2,3, 4,5,6, 7,8,9},
                {1,2,3, 4,5,6, 7,8,9},
                {1,2,3, 4,5,6, 7,8,9},
                {1,2,3, 4,5,6, 7,8,9}
        };

        assertTrue("Rows should be valid", sudoku.checkRows(board2));
        assertFalse("Cols should be invalid", sudoku.checkCols(board2));
        assertFalse("Subs should be invalid", sudoku.checkSubMatrices(board2));

        int[][] board3 = {
                {1,2,3, 4,5,6, 7,8,9},
                {4,5,6, 7,8,9, 1,2,3},
                {7,8,9, 1,2,3, 4,5,6},
                {2,3,4, 5,6,7, 8,9,1},
                {5,6,7, 8,9,1, 2,3,4},
                {3,4,5, 6,7,8, 9,1,2},
                {8,9,1, 2,3,4, 5,6,7},
                {6,7,8, 9,1,2, 3,4,5},
                {9,1,2, 3,4,5, 6,7,8}
        };

        assertFalse(sudoku.evaluateBoard(board3));
    }

}
