package test.java.com.codepath.instacart;

import main.java.com.codepath.techscreens.instacart.InstaCartMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InstacartTechScreenTest {
    public InstaCartMap testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new InstaCartMap();
    }

    @Test
    public void testInstaCartMap() {
        InstaCartMap instaCartMap = new InstaCartMap();
        long timestamp = instaCartMap.set("foo", "bar");
        try {
            Thread.sleep(100);
            instaCartMap.set("foo", "bar2");
            assertEquals("Should be equal to bar", "bar", instaCartMap.get("foo", timestamp));
            assertEquals("Should be equal to last entred value 'bar2'", "bar2", instaCartMap.get("foo"));
            assertEquals("Should be equal to closest which is bar", "bar", instaCartMap.get("foo", timestamp + 49l));
            assertEquals("Should be equal to closest which is bar", "bar", instaCartMap.get("foo", timestamp + 51l));
        } catch (Exception ex) {
            // do nothing
        }
    }
}
