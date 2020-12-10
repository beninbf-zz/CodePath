package test.java.com.codepath.reddit;

import main.java.com.codepath.techscreens.reddit.TechScreen;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RedditTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testFlattenMap() {
        Map<String, Object> outerMap = new HashMap<>();
        outerMap.put("a", 5);
        outerMap.put("b", 6);

        Map<String, Object> mostInnerMap = new HashMap<>();
        mostInnerMap.put("m", 17);
        mostInnerMap.put("n", 9);

        Map<String, Object> firstInnerMap = new HashMap<>();
        firstInnerMap.put("f", 7);
        firstInnerMap.put("g", mostInnerMap);

        outerMap.put("c", firstInnerMap);
        Map<String, Integer> result = testObj.getFlattenedMap(outerMap);
        System.out.println(result);
    }
}
