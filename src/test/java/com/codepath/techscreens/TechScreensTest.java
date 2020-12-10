package test.java.com.codepath.techscreens;

import main.java.com.codepath.techscreens.TechScreens;
import main.java.com.codepath.techscreens.objects.Cell;
import main.java.com.codepath.techscreens.objects.Range;
import main.java.com.codepath.techscreens.objects.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TechScreensTest {

    public TechScreens testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreens();
    }

    @Test
    public void testOneEditAway() {
        // same
        assertTrue("Should be true", testObj.oneEditAway("", ""));
        assertTrue("Should be true", testObj.oneEditAway("a", "a"));
        assertTrue("Should be true", testObj.oneEditAway("abc", "abc"));

        // rep, ins, del at end
        assertTrue("Should be true", testObj.oneEditAway("abc", "abd"));
        assertTrue("Should be true", testObj.oneEditAway("abc", "ab"));

        // rep, ins, del at beginning
        assertTrue("Should be true", testObj.oneEditAway("abc", "bbc"));
        assertTrue("Should be true", testObj.oneEditAway("abc", "aabc"));
        assertTrue("Should be true", testObj.oneEditAway("abc", "bc"));

        // rep, ins, del in middle
        assertTrue("Should be true", testObj.oneEditAway("abc", "adc"));
        assertTrue("Should be true", testObj.oneEditAway("abc", "abdc"));
        assertTrue("Should be true", testObj.oneEditAway("abc", "ac"));

        // boundaries
        assertTrue("Should be true", testObj.oneEditAway("", "a"));
        assertTrue("Should be true", testObj.oneEditAway("a", ""));
        assertTrue("Should be true", testObj.oneEditAway("a", "aa"));
        assertTrue("Should be true", testObj.oneEditAway("aa", "a"));

        // repeats
        assertTrue("Should be true", testObj.oneEditAway("aaa", "aaaa"));
        assertTrue("Should be true", testObj.oneEditAway("aaa", "aa"));
        assertTrue("Should be true", testObj.oneEditAway("aaabbbccc", "aaabcbccc"));
        assertTrue("Should be true", testObj.oneEditAway("aaabbbccc", "aaabbccc"));
        assertTrue("Should be true", testObj.oneEditAway("aaabbbccc", "aaabcbbccc"));

        // should be false:
        assertFalse("Should be false", testObj.oneEditAway("bbb", "aba"));
        assertFalse("Should be false", testObj.oneEditAway("abc", "abdb"));
        assertFalse("Should be false", testObj.oneEditAway("abdb", "abc"));
        assertFalse("Should be false", testObj.oneEditAway("abc", "aaabc"));
        assertFalse("Should be false", testObj.oneEditAway("aaabc", "abc"));
        assertFalse("Should be false", testObj.oneEditAway("abc", "abccc"));
        assertFalse("Should be false", testObj.oneEditAway("abccc", "abc"));
        assertFalse("Should be false", testObj.oneEditAway("abc", "acb"));
        assertFalse("Should be false", testObj.oneEditAway("bac", "abc"));
        assertFalse("Should be false", testObj.oneEditAway("aaabbbccc", "aababbccc"));
        assertFalse("Should be false", testObj.oneEditAway("aaabbbccc", "aaabcbbdcc"));
    }

    @Test
    public void testEmployeeChart() {
        testObj.printEmployeeChart("1:Max:4,2:Ann:0,3:Albert:2,4:Edmond:2");
    }

    @Test
    public void testGetPathForTiles() {
        Tile[][] floor = {
            {new Tile(1, false), new Tile(0, false), new Tile(0, false)},
            {new Tile(1, false), new Tile(0, true), new Tile(0, false)},
            {new Tile(1, false), new Tile(1, false), new Tile(0, false)}
        };

        List<Cell> path = testObj.getPath(floor, 0, 0, 2, 1);
        System.out.println(path);
    }

    @Test
    public void testRanges() {
        Range range = new Range(0, 5);
        Range range1 = new Range(2, 6);
        Range range2 = new Range(11, 17);
        Range range3 = new Range(14, 17);
        Range range4 = new Range(10, 18);
        List<Range> ranges = new ArrayList<>(Arrays.asList(range, range1, range2, range3, range4));

        assertEquals("should be 16", 16, testObj.getUniqueSeconds(ranges));
        assertEquals("should be 16", 16, testObj.getUniqueSecondsEff(ranges));
    }

    @Test
    public void testFamilyTree() {
        assertEquals("should be child", "child", testObj.describeRelationship("Daphne", "Chris"));
        assertEquals("should be child", "parent", testObj.describeRelationship("Chris", "Daphne"));
        assertEquals("should be child", "parent", testObj.describeRelationship("Bret", "Chris"));
        assertEquals("should be child", "ancestor", testObj.describeRelationship("Chris", "Henry"));
        assertEquals("should be child", "ancestor", testObj.describeRelationship("Annie", "Jack"));
        assertEquals("should be child", "unknown", testObj.describeRelationship("Foo", "Bar"));
    }

    @Test
    public void testGetClosest() {
        int[] input = {10, 50, 65, 70, 80, 120, 200};
        assertEquals("closest is 65", 65, testObj.getClosest(input, 60));

        assertEquals("closest is 200", 200, testObj.getClosest(input, 200));
        assertEquals("closest is 200", 200, testObj.getClosest(input, 210));
        assertEquals("closest is 10", 10, testObj.getClosest(input, 10));
        assertEquals("closest is 10", 10, testObj.getClosest(input, 5));
        assertEquals("closest is 80", 80, testObj.getClosest(input, 89));
        assertEquals("closest is 80", 80, testObj.getClosest(input, 79));

    }
}
