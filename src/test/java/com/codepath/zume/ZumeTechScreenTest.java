package test.java.com.codepath.zume;

import main.java.com.codepath.techscreens.zume.TechScreen;
import main.java.com.codepath.techscreens.zume.ZerosRectangle;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ZumeTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testZumeViaKarat_DomainsClicks() {
        int[][] image1 = {
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 0, 0, 0, 1},
                {1, 1, 1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1}
        };

        ZerosRectangle image1Result = testObj.findRectangleOfZeros(image1);
        assertEquals("Start row should be 2", image1Result.beginning.row, 2);
        assertEquals("Start col should be 3", image1Result.beginning.col, 3);
        assertEquals("Width should be 3", image1Result.width, 3);
        assertEquals("Height should be 2", image1Result.height, 2);

        int[][] image2 = {
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 0}
        };

        ZerosRectangle image2Result = testObj.findRectangleOfZeros(image2);
        assertEquals("Start row should be 4", image2Result.beginning.row, 4);
        assertEquals("Start col should be 6", image2Result.beginning.col, 6);
        assertEquals("Width should be 1", image2Result.width, 1);
        assertEquals("Height should be 1", image2Result.height, 1);

        int[][] image3 = {
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 0, 0},
                {1, 1, 1, 1, 1, 0, 0}
        };

        ZerosRectangle image3Result = testObj.findRectangleOfZeros(image3);
        assertEquals("Start row should be 3", image3Result.beginning.row, 3);
        assertEquals("Start col should be 5", image3Result.beginning.col, 5);
        assertEquals("Width should be 2", image3Result.width, 2);
        assertEquals("Height should be 2", image3Result.height, 2);

        int[][] image4 = {
                {0, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1}
        };

        ZerosRectangle image4Result = testObj.findRectangleOfZeros(image4);
        System.out.println(image4Result);
        assertEquals("Start row should be 1", image4Result.beginning.row, 0);
        assertEquals("Start col should be 1", image4Result.beginning.col, 0);
        assertEquals("Width should be 1", image4Result.width, 1);
        assertEquals("Height should be 1", image4Result.height, 1);

        int[][] image5 = {
                {0}
        };

        ZerosRectangle image5Result = testObj.findRectangleOfZeros(image5);
        assertEquals("Start row should be 1", image5Result.beginning.row, 0);
        assertEquals("Start col should be 1", image5Result.beginning.col, 0);
        assertEquals("Width should be 1", image5Result.width, 1);
        assertEquals("Height should be 1", image5Result.height, 1);
    }

    @Test
    public void testZume_findNodesWithOneParents() {
        int[][] parentChildPairs = new int[][]{
                {1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7},
                {4, 5}, {4, 8}, {8, 10}
        };

        List<Integer> answer = new ArrayList<>(Arrays.asList(5, 7, 8, 10));
        Set<Integer> result = testObj.findNodesWithOneParent(parentChildPairs);
        Integer[] ans = result.toArray(new Integer[0]);
        for (int i = 0; i < ans.length; i++) {
            assertEquals("items should be equal", answer.get(i).intValue(), ans[i].intValue());
        }
    }
}
