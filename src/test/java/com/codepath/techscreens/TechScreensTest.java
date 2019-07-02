package test.java.com.codepath.techscreens;

import main.java.com.codepath.objects.UserClick;
import main.java.com.codepath.techscreens.TechScreens;
import main.java.com.codepath.techscreens.objects.Cell;
import main.java.com.codepath.techscreens.objects.ConnectFour;
import main.java.com.codepath.techscreens.objects.FareCalculator;
import main.java.com.codepath.techscreens.objects.InstaCartMap;
import main.java.com.codepath.techscreens.objects.Message;
import main.java.com.codepath.techscreens.objects.NameNumberElement;
import main.java.com.codepath.techscreens.objects.Pallet;
import main.java.com.codepath.techscreens.objects.Person;
import main.java.com.codepath.techscreens.objects.Position;
import main.java.com.codepath.techscreens.objects.Range;
import main.java.com.codepath.techscreens.objects.StackRoxNode;
import main.java.com.codepath.techscreens.objects.StatsCounter;
import main.java.com.codepath.techscreens.objects.StudentCoursePair;
import main.java.com.codepath.techscreens.objects.Sudoku;
import main.java.com.codepath.techscreens.objects.Tile;
import main.java.com.codepath.techscreens.objects.Trailer;
import main.java.com.codepath.techscreens.objects.ZerosRectangle;
import main.java.com.codepath.util.Util;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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

        int largestSquare = testObj.largestSquareMatrix(matrix);
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

        int largestSquare = testObj.largestSquareMatrix(matrix);
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

        int largestSquare = testObj.largestSquareMatrix(matrix1);
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

        int largestSquare = testObj.largestSquareMatrix(matrix2);
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

        int largestSquare = testObj.largestSquareMatrix(matrix3);
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

        int largestSquare = testObj.largestSquareMatrix(matrix1);
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
        int[][] parentChildPairs = new int[][]{
            {1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7},
            {4, 5}, {4, 8}, {8, 10}
        };

        List<Integer> answer = new ArrayList<>(Arrays.asList(1, 2, 4));
        Set<Integer> result = testObj.findNodesWithZeroParents(parentChildPairs);
        Integer[] ans = result.toArray(new Integer[0]);
        for (int i = 0; i < ans.length; i++) {
            assertEquals("items should be equal", answer.get(i).intValue(), ans[i].intValue());
        }
    }

    @Test
    public void testZume_findNodesWithOneParents() {
        int[][] parentChildPairs = new int[][]{
            {1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7},
            {4, 5}, {4, 8}, {8, 10}
        };

        List<Integer> answer = new ArrayList<>(Arrays.asList(5, 7, 8, 10));
        Set<Integer> result = testObj.findNodesWithOneParent(parentChildPairs);
        Integer[] ans = result.toArray(new Integer[0]);
        for (int i = 0; i < ans.length; i++) {
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
        for (String key : results.keySet()) {
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
        for (StudentCoursePair p : list) {
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
        for (String item : input) {
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

    @Test
    public void testFindTargetInSplitArray() {
        int[] nums = {4, 5, 6, 7, 8, 0, 1, 2};
        assertEquals("Should be 5", 5, testObj.findTargetInSplitArray(nums, 0));
        assertEquals("Should be -1", -1, testObj.findTargetInSplitArray(nums, 3));
        assertEquals("Should be 2", 2, testObj.findTargetInSplitArray(nums, 6));
        assertEquals("Should be 0", 0, testObj.findTargetInSplitArray(nums, 4));
        assertEquals("Should be 7", 7, testObj.findTargetInSplitArray(nums, 2));
    }

    @Test
    public void testFindTargetInSplitArray1() {
        int[] nums = {10, 11, 1, 2, 3, 4, 5, 6};
        assertEquals("Should be 2", 2, testObj.findTargetInSplitArray(nums, 1));
        assertEquals("Should be 0", 0, testObj.findTargetInSplitArray(nums, 10));
        assertEquals("Should be 7", 7, testObj.findTargetInSplitArray(nums, 6));
        assertEquals("Should be 1", 1, testObj.findTargetInSplitArray(nums, 11));
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

    @Test
    public void testOneEditAway() {
        // same
        assertTrue("Should be true", testObj.oneEditAway("", ""));
        assertTrue("Should be true", testObj.oneEditAway("a", "a"));
        assertTrue("Should be true", testObj.oneEditAway("abc", "abc"));

        // rep, ins, del at end
        assertTrue("Should be true", testObj.oneEditAway("abc", "abd"));
        assertTrue("Should be true", testObj.oneEditAway("abc", "ab"));

        // rep, ins, del at beginning
        assertTrue("Should be true", testObj.oneEditAway("abc", "bbc"));
        assertTrue("Should be true", testObj.oneEditAway("abc", "aabc"));
        assertTrue("Should be true", testObj.oneEditAway("abc", "bc"));

        // rep, ins, del in middle
        assertTrue("Should be true", testObj.oneEditAway("abc", "adc"));
        assertTrue("Should be true", testObj.oneEditAway("abc", "abdc"));
        assertTrue("Should be true", testObj.oneEditAway("abc", "ac"));

        // boundaries
        assertTrue("Should be true", testObj.oneEditAway("", "a"));
        assertTrue("Should be true", testObj.oneEditAway("a", ""));
        assertTrue("Should be true", testObj.oneEditAway("a", "aa"));
        assertTrue("Should be true", testObj.oneEditAway("aa", "a"));

        // repeats
        assertTrue("Should be true", testObj.oneEditAway("aaa", "aaaa"));
        assertTrue("Should be true", testObj.oneEditAway("aaa", "aa"));
        assertTrue("Should be true", testObj.oneEditAway("aaabbbccc", "aaabcbccc"));
        assertTrue("Should be true", testObj.oneEditAway("aaabbbccc", "aaabbccc"));
        assertTrue("Should be true", testObj.oneEditAway("aaabbbccc", "aaabcbbccc"));

        // should be false:
        assertFalse("Should be false", testObj.oneEditAway("bbb", "aba"));
        assertFalse("Should be false", testObj.oneEditAway("abc", "abdb"));
        assertFalse("Should be false", testObj.oneEditAway("abdb", "abc"));
        assertFalse("Should be false", testObj.oneEditAway("abc", "aaabc"));
        assertFalse("Should be false", testObj.oneEditAway("aaabc", "abc"));
        assertFalse("Should be false", testObj.oneEditAway("abc", "abccc"));
        assertFalse("Should be false", testObj.oneEditAway("abccc", "abc"));
        assertFalse("Should be false", testObj.oneEditAway("abc", "acb"));
        assertFalse("Should be false", testObj.oneEditAway("bac", "abc"));
        assertFalse("Should be false", testObj.oneEditAway("aaabbbccc", "aababbccc"));
        assertFalse("Should be false", testObj.oneEditAway("aaabbbccc", "aaabcbbdcc"));
    }

    @Test
    public void testMinTimeForAmazon() {
        List<Integer> parts = Arrays.asList(8, 4, 6, 12);
        assertEquals("Min time should be 58", 58, testObj.minimumTime(parts.size(), parts));
    }

    @Test
    public void testMinDistanceToObstacleForAmazon() {
        List<List<Integer>> lot = new ArrayList<>();
        List<Integer> firstRow = Arrays.asList(1, 0, 0);
        List<Integer> secondRow = Arrays.asList(1, 0, 0);
        List<Integer> thirdRow = Arrays.asList(1, 9, 1);

        lot.add(firstRow);
        lot.add(secondRow);
        lot.add(thirdRow);
        assertEquals("Should be 3", 3, testObj.minDistanceToRemoveObstacle(lot.size(), lot.get(0).size(), lot));
    }

    @Test
    public void testGetTimeToCompleteTasks() {
        int[] array = {1, 1, 2, 1};
        assertEquals("Count should be 7", 7, testObj.getTimeToCompleteTasks(array, 2));

        int[] array1 = {1, 2, 3, 4};
        assertEquals("Count should be 4", 4, testObj.getTimeToCompleteTasks(array1, 2));
    }

    @Test
    public void testSortedSquares() {
        int[] a = {-4, -2, 1, 3, 5};
        int[] results = testObj.sortedSquares(a);
        Util.print(results);

        int[] b = {-6, -4, -3, -2, -1};
        Util.print(testObj.sortedSquares(b));

        int[] c = {3, 4, 5, 7, 8};
        Util.print(testObj.sortedSquares(c));

        int[] d = {};
        Util.print(testObj.sortedSquares(d));
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

    @Test
    public void testEmployeeChart() {
        testObj.printEmployeeChart("1:Max:4,2:Ann:0,3:Albert:2,4:Edmond:2");
    }

    @Test
    public void testFindColors() {
        String[] colors = {"aliceblue","antiquewhite","aqua","aquamarine","azure","beige","bisque","black","blanchedalmond","blue",
            "blueviolet","brown","burlywood","cadetblue","chartreuse","chocolate","coral","cornflowerblue","cornsilk","crimson",
            "cyan","darkblue","darkcyan","darkgoldenrod","darkgray","darkgreen","darkgrey","darkkhaki","darkmagenta","darkolivegreen",
            "darkorange","darkorchid","darkred","darksalmon","darkseagreen","darkslateblue","darkslategray","darkslategrey",
            "darkturquoise","darkviolet","deeppink","deepskyblue","dimgray","dimgrey","dodgerblue","firebrick","floralwhite",
            "forestgreen","fuchsia","gainsboro","ghostwhite","gold","goldenrod","gray","green","greenyellow","grey","honeydew",
            "hotpink","indianred","indigo","ivory","khaki","lavender","lavenderblush","lawngreen","lemonchiffon","lightblue","lightcoral",
            "lightcyan","lightgoldenrodyellow","lightgray","lightgreen","lightgrey","lightpink","lightsalmon","lightseagreen","lightskyblue",
            "lightslategray","lightslategrey","lightsteelblue","lightyellow","lime","limegreen","linen","magenta","maroon","mediumaquamarine",
            "mediumblue","mediumorchid","mediumpurple","mediumseagreen","mediumslateblue","mediumspringgreen","mediumturquoise",
            "mediumvioletred","midnightblue","mintcream","mistyrose","moccasin","navajowhite","navy","oldlace","olive","olivedrab",
            "orange","orangered","orchid","palegoldenrod","palegreen","paleturquoise", "palevioletred","papayawhip","peachpuff","peru",
            "pink","plum","powderblue","purple","red","rosybrown","royalblue","saddlebrown","salmon","sandybrown","seagreen",
            "seashell","sienna","silver","skyblue","slateblue","slategray","slategrey","snow","springgreen","steelblue","tan","teal",
            "thistle","tomato","turquoise","violet","wheat","white","whitesmoke","yellow","yellowgreen"};

        List<Integer> pos = testObj.getPositions("uqi", "darkturquoise");
        System.out.println(pos);
        assertTrue("Should be sorted", testObj.isSorted(pos));

        List<Integer> negCase = Arrays.asList(6, 2, 7);
        assertFalse("Should not be sorted", testObj.isSorted(negCase));

        System.out.println(testObj.findColors("uqi", colors));
    }

    @Test
    public void testGetPathForTiles() {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */

        Tile[][] floor = {
            {new Tile(1, false), new Tile(0, false), new Tile(0, false)},
            {new Tile(1, false), new Tile(0, true), new Tile(0, false)},
            {new Tile(1, false), new Tile(1, false), new Tile(0, false)}
        };

        List<Cell> path = testObj.getPath(floor, 0, 0, 2, 1);
        System.out.println(path);
    }

    @Test
    public void testConnectFour() {
        ConnectFour board = new ConnectFour();
        Integer PLAYER_ONE = 1;
        Integer PLAYER_TWO = 2;

        // vertical
         System.out.println(board.play(PLAYER_ONE, 3));
         System.out.println(board.play(PLAYER_ONE, 3));
         System.out.println(board.play(PLAYER_ONE, 3));
         System.out.println(board.play(PLAYER_ONE, 3));

        // horizontal
        System.out.println(board.play(PLAYER_ONE, 1));
        System.out.println(board.play(PLAYER_ONE, 2));
        System.out.println(board.play(PLAYER_ONE, 3));
        System.out.println(board.play(PLAYER_ONE, 4));

        //Need complete diagonal tests

        board.print();
    }

    @Test
    public void findNextLargest() {
        int[] input = {1, 2, 7, 6, 5, 4, 3, 1};
        testObj.findNextLargest(input);
        Util.print(input);
        int[] inputAnswer = {1, 3, 1, 2, 4, 5, 6, 7};
        testEquality(input, inputAnswer);

        int[] input1 = {1, 1, 5};
        testObj.findNextLargest(input1);
        Util.print(input1);
        int[] inputAnswer1 = {1, 5, 1};
        testEquality(input1, inputAnswer1);

        int[] input2 = {1, 2, 3};
        testObj.findNextLargest(input2);
        Util.print(input2);
        int[] inputAnswer2 = {1, 3, 2};
        testEquality(input2, inputAnswer2);

        int[] input3 = {5, 1, 1};
        testObj.findNextLargest(input3);
        Util.print(input3);
        int[] inputAnswer3 = {1, 1, 5};
        testEquality(input3, inputAnswer3);
    }

    private void testEquality(int[] input, int[] answer) {

        if (input == null || answer == null) {
            assertTrue("Invalid input", false);
        }

        if (input.length != answer.length) {
            assertTrue("Arrays are of different lengths", false);
        }

        for (int i = 0; i < answer.length; i++) {
            assertEquals("values should be equal", answer[i], input[i]);
        }
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
        Map<String, Integer> result = testObj.getFlattendedMap(outerMap);
        System.out.println(result);
    }

    @Test
    public void testRanges() {
        Range range = new Range(0, 5);
        Range range1 = new Range(2, 6);
        Range range2 = new Range(11, 17);
        Range range3 = new Range(14, 17);
        Range range4 = new Range(10, 18);
        List<Range> ranges = new ArrayList<>(Arrays.asList(range, range1, range2, range3, range4));

        assertEquals("should be 16", 16, testObj.getUniqueSeconds(ranges));
        assertEquals("should be 16", 16, testObj.getUniqueSecondsEff(ranges));
    }

    @Test
    public void testFamilyTree() {
        assertEquals("should be child", "child", testObj.describeRelationship("Daphne", "Chris"));
        assertEquals("should be child", "parent", testObj.describeRelationship("Chris", "Daphne"));
        assertEquals("should be child", "parent", testObj.describeRelationship("Bret", "Chris"));
        assertEquals("should be child", "ancestor", testObj.describeRelationship("Chris", "Henry"));
        assertEquals("should be child", "ancestor", testObj.describeRelationship("Annie", "Jack"));
        assertEquals("should be child", "unknown", testObj.describeRelationship("Foo", "Bar"));
    }

    @Test
    public void testSudoku() {

        Sudoku sudoku = new Sudoku();
        int[][] board = {
            {1,2,3, 4,5,6, 7,8,9},
        {4,5,6, 7,8,9, 1,2,3},
        {7,8,9, 1,2,3, 4,5,6},
        {2,3,4, 5,6,7, 8,9,1},
        {5,6,7, 8,9,1, 2,3,4},
        {8,9,1, 2,3,4, 5,6,7},
        {3,4,5, 6,7,8, 9,1,2},
        {6,7,8, 9,1,2, 3,4,5},
        {9,1,2, 3,4,5, 6,7,8}};

        assertTrue("Rows should be valid", sudoku.checkRows(board));
        assertTrue("Cols should be valid", sudoku.checkCols(board));
        assertTrue("Subs should be valid", sudoku.checkSubMatrices(board));

        int[][] board1 = {
            {1,1,1, 1,1,1, 1,1,1},
            {2,2,2, 2,2,2, 2,2,2},
            {3,3,3, 3,3,3, 3,3,3},
            {4,4,4, 4,4,4, 4,4,4},
            {5,5,5, 5,5,5, 5,5,5},
            {6,6,6, 6,6,6, 6,6,6},
            {7,7,7, 7,7,7, 7,7,7},
            {8,8,8, 8,8,8, 8,8,8},
            {9,9,9, 9,9,9, 9,9,9}};

        assertFalse("Rows should be invalid", sudoku.checkRows(board1));
        assertTrue("Cols should be invalid", sudoku.checkCols(board1));
        assertFalse("Subs should be invalid", sudoku.checkSubMatrices(board1));

        int[][] board2 = {
          {1,2,3, 4,5,6, 7,8,9},
          {1,2,3, 4,5,6, 7,8,9},
          {1,2,3, 4,5,6, 7,8,9},
          {1,2,3, 4,5,6, 7,8,9},
          {1,2,3, 4,5,6, 7,8,9},
          {1,2,3, 4,5,6, 7,8,9},
          {1,2,3, 4,5,6, 7,8,9},
          {1,2,3, 4,5,6, 7,8,9},
          {1,2,3, 4,5,6, 7,8,9}
        };

        assertTrue("Rows should be valid", sudoku.checkRows(board2));
        assertFalse("Cols should be invalid", sudoku.checkCols(board2));
        assertFalse("Subs should be invalid", sudoku.checkSubMatrices(board2));

        int[][] board3 = {
            {1,2,3, 4,5,6, 7,8,9},
            {4,5,6, 7,8,9, 1,2,3},
            {7,8,9, 1,2,3, 4,5,6},
            {2,3,4, 5,6,7, 8,9,1},
            {5,6,7, 8,9,1, 2,3,4},
            {3,4,5, 6,7,8, 9,1,2},
            {8,9,1, 2,3,4, 5,6,7},
            {6,7,8, 9,1,2, 3,4,5},
            {9,1,2, 3,4,5, 6,7,8}
        };

       assertFalse(sudoku.evaluateBoard(board3));
    }

    @Test
    public void testPrintZigZag() {
        testObj.printZigZag("ABCDEFGHIJKLMNOPQ", 4);

    }

    @Test
    public void testPrintZigZag1() {
        testObj.printZigZag("ABCDEFGHIJKLMNOPQ", 4);
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

    @Test
    public void testFareCalculator() {
        FareCalculator fareCalculator = new FareCalculator(10.00d);
        fareCalculator.addRider(1, 1);
        fareCalculator.addRider(2, 3);
        assertEquals("should be 13.33", 13.33333d, fareCalculator.dropFare(1,4).doubleValue(), .0002);
    }

    @Test
    public void testTrailerWeight() {
        Pallet pallet1 = new Pallet(1, 5);
        Pallet pallet2 = new Pallet(2, 6);
        Pallet pallet3 = new Pallet(3, 7);

        Trailer trailer = new Trailer();

        trailer.load(pallet1, 2);
        trailer.load(pallet2, 5);
        Pallet pallet = trailer.unload(1, 6);

        assertEquals("pallet id should be 1", 1, pallet.id);

        trailer.load(pallet3, 8);

        assertEquals("weight should be 13", 13, trailer.getWeight());
        assertEquals("weight at time 0: ", 0,trailer.weightAt(0));
        assertEquals("weight at time 1: ", 0, trailer.weightAt(1));
        assertEquals("weight at time 2: ", 5, trailer.weightAt(2));
        assertEquals("weight at time 3: ", 5,trailer.weightAt(3));
        assertEquals("weight at time 4: ", 5, trailer.weightAt(4));
        assertEquals("weight at time 5: ", 11, trailer.weightAt(5));
        assertEquals("weight at time 6: ", 6, trailer.weightAt(6));
        assertEquals("weight at time 7: ", 6, trailer.weightAt(7));
        assertEquals("weight at time 8: ", 13, trailer.weightAt(8));
    }

    @Test
    public void testSecretSanta() {
        List<String> names = Arrays.asList("Bill", "Bob", "Tom");
        Map<String, String> map = testObj.getRandomMap(names);
        System.out.println(map);
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
    public void testSquareMatix() {
        int[][] input = {
                    {0, 1, 1},
                    {0, 1, 1},
                    {0, 0, 0}};
        assertEquals("Should be 2", 2, testObj.largestSquareMatrixOptimal(input));
        assertEquals("Should be 2", 2, testObj.largestSubMatrixOfOnes(input));


        int[][] input1 = {
            {0, 1, 1, 1},
            {0, 1, 1, 1},
            {0, 1, 1, 1},
            {0, 1, 1, 1}};
        assertEquals("Should be 3", 3, testObj.largestSquareMatrixOptimal(input1));
        assertEquals("Should be 3", 3, testObj.largestSubMatrixOfOnes(input1));
    }

    @Test
    public void testGetClosest() {
        int[] input = {10, 50, 65, 70, 80, 120, 200};
        assertEquals("closest is 65", 65, testObj.getClosest(input, 60));

        assertEquals("closest is 200", 200, testObj.getClosest(input, 200));
        assertEquals("closest is 200", 200, testObj.getClosest(input, 210));
        assertEquals("closest is 10", 10, testObj.getClosest(input, 10));
        assertEquals("closest is 10", 10, testObj.getClosest(input, 5));
        assertEquals("closest is 80", 80, testObj.getClosest(input, 89));
        assertEquals("closest is 80", 80, testObj.getClosest(input, 79));

    }
}
