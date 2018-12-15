package main.java.com.codepath.graphs;

import main.java.com.codepath.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ShortestPathWithKeysAndDoors {

    public static final Set<String> keys = new HashSet<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));
    public static final Set<String> doors = new HashSet<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));

    public static final String START = "@";
    public static final String END = "+";
    public static final String WATER = "#";
    public static final String LAND = ".";

    public ArrayList<int[]> findPath(String[][] grid) {

        if (grid == null) {
            return null;
        }

        int[] startCoordinates = findStartOrEnd(grid, START);
        LinkedHashSet<String> seen = new LinkedHashSet<>();
        ArrayList<int[]> candidatePath = new ArrayList<>();
        ArrayList<ArrayList<int[]>> paths = new ArrayList<>();
        exploreGrid1(grid, startCoordinates[0], startCoordinates[1], candidatePath, paths, seen);


//        for (int[] coordinates : paths.get(0)) {
//            System.out.println("[" + coordinates[0] + ", " + coordinates[1] + "]");
//        }
        System.out.println("-----------------");
//        for (int[] coordinates : paths.get(1)) {
//            System.out.println("[" + coordinates[0] + ", " + coordinates[1] + "]");
//        }
//        System.out.println("-----------------");

        ArrayList<int[]> shortestPath = null;
        if (paths.size() > 0) {
            shortestPath = paths.get(0);
            for (ArrayList<int[]> list : paths) {
                if (list.size() < shortestPath.size()) {
                    shortestPath = list;
                }
            }
        }
        return shortestPath;
    }

    private void exploreGrid1(String[][] grid, int startRow, int startCol, ArrayList<int[]> candidatePath, ArrayList<ArrayList<int[]>> paths,
                              Set<String> seen) {

        if (inBounds(grid, startRow, startCol) && grid[startRow][startCol].equals(END)) {
            int[] path = new int[2];
            path[0] = startRow;
            path[1] = startCol;
            candidatePath.add(path);
            Util.copyArrayListsToList(candidatePath, paths);
            return;
        }

        if (!canMove(grid, startRow, startCol, candidatePath, seen)) {
            return;
        }

        int[] path = new int[2];
        path[0] = startRow;
        path[1] = startCol;
        candidatePath.add(path);

        seen.add(startRow + "" + startCol);



        exploreGrid1(grid, startRow - 1, startCol, candidatePath, paths, seen);
        exploreGrid1(grid, startRow , startCol + 1, candidatePath, paths, seen);
        exploreGrid1(grid, startRow + 1, startCol, candidatePath, paths, seen);
        exploreGrid1(grid, startRow , startCol - 1, candidatePath, paths, seen);
        removeLast(candidatePath, seen);
    }

    private void removeLast(ArrayList<int[]> list, Set<String> seen) {
        if (list.size() > 0) {
            int[] array = list.get(list.size() - 1);
            list.remove(list.size() - 1);
            seen.remove(array[0] + "" + array[1]);
        }
    }

    private boolean canMove(String[][] grid, int endRow, int endCol, ArrayList<int[]> candidatePath, Set<String> seen) {
        if (!inBounds(grid, endRow, endCol)) {
            return false;
        }

        if (grid[endRow][endCol].equals(WATER)) {
            return false;
        }

        String str = endRow + "" + endCol;
        if (seen.contains(endRow + "" + endCol)) {
            return false;
        }

        if (grid[endRow][endCol].equals(LAND) ||
            grid[endRow][endCol].equals(START) ||
            keys.contains(grid[endRow][endCol]) ||
            pathHasKey(grid, endRow, endCol, candidatePath)) {
            return true;
        }

         return false;
    }

    private boolean inBounds(String[][] grid, int row, int col) {
        return  (row >= 0 && row < grid.length) && (col >= 0 && col < grid[0].length);
    }

    private boolean pathHasKey(String[][] grid, int endRow, int endCol, ArrayList<int[]> candidatePath) {
        for (int[] array : candidatePath) {
            String s = grid[array[0]][array[1]];
            if (s.toUpperCase().equals(grid[endRow][endCol])) {
                return true;
            }
        }
        return false;
    }

    private int[] findStartOrEnd(String[][] grid, String landMark) {
        int rows = grid.length;
        int cols = grid[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j].equals(landMark)) {
                    int[] end = new int[2];
                    end[0] = i;
                    end[1] = j;
                    return end;
                }
            }
        }
        return null;
    }
}
