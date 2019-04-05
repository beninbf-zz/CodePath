package test.java.com.codepath.graphs;

import main.java.com.codepath.graphs.ShortestPathWithKeysAndDoors;
import main.java.com.codepath.objects.Cell;
import main.java.com.codepath.objects.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import static org.junit.Assert.*;

public class ShortestPathWithKeysAndDoorsTest {

    public ShortestPathWithKeysAndDoors testObj = null;

    @Before
    public void setUp() {
        testObj = new ShortestPathWithKeysAndDoors();
    }

    @Test
    public void findPath1() {
        String[] grid = {"...B",
                          ".b#.",
                          "@#+ ."};

        int[][] answer = {{2, 0}, {1, 0}, {1, 1}, {0, 1}, {0, 2}, {0, 3}, {1, 3}, {2, 3}, {2, 2}};
        List<Cell> path = testObj.findPath(grid);
        assertEquals("Shortest path should be equal to the answers length", answer.length, path.size());
        testPathEquality(answer, path);
    }

    @Test
    public void findPath2() {
        String[] grid = {"+B...",
                          "####.",
                          "##b#.",
                          "a...A",
                          "##@##"};

        int[][] answer = {{4, 2}, {3, 2}, {2, 2}, {3, 2}, {3, 1}, {3, 0},
                          {3, 1}, {3, 2}, {3, 3}, {3, 4}, {2, 4}, {1, 4},
                          {0, 4}, {0, 3}, {0, 2}, {0, 1}, {0, 0}};

        List<Cell> path = testObj.findPath(grid);
        assertEquals("Shortest path should be equal to the answers length", answer.length, path.size());
        testPathEquality(answer, path);
    }

    @Test
    public void findPath3() {
        String[] grid = {"#@...",
                          "#.##.",
                          "####.",
                          "##A#.",
                          "..a..",
                          ".b##B",
                          "..##.",
                          "#.##+"};

        int[][] answer = {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {1, 4}, {2, 4}, {3, 4}, {4, 4}, {4, 3}, {4, 2},
                          {4, 1}, {5, 1}, {4, 1}, {4, 2}, {4, 3}, {4, 4}, {5, 4}, {6, 4}, {7, 4}};
        List<Cell> path = testObj.findPath(grid);
        assertEquals("Shortest path should be equal to the answers length", answer.length, path.size());
        testPathEquality(answer, path);
    }

    private void testPathEquality(int[][] result, List<Cell> path) {
        int i = 0;
        for(Cell cell: path) {
            assertEquals(String.format("row should be equal for index=%s", i), cell.row, result[i][0]);
            assertEquals(String.format("col should be equal for index=%s", i), cell.col, result[i][1]);
            i++;
        }
    }

    public void testDijkstrasAlgorithm() {
        Queue<Vertex<String>> queue = new PriorityQueue<>();
    }
}