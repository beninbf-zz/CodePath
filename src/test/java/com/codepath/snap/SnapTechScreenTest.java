package test.java.com.codepath.snap;

import main.java.com.codepath.techscreens.snap.Message;
import main.java.com.codepath.techscreens.snap.StatsCounter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SnapTechScreenTest {

    @Test
    public void testSnapGetMax() {
        final long MIN = 60000;
        StatsCounter sc = new StatsCounter();

        sc.register(new Message(1 * MIN, 1));
        assertEquals("Should be 1", 1, sc.getMax());

        sc.register(new Message(2 * MIN, 10));
        assertEquals("Should be 10", 10, sc.getMax());

        sc.register(new Message(5 * MIN, 4));
        assertEquals("Should be 10", 10, sc.getMax());

        sc.register(new Message(6 * MIN, 0));
        assertEquals("Should be 10", 10, sc.getMax());

        sc.register(new Message(13 * MIN, 2));
        assertEquals("Should be 4", 4, sc.getMax());
    }
}
