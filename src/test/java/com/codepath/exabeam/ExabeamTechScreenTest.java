package test.java.com.codepath.exabeam;

import main.java.com.codepath.techscreens.exabeam.TechScreen;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExabeamTechScreenTest {
    public TechScreen testObj = null;

    @Before
    public void setUp() throws Exception {
        testObj = new TechScreen();
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
}
