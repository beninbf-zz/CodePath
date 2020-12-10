package test.java.com.codepath.yelp;

import main.java.com.codepath.objects.UserClick;
import main.java.com.codepath.techscreens.yelp.TechScreen;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class YelpTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
    }

    @Test
    public void testUserClicks() {
        List<UserClick> clicks = Arrays.asList(
                new UserClick("A", "B"),
                new UserClick("B", "C"),
                new UserClick("C", "B"),
                new UserClick("B", "D"),
                new UserClick("A", "Z"),
                new UserClick("Z", "B")
        );

        String result = testObj.getFinalDestination(clicks, "A");
        assertEquals("should be B", "B", result);
    }

    @Test
    public void testBookMarkMatch() {

        String bizNames[] = {
                "Burger King",
                "McDonald's",
                "Bob’s Burgers",
                "Five Guys",
                "Super Duper Burgers",
                "Wahlburgers"
        };

        String bizNames1[] = {
                "Wahlburgers",
                "Burger King",
                "McDonald's",
                "Bob’s Burgers",
                "Five Guys",
                "Super Duper Burgers",
                "burger town",
                "the place for burgers"
        };

        String bizNames2[] = {
                "Wahlburgers",
                "Burger King",
                "McDonald's",
                "Bob’s Burgers",
                "Five Guys",
                "Super Duper Burgers"
        };

        String[] empty = new String[1];
        String[] nullArray = null;

        //   List<String> result = Solution.getBookMarks(bizNames, "bur");
        //   System.out.println(result);
        //   Assert.assertEquals(4, result.size());

        //   List<String> result1 = Solution.getBookMarks(bizNames1, "bur");
        //   System.out.println(result1);
        //   Assert.assertEquals(4, result1.size());

        //   List<String> result2 = Solution.getBookMarks(bizNames1, "xoo");
        //   System.out.println(result2);
        //   Assert.assertEquals(0, result2.size());

        //   List<String> result3 = Solution.getBookMarks(empty, "xoo");
        //   System.out.println(result3);
        //   Assert.assertEquals(0, result3.size());

        //   List<String> result4 = Solution.getBookMarks(nullArray, "xoo");
        //   System.out.println(result4);
        //   Assert.assertEquals(0, result4.size());

        //   List<String> result5 = Solution.getBookMarks(bizNames1, "");
        //   System.out.println(result5);
        //   Assert.assertEquals(4, result5.size());

        //   List<String> result6 = Solution.getBookMarks(nullArray, null);
        //   System.out.println(result5);
        //   Assert.assertEquals(0, result6.size());

        List<String> result7 = testObj.getBookMarks(bizNames1, "bur");
        System.out.println(result7);
        assertEquals(4, result7.size());

        List<String> result8 = testObj.getBookMarks(bizNames2, "bur");
        System.out.println(result8);
        assertEquals(4, result8.size());
    }
}
