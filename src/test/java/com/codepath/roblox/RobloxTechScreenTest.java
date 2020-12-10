package test.java.com.codepath.roblox;

import main.java.com.codepath.techscreens.roblox.StudentCoursePair;
import main.java.com.codepath.techscreens.roblox.TechScreen;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RobloxTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testRobloxViaKarat_DomainClicks() {
        String[] counts = {
                "900,google.com",
                "60,mail.yahoo.com",
                "10,mobile.sports.yahoo.com",
                "40,sports.yahoo.com",
                "300,yahoo.com",
                "10,stackoverflow.com",
                "2,en.wikipedia.org",
                "1,es.wikipedia.org",
                "1,mobile.sports"
        };

        Map<String, Integer> results = testObj.aggregateCounts(counts);
        assertEquals(".com should be 1320", 1320, results.get("com").intValue());
        assertEquals("stackoverflow.com should be 10", 10, results.get("stackoverflow.com").intValue());
        assertEquals("sports.yahoo.com should be 50", 50, results.get("sports.yahoo.com").intValue());
        assertEquals("google.com should be 900", 900, results.get("google.com").intValue());
        assertEquals("sports should be 1", 1, results.get("sports").intValue());
        assertEquals("org should be 3", 3, results.get("org").intValue());
        assertEquals("wikipedia.org should be 3", 3, results.get("wikipedia.org").intValue());
        assertEquals("mobile.sports should be 1", 1, results.get("mobile.sports").intValue());
        assertEquals("es.wikipedia.org should be 1", 1, results.get("es.wikipedia.org").intValue());
        assertEquals("en.wikipedia.org should be 2", 2, results.get("en.wikipedia.org").intValue());
        assertEquals("mail.yahoo.com should be 10", 10, results.get("mobile.sports.yahoo.com").intValue());
        assertEquals("yahoo.com should be 410", 410, results.get("yahoo.com").intValue());
    }

    @Test
    public void testVoidFindStudentCoursePairs() {
        String[][] studentCoursePairs = {
                {"58", "Software Design"},
                {"58", "Linear Algebra"},
                {"94", "Art History"},
                {"94", "Operating Systems"},
                {"17", "Software Design"},
                {"58", "Mechanics"},
                {"58", "Economics"},
                {"17", "Linear Algebra"},
                {"17", "Political Science"},
                {"94", "Economics"}
        };

        List<StudentCoursePair> list = testObj.find_pairs(studentCoursePairs);

        System.out.println("FROM MAIN");
        for (StudentCoursePair p : list) {
            System.out.println(p);
        }
    }

}
