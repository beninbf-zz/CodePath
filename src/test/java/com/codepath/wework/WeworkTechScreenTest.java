package test.java.com.codepath.wework;

import main.java.com.codepath.techscreens.wework.TechScreen;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeworkTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testFindTargetInSplitArray() {
        int[] nums = {4, 5, 6, 7, 8, 0, 1, 2};
        assertEquals("Should be 5", 5, testObj.findTargetInSplitArray(nums, 0));
        assertEquals("Should be -1", -1, testObj.findTargetInSplitArray(nums, 3));
        assertEquals("Should be 2", 2, testObj.findTargetInSplitArray(nums, 6));
        assertEquals("Should be 0", 0, testObj.findTargetInSplitArray(nums, 4));
        assertEquals("Should be 7", 7, testObj.findTargetInSplitArray(nums, 2));
    }

    @Test
    public void testFindTargetInSplitArray1() {
        int[] nums = {10, 11, 1, 2, 3, 4, 5, 6};
        assertEquals("Should be 2", 2, testObj.findTargetInSplitArray(nums, 1));
        assertEquals("Should be 0", 0, testObj.findTargetInSplitArray(nums, 10));
        assertEquals("Should be 7", 7, testObj.findTargetInSplitArray(nums, 6));
        assertEquals("Should be 1", 1, testObj.findTargetInSplitArray(nums, 11));
    }
}
