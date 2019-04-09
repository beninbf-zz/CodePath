package main.java.com.codepath.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util {

    public static void print(int[] array) {
        System.out.print("[");
        for(int i = 0; i < array.length - 1; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.print(array[array.length - 1]+"]");
        System.out.println();
    }

    public static int sum(Integer[] array) {
        int total = 0;


        for(Integer number: array) {
            if (number != null) {
                total += number.intValue();
            }
        }
        return total;
    }

    public static <T> void print(T[] array) {
        System.out.print("[");
        for(int i = 0; i < array.length - 1; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.print(array[array.length - 1]+"]");
        System.out.println();
    }

    public static void print2DArray(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            System.out.print("[ ");
            for (int j= 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.print("]");
            System.out.println();
        }
    }

    public static void print(char[] array) {
        System.out.print("[");
        for(int i = 0; i < array.length - 1; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.print(array[array.length - 1]+"]");
        System.out.println();
    }

    public static void print2DArray(Object[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            System.out.print("[ ");
            for (int j= 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.print("]");
            System.out.println();
        }
    }


    public String join(List<String> list, String delimeter) {
        if (list == null || list.size() == 0) {
            return null;
        }
        String loopDelim = "";
        StringBuilder sb = new StringBuilder();
        for (String item: list) {
            sb.append(loopDelim);
            sb.append(item);

            loopDelim = delimeter;
        }

        return sb.toString();
    }

    public static void arrayDeepCopy(ArrayList<int[]> source, ArrayList<int[]> destination) {
        for(int[] array : source) {
            int[] newArray = new int[2];
            newArray[0] = array[0];
            newArray[1] = array[1];
            destination.add(newArray);
        }
    }

    public static void copyArrayListsToList(ArrayList<int[]> source, ArrayList<ArrayList<int[]>> destination) {
        ArrayList<int[]> temp = new ArrayList<>();
        for(int[] sourceArray : source) {
            int[] newArray = new int[2];
            newArray[0] = sourceArray[0];
            newArray[1] = sourceArray[1];
            temp.add(newArray);
        }
        destination.add(temp);
    }

//    public static void copy(Object[][] source, Object[][] destination) {
//        for (int i = 0; i < source.length; i++) {
//           System.arraycopy(source[i], 0, destination[i], 0, source.length);
//        }
//    }

    public static void copy(Boolean[][] source, Boolean[][] destination) {
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, source.length);
        }
    }

    public static void copy(Object[][] source, Object[][] destination) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source and destination must be non null");
        }

        if (source.length != destination.length && source[0].length != destination[0].length) {
            throw new IllegalArgumentException("Source and destination must have the same dimensions");
        }

        if (source.getClass() != destination.getClass()) {
            throw new IllegalArgumentException("Source and destination must be of same class");
        }
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, source.length);
        }
    }

    public static String[][] buildGrid(String[] grid) {
        int rows = grid.length;
        int cols = grid[0].length();
        String[][] board = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String rowStr = grid[i];
                board[i][j] = String.valueOf(rowStr.charAt(j));
            }
        }
        return board;
    }

    public static <S, T extends Iterable<S>> void print(T list) {
        System.out.print("[");
        for(Object element: list) {
            System.out.print(element + ", ");
        }
        System.out.print("]");
        System.out.println();
    }

    public static int charDifference(String one, String two) {
        int difference = 0;
        int length = one.length();
        for (int i = 0; i < length; i++) {
           if (one.charAt(i) != two.charAt(i)) {
               difference++;
           }
        }

        return difference;
    }

    /**
     * Method was written for the IK webs interface
     * @param path
     * @param result
     */
    public static void covertToArray(ArrayList<int[]> path, int[][] result) {
        for (int i = 0; i < path.size(); i++) {
            int[] coordinate = path.get(i);
            result[i] = coordinate;
        }
    }

    public static void swap(int[] array, int i, int j) {
        if (array == null || array.length == 0) {
            return;
        }

        int temp = array[i];
        array[i] = array[j];
        array[j] =  temp;
    }

    public static void swap(char[] array, int i, int j) {
        if (array == null || array.length == 0) {
            return;
        }

        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static String getString(String[] row) {
        StringBuffer sb = new StringBuffer();
        for (String s: row) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static String getString(char[] row) {
        StringBuffer sb = new StringBuffer();
        for (Character c: row) {
            sb.append(c);
        }
        return sb.toString();
    }
}
