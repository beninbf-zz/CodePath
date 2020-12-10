package test.java.com.codepath.lyft;

import main.java.com.codepath.techscreens.lyft.TechScreen;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LyftTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testIntersectionIterator() {
        Iterator<Integer> it1 = testIter(1);
        Iterator<Integer> it2 = testIter(1);
        assertEquals(Arrays.asList(1), testObj.commonElements(it1, it2));

        it1 = testIter(1, 2);
        it2 = testIter(1, 2);
        assertEquals(Arrays.asList(1, 2), testObj.commonElements(it1, it2));

        it1 = testIter(1, 2, 3);
        it2 = testIter(1, 3);
        assertEquals(Arrays.asList(1, 3), testObj.commonElements(it1, it2));

        it1 = testIter(4, 5, 6);
        it2 = testIter(1, 3);
        assertEquals(Arrays.asList(), testObj.commonElements(it1, it2));

        it1 = testIter(1, 2, 3, 4, 5, 6);
        it2 = testIter(2, 3, 4, 10, 11);
        assertEquals(Arrays.asList(2, 3, 4), testObj.commonElements(it1, it2));

        it1 = testIter(1, 2, 3, 4, 5, 6);
        it2 = testIter(1, 2, 3, 4, 5, 6);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), testObj.commonElements(it1, it2));

        it1 = testIter(1, 2, 3, 4, 5, 6, 10);
        it2 = testIter(2, 3, 10, 11);
        assertEquals(Arrays.asList(2, 3, 10), testObj.commonElements(it1, it2));

        it1 = testIter();
        it2 = testIter();
        assertEquals(Collections.emptyList(), testObj.commonElements(it1, it2));
    }

    public Iterator<Integer> testIter(Integer... ints) {
        return Arrays.asList(ints).iterator();
    }
}
