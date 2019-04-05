package test.java.com.codepath.techscreens;

import main.java.com.codepath.objects.Person;
import main.java.com.codepath.techscreens.TechScreens;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TechScreensTest {

    public TechScreens testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreens();
    }

    @Test
    public void testGetFirstBooking() {
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
    public void testGetLargestAllOnesSquareSubmatrix1() {
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
    public void testGetLargestAllOnesSquareSubmatrix2() {
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
    public void testGetLargestAllOnesSquareSubmatrix3() {
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
    public void testGetLargestAllOnesSquareSubmatrix4() {

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
    public void testGetLargestAllOnesSquareSubmatrix5() {
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
    public void testGetLargestMatrixAllOnes() {
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
    public void testGetStringFromField() {
        Person parent = new Person("Johns Dad", "Doe Dad", 65, null);
        Person person = new Person("John", "doe", 30, parent);

        String result = testObj.getJsonString(person);
        System.out.println(result);
    }

    @Test
    public void findNodesWithZeroParents() {
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
    public void findNodesWithOneParents() {
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
}
