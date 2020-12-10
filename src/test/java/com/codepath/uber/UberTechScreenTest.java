package test.java.com.codepath.uber;

import main.java.com.codepath.techscreens.uber.TechScreen;
import main.java.com.codepath.techscreens.uber.FareCalculator;
import main.java.com.codepath.util.Util;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UberTechScreenTest {

    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testFareCalculator() {
        FareCalculator fareCalculator = new FareCalculator(10.00d);
        fareCalculator.addRider(1, 1);
        fareCalculator.addRider(2, 3);
        assertEquals("should be 13.33", 13.33333d, fareCalculator.dropFare(1,4).doubleValue(), .0002);
    }

    @Test
    public void findNextLargest() {
        int[] input = {1, 2, 7, 6, 5, 4, 3, 1};
        testObj.findNextLargest(input);
        Util.print(input);
        int[] inputAnswer = {1, 3, 1, 2, 4, 5, 6, 7};
        testEquality(input, inputAnswer);

        int[] input1 = {1, 1, 5};
        testObj.findNextLargest(input1);
        Util.print(input1);
        int[] inputAnswer1 = {1, 5, 1};
        testEquality(input1, inputAnswer1);

        int[] input2 = {1, 2, 3};
        testObj.findNextLargest(input2);
        Util.print(input2);
        int[] inputAnswer2 = {1, 3, 2};
        testEquality(input2, inputAnswer2);

        int[] input3 = {5, 1, 1};
        testObj.findNextLargest(input3);
        Util.print(input3);
        int[] inputAnswer3 = {1, 1, 5};
        testEquality(input3, inputAnswer3);
    }

    private void testEquality(int[] input, int[] answer) {

        if (input == null || answer == null) {
            assertTrue("Invalid input", false);
        }

        if (input.length != answer.length) {
            assertTrue("Arrays are of different lengths", false);
        }

        for (int i = 0; i < answer.length; i++) {
            assertEquals("values should be equal", answer[i], input[i]);
        }
    }
}
