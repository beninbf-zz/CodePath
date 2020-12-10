package test.java.com.codepath.amazon;

import main.java.com.codepath.techscreens.amazon.TechScreen;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AmazonTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testMinTimeForAmazon() {
        List<Integer> parts = Arrays.asList(8, 4, 6, 12);
        assertEquals("Min time should be 58", 58, testObj.minimumTime(parts.size(), parts));
    }

    @Test
    public void testMinDistanceToObstacleForAmazon() {
        List<List<Integer>> lot = new ArrayList<>();
        List<Integer> firstRow = Arrays.asList(1, 0, 0);
        List<Integer> secondRow = Arrays.asList(1, 0, 0);
        List<Integer> thirdRow = Arrays.asList(1, 9, 1);

        lot.add(firstRow);
        lot.add(secondRow);
        lot.add(thirdRow);
        assertEquals("Should be 3", 3, testObj.minDistanceToRemoveObstacle(lot.size(), lot.get(0).size(), lot));
    }
}
