package test.java.com.codepath.glassdoor;

import main.java.com.codepath.techscreens.glassdoor.TechScreen;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GlassdoorTechScreen {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testGlassDoor() {
        int[] queue = {5, 5, 5, 10, 10};
        assertTrue(testObj.sellIceCream(queue));
    }

    @Test
    public void testGlassDoor1() {
        int[] queue = {10, 10, 5};
        assertFalse(testObj.sellIceCream(queue));
    }

    @Test
    public void testGlassDoor2() {
        int[] queue = {5, 5, 20, 5};
        assertFalse(testObj.sellIceCream(queue));
    }

    @Test
    public void testGlassDoor3() {
        int[] queue = {5, 5, 5, 20, 5, 10};
        assertTrue(testObj.sellIceCream(queue));
    }

    @Test
    public void testGlassDoor4() {
        int[] queue = {5, 5, 10, 10, 20};
        assertFalse(testObj.sellIceCream(queue));
    }

    @Test
    public void testGlassDoor5() {
        int[] queue = {5, 10, 5, 20};
        assertTrue(testObj.sellIceCream(queue));
    }

    @Test
    public void testGlassDoor6() {
        int[] queue = {5, 5, 5, 10, 10, 20};
        assertTrue(testObj.sellIceCream(queue));
    }

    @Test
    public void testGlassDoor7() {
        int[] queue = {5, 5, 5, 5, 20};
        assertTrue(testObj.sellIceCream(queue));
    }

    @Test
    public void testCountWordSynonyms() {
        String input = "hot warm warm sizzling cold cool icy cold feather";
        Map<String, Integer> map = testObj.countWords(input);

        String[][] synonyms = {
                {"hot", "warm"},
                {"warm", "sizzling"},
                {"cold", "cool"},
                {"icy", "cold"}};

        Map<String, Integer> consolidatedMap = testObj.countWordsWithSynonyms(map, synonyms);
        System.out.println(consolidatedMap);
    }
}
