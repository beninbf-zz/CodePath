package main.java.com.codepath.graphs;

import com.sun.org.apache.bcel.internal.generic.LADD;
import com.sun.org.apache.bcel.internal.generic.LAND;
import main.java.com.codepath.objects.Cell;
import main.java.com.codepath.objects.ShortestPathCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/*
 * ShortestPathWithKeysAndDoors - The following represents my findings from the struggle of
 * doing this problem using what amounts to a DFS representation. Its really just a complex
 * recursion problem, which is what DFS amounts to anyway. This constitutes a brute force
 * approach where we are generating every possible path, and returning when we have found
 * the shortest.
 *
 * Important take aways
 *
 *
 * BELOW WAS THE PART I WAS FUCKING UP ON!!!!!!
 * What I was originally doing, was simply checking to see if the point was a key.
 * IF it was a key, then I was simply adding it to the key ring. When something is
 * added to the key ring, then the possibility exists that there are other path ways
 * that are possible, because we might have ran into doors for which we did not have a key.
 *
 * Now that we have added a key, we need to mark all of the previously visited cells, as
 * unvisited, so that when I recursion starts over we can use the key for those doors.
 *
 * My original code as
 *
 * if (point is a key) {
 *     keyRing.add(key)
 *     clear visited array
 * }
 *
 * This was a huge mistake, because, if I "already" have that key, then I don't need to clear
 * out the visited array, I can just simply use the key.
 *
 * I should only clear out the visited array (there by re-visiting the previously visited cells)
 * if I DONT have the key. If I do have key in my possession, then it means, I have either already
 * visited cells that can be opened with that key, or I simply will in a future, and so
 * there is nothing to revisit.
 *
 * We should ONLY reset the visited array, if we run into a key that we don't have!!!!
 *
 *
 * private boolean isKey(String str, boolean[][] visited) {
 *  if (keys.contains(str)) {
 *       if (!keyRing.contains(str)) {
 *          keyRing.add(str);
 *           clearVisited(visited);
 *          return true;
 *       }
 *   }
 *   return false;
 * }
 *
 *  I was previously failing this use case. The path went from [0, 17] to [1, 17].
 *  The final path calculated was 6, when it should have been 24.
 *
 *  This occurred because I was passing the KeyRing as an object that would be shared
 *  BY ALL STACK FRAMES throughout the recursion.
 *
 *  This is a terrible mistake, because of what it means for walking the array. It means
 *  that, irregardless of the branch of recursion that we might be in, any time we may have
 *  added a key to the key ring in a previous call, it would be available for ALL subsequent
 *  EVEN in the cases of back tracking to stack frames, where the path DIDN'T include
 *  picking up a key.
 *
 *  This flaw was discovered when switching to using a bit mask. The bit mask, was nothing
 *  more than a integer. This integer is pass by value, such that, when any call returned from its
 *  recursion, its key ring at the time of the call would remain, when the given recursive call
 *  returned, and that is exactly what we wanted. We wanted to "remember" the "state" of the key
 *  ring at every stack frame, before transitioning to subsequent recursive calls.
 *
 *      String[] input = new String[]{".dj##.f.j#efejj..@e#+G.c.",
 *          ".hdI#.#aAghficDe#J.CGa.ba"};
 */

/**
 * An important aspect of recursion must be highlighted here. What needs to be highlighted
 * is "state" and "transition". If you look at the inputs of this method, specifically
 * the key ring and the visited array, those variables dictate the choices of where the
 * current recursive call can explore.
 *
 * Previously, I was passing the reference to the key ring, to ALL of the recursive calls.
 * So all nodes in the recursion were sharing the same key ring. This is a mistake, because
 * when the recursion returns to a particular root node, we need to "return" to the original
 * state of when that parent node was called. In order to do that, we need that root node to
 * maintain its own "state" for its key ring, in other words, the key ring, for any particular
 * root node, has to be the same as what it was, when the root node was first executed.
 * It can't be the same key ring for all nodes in the recursion tree.
 *
 * This same principle of maintaining "state" in order to make the proper transition to other
 * choices, governs why the visited array, must also maintain the same state in each node
 * in the recursion tree.
 *
 * That's why, we must call "initVisited" on the visited array. So that we can declare a brand
 * new visited array, for that particular node. In this way, when the recursion goes back up the
 * call stack, and returns to a given node, that visited array, was the same as it was when it
 * was originally called in "most" cases. I say most cases, because in some of the nodes in
 * the recursion tree, the visited array will be re-instantiated, to be contain false values.
 *
 * This is done, because only that particular root node, that has found a "key", should be able
 * to revisit old cells. Other nodes in the recursion tree, that have come before the node where
 * the visited array is cleared out...when the recursion returns to those parent nodes, their
 * visited array should be the same as it was when it was first called. The state of the visited
 * array must also be maintained.
 *
 * If we passed the same visited array to all subsequent recursive calls, the code would never end,
 * because everytime we found a key, the visited array would be reset to contain all false's. That's
 * not what we want, we only want certain nodes in the recursive tree, to be able to revisit cells,
 * we don't necessarily want ALL nodes in the recursion tree to be able to revisit all cells.
 */

