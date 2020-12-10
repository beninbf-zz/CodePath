package test.java.com.codepath.facebook;

import main.java.com.codepath.techscreens.facebook.TechScreen;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FacebookTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testGetTimeToCompleteTasks() {
        int[] array = {1, 1, 2, 1};
        assertEquals("Count should be 7", 7, testObj.getTimeToCompleteTasks(array, 2));

        int[] array1 = {1, 2, 3, 4};
        assertEquals("Count should be 4", 4, testObj.getTimeToCompleteTasks(array1, 2));
    }
}
