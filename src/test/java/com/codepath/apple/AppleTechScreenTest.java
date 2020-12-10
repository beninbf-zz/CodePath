package test.java.com.codepath.apple;

import main.java.com.codepath.techscreens.apple.TechScreen;
import main.java.com.codepath.util.Util;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AppleTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testSecretSanta() {
        List<String> names = Arrays.asList("Bill", "Bob", "Tom");
        Map<String, String> map = testObj.getRandomMap(names);
        System.out.println(map);
    }

    @Test
    public void testSortedSquares() {
        int[] a = {-4, -2, 1, 3, 5};
        int[] results = testObj.sortedSquares(a);
        Util.print(results);

        int[] b = {-6, -4, -3, -2, -1};
        Util.print(testObj.sortedSquares(b));

        int[] c = {3, 4, 5, 7, 8};
        Util.print(testObj.sortedSquares(c));

        int[] d = {};
        Util.print(testObj.sortedSquares(d));
    }
}
