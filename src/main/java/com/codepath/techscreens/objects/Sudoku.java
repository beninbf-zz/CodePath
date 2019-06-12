package main.java.com.codepath.techscreens.objects;

import java.util.HashSet;
import java.util.Set;

public class Sudoku {

    private static final int STEP = 3;

    public boolean checkRows(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            if(!valid(board[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCols(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            int[] col = new int[9];
            for (int j = 0; j < board[0].length; j++) {
                col[j] = board[j][i];
            }
            if(!valid(col)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSubMatrices(int[][] board) {
        for (int i = 0; i < board.length; i = i + STEP) {
            for (int j = 0; j < board[0].length; j = j + STEP) {
                if (!checkSubMatrix(board, i, j)) {
                    return false;
                }
            }
            System.out.println();
        }
        return true;
    }

    public boolean checkSubMatrix(int[][] subMatrix, int startRow, int startCol) {
        int[] array = new int[9];
        int k = 0;
        for (int i = startRow; i < startRow + STEP; i++) {
            for (int j = startCol; j < startCol + STEP; j++) {
                array[k] = subMatrix[i][j];
                k++;
            }
        }
        return valid(array);
    }

    public boolean valid(int[] array) {
        Set<Integer> set = new HashSet<>();
        for (Integer n: array) {
            set.add(n);
        }

        for (int i = 1; i <= 9; i++) {
            if (!set.contains(i)) {
                return false;
            }
        }
        return true;
     }

     public boolean evaluateBoard(int[][] board) {
        return checkRows(board) && checkCols(board) && checkSubMatrices(board);
     }
}
