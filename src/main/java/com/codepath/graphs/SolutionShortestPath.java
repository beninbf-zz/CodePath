package main.java.com.codepath.graphs;

import main.java.com.codepath.util.Util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class SolutionShortestPath {

    static int[][] find_shortest_path(String[] grid) {
        char [][] board = new char[grid.length][grid[0].length()];
        for(int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length(); j++) {
                board[i][j] = grid[i].charAt(j);
            }
        }
        return (pathToString(solve(board)));
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
    private static int[][] pathToString(Iterable<Coord> path) {
        int sz = 0;
        for (Coord coord : path)
            sz++;
        int[][] ans = new int[sz][2];
        int i = 0;
        for (Coord coord : path)
        {
            ans[i][0] = coord.y;
            ans[i][1] = coord.x;
            i++;
        }
        return ans;
    }
    private static List<Coord> solve(char[][] board) {
        List<Coord> shortestPathToGoal = new ArrayList<>();
        for (int y = 0; y < board.length; y++)
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] == '@'/*Start*/) {
                    walk(board, y, x, 0, initVisited(board), new ArrayDeque<>(), shortestPathToGoal);
                }
            }
        return shortestPathToGoal;
    }
    private static void walk(char[][] board, int y, int x, int keyRing, boolean[][] visited,
                             Deque<Coord> path, List<Coord> shortestPathToGoal) {
        if (y < 0 || y >= board.length || x < 0 || x >= board[y].length || visited[y][x])
            return; // outside board or already visited
        char point = board[y][x];
        if (point == '#'/*Water*/)
            return; // cannot walk on water
        if (point == '+'/*Goal*/) {
            shortestPathToGoal.clear();
            shortestPathToGoal.addAll(path);
            shortestPathToGoal.add(new Coord(y, x));
            //System.out.println("---- ----");
            return; // return to look for alternate shorter path
        }
        if (! shortestPathToGoal.isEmpty() && path.size() + 2 >= shortestPathToGoal.size())
            return; // shorter (or equal) path already found
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
        path.removeLast();
        visited[y][x] = false;
    }
    private static boolean[][] initVisited(char[][] board) {
        boolean[][] visited = new boolean[board.length][];
        for (int y = 0; y < board.length; y++)
            visited[y] = new boolean[board[y].length];
        return visited;
    }

    public static void main(String[] args) {

        String[] array = {"...B", ".b#.", "@#+."};
        String[] array2 = {"+B...", "####.", "##b#.", "a...A", "##@##"};

        int[][] path = find_shortest_path(array2);
        System.out.println("Length of path: " + path.length);

        for (int i = 0; i < path.length; i++ ) {
            for (int j = 0; j < path[i].length; j++) {
                System.out.print(path[i][j] + "");
            }
            System.out.println();
        }
    }
}
