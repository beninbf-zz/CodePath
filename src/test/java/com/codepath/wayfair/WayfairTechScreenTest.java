package test.java.com.codepath.wayfair;

import main.java.com.codepath.techscreens.wayfair.TechScreen;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WayfairTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testFindSmallestMissingPositiveInteger() {
        int[] A = new int[]{1, 3, 4, 6, 2, 1};
        int ans = testObj.findSmallestMissingPositiveInteger(A);
        assertEquals("Answer should be 5", 5, ans);


        int[] A1 = new int[]{-1, -3};
        int ans1 = testObj.findSmallestMissingPositiveInteger(A1);
        assertEquals("Answer should be 1", 1, ans1);

    }
}
