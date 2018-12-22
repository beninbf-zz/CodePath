package main.java.com.codepath.linkedlistproblems;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringJoiner;

public class Main {
    public static void main (String[] args) {
        char[][] board = {  {'0', '2', '1', '1', '1'},
                            {'0', '1', '0', '0', '1'},
                            {'0', '0', '0', '0', '1'},
                            {'0', '0', 'A', '0', '1'},
                            {'1', '1', 'a', '1', '1'},
                            {'1', 'b', '0', '0', 'B'},
                            {'1', '1', '0', '0', '1'},
                            {'0', '1', '0', '0', '3'}};

        char[][] board1 =  { {'1', '1', '1', 'B'},
                            {'1', 'b', '0', '1'},
                            {'2', '0', '3', '1'}};

        char[][] board2 = { {'3', 'B', '1', '1', '1'},
                            {'0', '0', '0', '0', '1'},
                            {'0', '0', 'b', '0', '1'},
                            {'a', '1', '1', '1', 'A'},
                            {'0', '0', '2', '0', '0'}};


        System.out.println(pathToString(solve(board1)));
    }
    private static final class Coord {
        final int y, x;
        Coord(int y, int x) {
            this.y = y;
            this.x = x;
        }
        @Override public String toString() {
            return String.valueOf(this.y).concat(String.valueOf(this.x));
        }
    }
    private static String pathToString(Iterable<Coord> path) {
        StringJoiner joiner = new StringJoiner("->");
        for (Coord coord : path)
            joiner.add(coord.toString());
        return joiner.toString();
    }
    private static List<Coord> solve(char[][] board) {
        List<Coord> shortestPathToGoal = new ArrayList<>();
        for (int y = 0; y < board.length; y++)
            for (int x = 0; x < board[y].length; x++)
                if (board[y][x] == '2'/*Start*/)
                    walk(board, y, x, 0, initVisited(board), new ArrayDeque<>(), shortestPathToGoal);
        return shortestPathToGoal;
    }
    private static void walk(char[][] board, int y, int x, int keyRing, boolean[][] visited,
                             Deque<Coord> path, List<Coord> shortestPathToGoal) {
        if (y < 0 || y >= board.length || x < 0 || x >= board[y].length || visited[y][x])
            return; // outside board or already visited
        char point = board[y][x];
        if (point == '0'/*Water*/)
            return; // cannot walk on water
        if (point == '3'/*Goal*/) {
            shortestPathToGoal.clear();
            shortestPathToGoal.addAll(path);
            shortestPathToGoal.add(new Coord(y, x));
            return; // return to look for alternate shorter path
        }
//        if (! shortestPathToGoal.isEmpty() && path.size() + 2 >= shortestPathToGoal.size())
//            return; // shorter (or equal) path already found
        if (point >= 'A' && point <= 'Z') { // Door
            if ((keyRing & (1 << (point - 'A'))) == 0)
                return; // missing key
        } else if (point >= 'a' && point <= 'z') { // Key
            if ((keyRing & (1 << (point - 'a'))) == 0) {
                keyRing |= (1 << (point - 'a')); // add key to keyring
                visited = initVisited(board); // may now revisit previous steps
            }
        }
        visited[y][x] = true;
        path.addLast(new Coord(y, x));
        walk(board, y    , x + 1, keyRing, visited, path, shortestPathToGoal); // right
        walk(board, y + 1, x    , keyRing, visited, path, shortestPathToGoal); // down
        walk(board, y    , x - 1, keyRing, visited, path, shortestPathToGoal); // left
        walk(board, y - 1, x    , keyRing, visited, path, shortestPathToGoal); // up
        Coord d = path.removeLast();
//        System.out.println("coord: " + d.y + ", " + d.x);
//        System.out.println("y x:   " + y + ", " +x);


        visited[y][x] = false;
    }
    private static boolean[][] initVisited(char[][] board) {
        boolean[][] visited = new boolean[board.length][];
        for (int y = 0; y < board.length; y++)
            visited[y] = new boolean[board[y].length];
        return visited;
    }
}

