package test.java.com.codepath.techscreens;

import main.java.com.codepath.techscreens.objects.InstaCartMap;
import main.java.com.codepath.techscreens.objects.NameNumberElement;
import main.java.com.codepath.techscreens.objects.Person;
import main.java.com.codepath.techscreens.objects.Position;
import main.java.com.codepath.techscreens.objects.StackRoxNode;
import main.java.com.codepath.techscreens.TechScreens;
import main.java.com.codepath.techscreens.objects.StudentCoursePair;
import main.java.com.codepath.techscreens.objects.ZerosRectangle;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TechScreensTest {

    public TechScreens testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreens();
    }

    @Test
    public void testSonder_GetFirstBooking() {
        String bookingsInput = "4:8 19:35 80:160";
        int current_date = 35;
        int stay_length = 46;
        assertEquals("first available date should be 180", 160, testObj.getDate(bookingsInput, current_date, stay_length));

        bookingsInput = "4:8 19:35 80:160";
        current_date = 50;
        stay_length = 15;
        assertEquals("first available date should be 50", 50, testObj.getDate(bookingsInput, current_date, stay_length));

        bookingsInput = "0:3 3:6 7:14";
        current_date = 14;
        stay_length = 46;
        assertEquals("first available date should be 14", 14, testObj.getDate(bookingsInput, current_date, stay_length));

        bookingsInput = "0:3 3:6 7:14";
        current_date = 3;
        stay_length = 1;
        assertEquals("first available date should be 6", 6, testObj.getDate(bookingsInput, current_date, stay_length));

        bookingsInput = "0:3 3:6 7:14";
        current_date = 3;
        stay_length = 2;
        assertEquals("first available date should be 14", 14, testObj.getDate(bookingsInput, current_date, stay_length));
    }


    @Test
    public void testExabeam_GetLargestAllOnesSquareSubmatrix1() {
        List<Integer> row0 = Arrays.asList(1, 1, 0);
        List<Integer> row1 = Arrays.asList(1, 1, 0);
        List<Integer> row2 = Arrays.asList(1, 1, 1);

        List<List<Integer>> matrix = new ArrayList<>();
        matrix.add(row0);
        matrix.add(row1);
        matrix.add(row2);

        int largestSquare = testObj.largestMatrix(matrix);
        assertEquals("Largest square should be two", 2, largestSquare);
    }

    @Test
    public void testExabeam_GetLargestAllOnesSquareSubmatrix2() {
        List<Integer> row0 = Arrays.asList(0, 0, 0);
        List<Integer> row1 = Arrays.asList(1, 1, 0);
        List<Integer> row2 = Arrays.asList(1, 1, 1);

        List<List<Integer>> matrix = new ArrayList<>();
        matrix.add(row0);
        matrix.add(row1);
        matrix.add(row2);

        int largestSquare = testObj.largestMatrix(matrix);
        assertEquals("Largest square should be two", 2, largestSquare);
    }

    @Test
    public void testExabeam_GetLargestAllOnesSquareSubmatrix3() {
        List<Integer> row0 = Arrays.asList(1, 1, 0, 0, 0);
        List<Integer> row1 = Arrays.asList(0, 1, 1, 1, 0);
        List<Integer> row2 = Arrays.asList(0, 1, 1, 1, 0);
        List<Integer> row3 = Arrays.asList(0, 1, 1, 1, 0);
        List<Integer> row4 = Arrays.asList(0, 0, 0, 0, 0);

        List<List<Integer>> matrix1 = new ArrayList<>();
        matrix1.add(row0);
        matrix1.add(row1);
        matrix1.add(row2);
        matrix1.add(row3);
        matrix1.add(row4);

        int largestSquare = testObj.largestMatrix(matrix1);
        assertEquals("Largest square should be 3", 3, largestSquare);
    }

    @Test
    public void testExabeam_GetLargestAllOnesSquareSubmatrix4() {

        List<Integer> row0 = Arrays.asList(0, 0, 0);
        List<Integer> row1 = Arrays.asList(0, 1, 1);
        List<Integer> row2 = Arrays.asList(0, 1, 1);

        List<List<Integer>> matrix2 = new ArrayList<>();
        matrix2.add(row0);
        matrix2.add(row1);
        matrix2.add(row2);

        int largestSquare = testObj.largestMatrix(matrix2);
        assertEquals("Largest square should be 2", 2, largestSquare);
    }

    @Test
    public void testExabeam_GetLargestAllOnesSquareSubmatrix5() {
        List<Integer> row0 = Arrays.asList(0, 0, 0);
        List<Integer> row1 = Arrays.asList(0, 1, 0);
        List<Integer> row2 = Arrays.asList(0, 0, 0);

        List<List<Integer>> matrix3 = new ArrayList<>();
        matrix3.add(row0);
        matrix3.add(row1);
        matrix3.add(row2);

        int largestSquare = testObj.largestMatrix(matrix3);
        assertEquals("Largest square should be 1", 1, largestSquare);
    }

    @Test
    public void testExabeam_GetLargestMatrixAllOnes() {
        List<Integer> row0 = Arrays.asList(1, 1, 1, 1, 1);
        List<Integer> row1 = Arrays.asList(1, 1, 1, 0, 0);
        List<Integer> row2 = Arrays.asList(1, 1, 1, 0, 0);
        List<Integer> row3 = Arrays.asList(1, 1, 1, 0, 0);
        List<Integer> row4 = Arrays.asList(1, 1, 1, 1, 1);

        List<List<Integer>> matrix1 = new ArrayList<>();
        matrix1.add(row0);
        matrix1.add(row1);
        matrix1.add(row2);
        matrix1.add(row3);
        matrix1.add(row4);

        int largestSquare = testObj.largestMatrix(matrix1);
        assertEquals("Largest square should be 3", 3, largestSquare);
    }

    @Test
    public void testDyDx_GetStringFromField() {
        Person parent = new Person("Johns Dad", "Doe Dad", 65, null);
        Person person = new Person("John", "doe", 30, parent);

        String result = testObj.getJsonString(person);
        System.out.println(result);
    }

    @Test
    public void testZume_findNodesWithZeroParents() {
        int[][] parentChildPairs = new int[][] {
                {1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7},
                {4, 5}, {4, 8}, {8, 10}
        };

        List<Integer> answer = new ArrayList<>(Arrays.asList(1, 2, 4));
        Set<Integer> result = testObj.findNodesWithZeroParents(parentChildPairs);
        Integer[] ans = result.toArray(new Integer[0]);
        for(int i = 0; i < ans.length; i++) {
            assertEquals("items should be equal", answer.get(i).intValue(), ans[i].intValue());
        }
    }

    @Test
    public void testZume_findNodesWithOneParents() {
        int[][] parentChildPairs = new int[][] {
            {1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7},
            {4, 5}, {4, 8}, {8, 10}
        };

        List<Integer> answer = new ArrayList<>(Arrays.asList(5, 7, 8, 10));
        Set<Integer> result = testObj.findNodesWithOneParent(parentChildPairs);
        Integer[] ans = result.toArray(new Integer[0]);
        for(int i = 0; i < ans.length; i++) {
            assertEquals("items should be equal", answer.get(i).intValue(), ans[i].intValue());
        }
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

    @Test
    public void testStackRox_GetUniquePrefixes() {
        List<StackRoxNode> nodes = new ArrayList<>(Arrays.asList(
            new StackRoxNode("4", "ABCD"),
            new StackRoxNode("1", "ABC"),
            new StackRoxNode("2", "AB"),
            new StackRoxNode("3", "B")
        ));
        Map<String, String> results = testObj.getUniquePrefixes(nodes);
        for(String key: results.keySet()) {
            System.out.println(results.get(key));
        }
    }

    @Test
    public void testSignalFx_removeDuplicates() {
        List<Integer> input = Arrays.asList(3, 6, 2, 3, 6, 5, 2, 3);
        List<Integer> result = testObj.getUniqueValuesEff(input);
        List<Integer> answer = Arrays.asList(3, 6, 2, 5, -1, -1, -1, -1);
        int length = result.size();
        for (int i = 0; i < length; i++) {
            assertEquals("Values should be equal", result.get(i), answer.get(i));
        }
    }

    @Test
    public void testDarkStore_GetPositions() {
        Position[] AMZN = new Position[]{new Position(LocalDate.parse("2019-04-01"), 200),
            new Position(LocalDate.parse("2019-04-04"), 400)};
        Position[] GOOG = new Position[]{new Position(LocalDate.parse("2019-04-02"), 500),
            new Position(LocalDate.parse("2019-04-03"), 700)};
        Position[] MSFT = new Position[]{new Position(LocalDate.parse("2019-04-01"), 400),
            new Position(LocalDate.parse("2019-04-02"), 200),
            new Position(LocalDate.parse("2019-04-04"), 600)};

        Position[][] portfolio = new Position[][]{AMZN, GOOG, MSFT};
        Position[] totals = testObj.getTotalsFromPortfolio(portfolio);

        System.out.println(Arrays.toString(totals));

        Position[] YAHOO = new Position[]{new Position(LocalDate.parse("2019-04-02"), 200),
            new Position(LocalDate.parse("2019-04-04"), 300)};
        Position[] APPL = new Position[]{new Position(LocalDate.parse("2019-04-02"), 200),
            new Position(LocalDate.parse("2019-04-03"), 100)};
        Position[] GE = new Position[]{new Position(LocalDate.parse("2019-04-01"), 500),
            new Position(LocalDate.parse("2019-04-03"), 700)};


        Position[][] portfolio1 = new Position[][]{YAHOO, APPL, GE};
        Position[] moreTotals = testObj.getTotalsFromPortfolio(portfolio1);

        System.out.println(Arrays.toString(moreTotals));
    }

    @Test
    public void testZumeViaKarat_DomainsClicks() {
        int[][] image1 = {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 0, 0, 1},
            {1, 1, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1}
        };

        ZerosRectangle image1Result = testObj.findRectangleOfZeros(image1);
        assertEquals("Start row should be 2", image1Result.beginning.row, 2);
        assertEquals("Start col should be 3", image1Result.beginning.col, 3);
        assertEquals("Width should be 3", image1Result.width, 3);
        assertEquals("Height should be 2", image1Result.height, 2);

        int[][] image2 = {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 0}
        };

        ZerosRectangle image2Result = testObj.findRectangleOfZeros(image2);
        assertEquals("Start row should be 4", image2Result.beginning.row, 4);
        assertEquals("Start col should be 6", image2Result.beginning.col, 6);
        assertEquals("Width should be 1", image2Result.width, 1);
        assertEquals("Height should be 1", image2Result.height, 1);

        int[][] image3 = {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 0, 0},
            {1, 1, 1, 1, 1, 0, 0}
        };

        ZerosRectangle image3Result = testObj.findRectangleOfZeros(image3);
        assertEquals("Start row should be 3", image3Result.beginning.row, 3);
        assertEquals("Start col should be 5", image3Result.beginning.col, 5);
        assertEquals("Width should be 2", image3Result.width, 2);
        assertEquals("Height should be 2", image3Result.height, 2);

        int[][] image4 = {
            {0, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1}
        };

        ZerosRectangle image4Result = testObj.findRectangleOfZeros(image4);
        System.out.println(image4Result);
        assertEquals("Start row should be 1", image4Result.beginning.row, 0);
        assertEquals("Start col should be 1", image4Result.beginning.col, 0);
        assertEquals("Width should be 1", image4Result.width, 1);
        assertEquals("Height should be 1", image4Result.height, 1);

        int[][] image5 = {
            {0}
        };

        ZerosRectangle image5Result = testObj.findRectangleOfZeros(image5);
        assertEquals("Start row should be 1", image5Result.beginning.row, 0);
        assertEquals("Start col should be 1", image5Result.beginning.col, 0);
        assertEquals("Width should be 1", image5Result.width, 1);
        assertEquals("Height should be 1", image5Result.height, 1);
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
    public void testFindSmallestMissingPositiveInteger() {
        int[] A = new int[]{1, 3, 4, 6, 2, 1};
        int ans = testObj.findSmallestMissingPositiveInteger(A);
        assertEquals("Answer should be 5", 5, ans);


        int[] A1 = new int[]{-1, -3};
        int ans1 = testObj.findSmallestMissingPositiveInteger(A1);
        assertEquals("Answer should be 1", 1, ans1);

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
        for (StudentCoursePair p: list) {
            System.out.println(p);
        }
    }

    @Test
    public void testUdemy() {
        String[] input = {
            "John one-billion-two-hundred-thousand",
            "Alex five",
            "Alex one",
            "John two-hundred-thirty-four-thousand-five-hundred-sixty-seven",
            "John thirty-three",
            "John four-million-eight-thousand"
        };

        List<NameNumberElement> elements = new ArrayList<>();
        for (String item: input) {
            String[] values = item.split(" ");
            NameNumberElement e = new NameNumberElement(values[0], values[1]);
            elements.add(e);
        }
        Collections.sort(elements);
        System.out.println(elements);
    }

    @Test
    public void testUdemyGetNumber() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 234567", 234567, e.getNumber("two-hundred-thirty-four-thousand-five-hundred-sixty-seven"));
    }

    @Test
    public void testUdemyGetNumber1() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 4500", 4500, e.getNumber("four-thousand-five-hundred"));
    }

    @Test
    public void testUdemyGetNumber2() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 1000000", 1000000, e.getNumber("one-million"));
    }

    @Test
    public void testUdemyGetNumber3() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 10000", 10000, e.getNumber("ten-thousand"));
    }

    @Test
    public void testUdemyGetNumber4() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 10050", 10050, e.getNumber("ten-thousand-fifty"));
    }

    @Test
    public void testUdemyGetNumber5() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 1550", 1550, e.getNumber("fifteen-hundred-fifty"));
    }

    @Test
    public void testUdemyGetNumber6() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 50001", 50001, e.getNumber("fifty-thousand-one"));
    }

    @Test
    public void testUdemyGetNumber7() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 450001", 450001, e.getNumber("four-hundred-fifty-thousand-one"));
    }

    @Test
    public void testUdemyGetNumber8() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 450000", 450000, e.getNumber("four-hundred-fifty-thousand"));
    }

    @Test
    public void testUdemyGetNumber9() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 892", 892, e.getNumber("eight-hundred-ninety-two"));
    }

    @Test
    public void testUdemyGetNumber10() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 1400000", 1400000, e.getNumber("one-million-four-hundred-thousand"));
    }

    @Test
    public void testUdemyGetNumber11() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 1462500", 1462500, e.getNumber("one-million-four-hundred-sixty-two-thousand-five-hundred"));
    }

    @Test
    public void testUdemyGetNumber12() {
        NameNumberElement e = new NameNumberElement("Alex", "five");
        assertEquals("Should be 5", 5, e.getNumber("five"));
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
    public void testMasterClass() {
        assertEquals("Should be equal to igpay", "igpay", testObj.pigLatinTranslate("pig"));
        assertEquals("Should be equal to eggay", "eggay", testObj.pigLatinTranslate("egg"));
        assertEquals("Should be equal to Ellohay", "Ellohay", testObj.pigLatinTranslate("Hello"));
        assertEquals("Should be equal to Orangeay", "Orangeay", testObj.pigLatinTranslate("Orange"));
        assertEquals("Should be equal to Uckstray", "Uckstray", testObj.pigLatinTranslateImproved("Struck"));
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
