package test.java.com.codepath.quizlet;

import main.java.com.codepath.techscreens.quizlet.TechScreen;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QuizletTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testQuizlet_SameSchema() {
        Map<String, Object> FIRST = new HashMap() {{
            put("a", "hello");
            put("b", 1);
        }};

        Map<String, Object> SECOND = new HashMap() {{
            put("a", "world");
            put("b", 2);
        }};

        Map<String, Object> THIRD = new HashMap() {{
            put("a", "hello");
            put("b", "1");
        }};

        Map<String, Object> FOURTH = new HashMap() {{
            put("a", "hello");
        }};

        Map<String, Object> FIFTH = new HashMap() {{
            put("a", 1);
        }};

        Map<String, Object> SIXTH = new HashMap() {{
            put("a", 1);
            put("b", 2);
        }};

        assertTrue("Schemas should be the same", testObj.sameSchema(Arrays.asList(FIRST, SECOND)));
        assertFalse("Schemas should NOT be the same", testObj.sameSchema(Arrays.asList(FIRST, THIRD)));
        assertFalse("Schemas should be the same", testObj.sameSchema(Arrays.asList(THIRD, FOURTH)));
        assertFalse("Schemas should NOT be the same", testObj.sameSchema(Arrays.asList(FIRST, FIFTH)));
        assertFalse("Schemas should NOT be the same", testObj.sameSchema(Arrays.asList(FIFTH, SIXTH)));
    }

}
