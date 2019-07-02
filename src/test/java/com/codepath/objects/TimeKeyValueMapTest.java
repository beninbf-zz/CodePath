package test.java.com.codepath.objects;

import main.java.com.codepath.objects.TimeKeyValueMap;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TimeKeyValueMapTest {

    private TimeKeyValueMap testObj;

    @Before
    public void setUp() {
        this.testObj = new TimeKeyValueMap();
    }

    @Test
    public void testGetWithNullTime() {
        testObj.put("keyA", "hello");

        assertEquals("value should be hello", "hello", testObj.get("keyA", null));
        testObj.put("A", "first");
        testObj.put("B", "second");

        Date date = new Date();
        long secondTimeStamp = date.getTime();
        testObj.put("A", "third");

        assertEquals("value should be third", "third", testObj.get("A", null));
        assertEquals("value should be third", "first", testObj.get("A", secondTimeStamp));

    }
}