/**
 * Other important take aways regarding coding fluency. When doing these problems where we must
 * move around a grid, or calculate a path or even all paths within a grid of characters, we
 * should transform the grid into a 2-d array of characters, we should not leave it as a 2-d
 * array of strings. It will be faster with characters.
 *
 * That means if we want to maintain any kind of "set" type object, we don't have to use the
 * Set object, we can use a bit mask.
 */

public class ShortestPathWithKeysAndDoors {
    public final char START = '@';
    public final char LAND = '.';
    public final char END = '+';
    public final char WATER = '#';

    public List<Cell> findPath(String[] board) {
        if (board == null) {
            return null;
        }

        char [][] grid = new char[board.length][board[0].length()];
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length(); j++) {
                grid[i][j] = board[i].charAt(j);
            }
        }

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int[] startCoordinates = findStartOrEnd(grid, START);
        List<Cell> candidatePath = new ArrayList<>();
        List<Cell> shortestPath = new ArrayList<>();
        int keyRing = 0;
        exploreDfs(grid, startCoordinates[0], startCoordinates[1], keyRing, candidatePath, shortestPath, visited);
        return shortestPath;
    }

    public int[][] find_shortest_path(String[] board) {
        if (board == null) {
            return null;
        }

        char [][] grid = new char[board.length][board[0].length()];
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length(); j++) {
                grid[i][j] = board[i].charAt(j);
            }
        }

        boolean[][][] visited = new boolean[grid.length][grid[0].length][1024];
        Map<ShortestPathCell, ShortestPathCell> pathMap = new HashMap<>();

        int[] end = findStartOrEnd(grid, END);
        int[] start = findStartOrEnd(grid, START);
        ShortestPathCell startCell = new ShortestPathCell(start[0], start[1], 0);
        ShortestPathCell endCell = new ShortestPathCell(end[0], end[1], 0);
        ShortestPathCell destinationCell = exploreBfs(grid, visited, startCell, endCell, pathMap);

        List<int[]> path = new ArrayList();
        path.add(0, new int[]{destinationCell.row, destinationCell.col});

        ShortestPathCell current = pathMap.get(destinationCell);
        while (!(current.row == startCell.row && current.col == startCell.col) || current.keyRing != 0) {
            path.add(0, new int[]{current.row, current.col});
            current = pathMap.get(current);
        }
        path.add(0, new int[]{startCell.row, startCell.col});
        int[][] results = new int[path.size()][2];
        int i = 0;
        for (int[] array: path) {
            results[i] = array;
            i++;
        }
        return results;
    }
    private ShortestPathCell exploreBfs(char[][] grid, boolean[][][] visited, ShortestPathCell startCell, ShortestPathCell endCell, Map<ShortestPathCell, ShortestPathCell> pathMap) {
        Queue<ShortestPathCell> queue = new LinkedList<>();
        queue.add(startCell);
        while (!queue.isEmpty()) {
            ShortestPathCell current = queue.poll();
            if (current.row == 4 && current.col ==  2 && current.keyRing == 1) {
                //System.out.println("ok");
            }
            if (current.row == endCell.row && current.col == endCell.col) {
                return current;
            }
            List<ShortestPathCell> neighbors = getNeighbors(grid, current, visited);
            visited[current.row][current.col][current.keyRing] = true;
            for (ShortestPathCell neighbor : neighbors) {
                if (visited[neighbor.row][neighbor.col][neighbor.keyRing] == false) {
                    pathMap.put(new ShortestPathCell(neighbor.row, neighbor.col, neighbor.keyRing), current);
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }

    private List<ShortestPathCell> getNeighbors(char[][] grid, ShortestPathCell current, boolean[][][] visited) {
        List<ShortestPathCell> neighbors = new ArrayList<>();
        int[] neighborRowCoordinates = {0,  0, -1, 1};
        int[] neighborColCoordinates = {-1, 1, 0, 0};

        for (int i = 0; i < 4; i++) {
            addNeighbor(current.row + neighborRowCoordinates[i], current.col + neighborColCoordinates[i], current.keyRing, grid, neighbors, visited);
        }

        return neighbors;
    }

    private boolean containsKey(int keyRing, int key) {
        return (keyRing & key) == key;
    }

    private boolean containsKeyOther(int keyRing, int key) {
        return ((keyRing >> key) & 1) == 1;
    }

    private int addKey(int keyRing, int key) {
        keyRing = keyRing | key;
        return keyRing;
    }

    private void addNeighbor(int row, int col, int keyRing, char[][] grid, List<ShortestPathCell> neighbors, boolean[][][] visited) {
        if (isValid(row, col, grid)) {
            if (row == 4 && col == 2 && keyRing == 3) {
                System.out.println("l");
            }
            if (visited[row][col][keyRing]) {
                return;
            }
            char ch = grid[row][col];
            if (isDoor(ch)) {
                int keyBit = ch - 'A';
                int keyBitMask = 1 << keyBit;
                if (containsKeyOther(keyRing, keyBit)) {
                    neighbors.add(new ShortestPathCell(row, col, keyRing));
                }
            } else if (isKey(ch)) {
                int keyBit = ch - 'a';
                int keyBitMask = 1 << keyBit;
                int newKeyRing = addKey(keyRing, keyBitMask);
                if (visited[row][col][newKeyRing] == false) {
                    neighbors.add(new ShortestPathCell(row, col, newKeyRing));
                }
            } else if (ch == LAND || ch == END) {
                neighbors.add(new ShortestPathCell(row, col, keyRing));
            } else {
                return;
            }
        }
    }

    private boolean isDoor(char ch) {
        return ch >= 'A' && ch <= 'J';
    }

    private boolean isKey(char ch) {
        return ch >= 'a' && ch <= 'j';
    }

    private boolean isValid(int row, int col, char[][] grid) {
        return (row >= 0 && row < grid.length) && (col >= 0 && col < grid[0].length);
    }

    private void exploreDfs(char[][] grid, int row, int col, int keyRing, List<Cell> candidatePath, List<Cell> shortestPath,
                            boolean[][] visited) {

        if (row < 0 || row >= grid.length || col < 0 || col >= grid[row].length) {
            return;
        }

        if (visited[row][col]) {
            return;
        }

        char location = grid[row][col];
        if (location == WATER) {
            return;
        }

        if (location == END) {
            shortestPath.clear();
            shortestPath.addAll(candidatePath);
            shortestPath.add(new Cell(row, col));
            return;
        }

        /* This return statement is very important. Without this code just goes on forever
        *  What this return statement is saying is that, if we can't find another path
        *  this is shorter or equal to the current one, then just return.
        *
        *  If you have already found a shortest path, then we are essentially building up
        *  another possible shortest path, and waiting for its size to be equal to
        *  or greater than the current shortest path.
        *
        *  If we have reached this return statement, and the difference between the candidatePath
        *  and the shortest path is only 1, then that means we haven't reach the end
        *  yet. So if the difference is only one, we might as well stop.
        */
        if (!shortestPath.isEmpty() && candidatePath.size() + 1 >= shortestPath.size()) {
            return;
        }

        // Is the location a key?
        if (location >= 'a' && location <= 'j') {
            // get the actual key
            int possibleKeyBit = location - 'a';
            // We create a bit mask, by starting with the int 1, and then shifting that one bit over.
            // 0 0 0 0 0 0 0 1 << possibleKeyBit.
            // so whatever possible keybit is, move 0 0 0 0 0 0 0 1 over to the left possibleKeyBit spaces
            int possibleKeyBitMask = 1 << possibleKeyBit;

            // if the below is true, (keyRing & possibleKeyBitMask) == 1, then we have the bit
            // essentially this is a contains check. If its 0, then we dont have the key, in our key
            // ring so we need to add it.
            if ((keyRing & possibleKeyBitMask) == 0) {
                // if we don't have the key, then add it to our key ring
                // doing the OR effectively adds the shifted bit to the keyRing variable
                keyRing = keyRing | possibleKeyBitMask;
                // then make all cells visitable again by this node
                visited = clearVisited(visited);
            } // if the we already have the key in the key ring, then do nothing
        } else if (location >= 'A' && location <= 'J') { // is it a door
            // check if we have the key. Even though these characters are capitals
            // considering they are alphabetic, we just need the magnitude difference
            // from A or the start of the alphabet. location - 'a', will be the same
            // location - 'A', as long as location matches the correct case (upper or lower).
            int doorBit = location - 'A';
            int doorBitKeyMask = 1 << doorBit;
            if ((keyRing & doorBitKeyMask) == 0) { // we don't have the key
                return;
            }
        }

        /*
        if (location >= 'a' && location <= 'j') { // Key
            if ((keyRing & (1 << (location - 'a'))) == 0) {
                keyRing |= (1 << (location - 'a')); // add key to keyring
                visited = clearVisited(visited); // may now revisit previous steps
            }
        } else if (location >= 'A' && location <= 'J') { // Door
            if ((keyRing & (1 << (location - 'A'))) == 0)
                return; // missing key
        }
        */

        visited[row][col] = true;
        candidatePath.add(new Cell(row, col));
        exploreDfs(grid, row , col + 1, keyRing, candidatePath, shortestPath, visited); //right
        exploreDfs(grid, row + 1, col, keyRing, candidatePath, shortestPath, visited); // down
        exploreDfs(grid, row , col - 1, keyRing, candidatePath, shortestPath, visited); // left
        exploreDfs(grid, row - 1, col, keyRing, candidatePath, shortestPath, visited); // up
        candidatePath.remove(candidatePath.size() - 1);
        visited[row][col] = false;
    }

    private static boolean[][] clearVisited(boolean[][] visited) {
        boolean[][] clearedVisited = new boolean[visited.length][];
        for (int y = 0; y < clearedVisited.length; y++) {
            clearedVisited[y] = new boolean[visited[y].length];
        }
        return clearedVisited;
    }

    private int[] findStartOrEnd(char[][] grid, char landMark) {
        int rows = grid.length;
        int cols = grid[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == landMark) {
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

