package test.java.com.codepath.sorting;

import main.java.com.codepath.sorting.Sort;
import main.java.com.codepath.sorting.SortingProblems;
import main.java.com.codepath.util.Util;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class SortingProblemsTest {

    private SortingProblems testObj;
    @Before
    public void setUp() {
        this.testObj = new SortingProblems();
    }

    @Test
    public void quickSort() {
        int[] array = {8, 7, 6, 5, 4};
        int[] result = {4, 5, 6, 7, 8};
        Sort sorter = new Sort();
        sorter.quickSort(array);
        Util.print(array);
        testEquality(array, result);
    }

    @Test
    public void mergeSort() {
        int[] array = {8, 7, 6, 5, 4};
        int[] result = {4, 5, 6, 7, 8};
        Sort sorter = new Sort();
        sorter.mergeSort(array);
        Util.print(array);
        testEquality(array, result);
    }

    @Test
    public void heapSortMax() {
        int[] array = {4, 10, 3, 5 ,1};
        int[] result = {1, 3, 4, 5, 10};
        Sort sorter = new Sort();
        sorter.heapSortMax(array);
        Util.print(array);
        testEquality(array, result);

        int[] array1 = {8, 7, 6, 5, 4};
        int[] result1 = {4, 5, 6, 7, 8};
        sorter.heapSortMax(array1);
        Util.print(array1);
        testEquality(array1, result1);
    }

    @Test
    public void heapSortMin() {
        int[] array = {4, 10, 3, 5 ,1};
        int[] result = {1, 3, 4, 5, 10};
        Sort sorter = new Sort();
        sorter.heapSortMin(array);
        Util.print(array);
        testEquality(array, result);

        int[] array1 = {8, 7, 6, 5, 4};
        int[] result1 = {4, 5, 6, 7, 8};
        sorter.heapSortMin(array1);
        Util.print(array1);
        testEquality(array1, result1);
    }

    @Test
    public void testMergeArraysAsc() {
        int[] array = {1, 4, 9};
        int[] array1 = {3, 7, 8};
        int[] array2 = {2, 8, 12};

        int[][] input = {array, array1, array2};

        int[] answer = {1, 2, 3, 4, 7, 8, 8, 9, 12};

        int[] result = testObj.mergeArrays(input);
        Util.print(result);
        testEquality(result, answer);
    }

    @Test
    public void testMergeArraysDesc() {
        int[] array = {9, 4, 1};
        int[] array1 = {8, 7, 3};
        int[] array2 = {12, 8, 2};

        int[][] input = {array, array1, array2};

        int[] answer = {12, 9, 8, 8, 7, 4, 3, 2, 1};

        int[] result = testObj.mergeArrays(input);
        Util.print(result);
        testEquality(result, answer);
    }

    @Test
    public void testMergeArraysAsc1() {
        int[][] input = {
            {3, 3, 12, 20, 22, 25, 34},
            {4, 10, 12, 20, 28, 32, 36},
            {5, 6, 10, 19, 22, 28, 34},
            {8, 17, 17, 25, 34, 34, 42},
            {6, 7, 14, 17, 18, 25, 26},
            {8, 10, 15, 19, 28, 32, 40},
            {5, 13, 17, 19, 25, 26, 27},
            {1, 9, 12, 20, 26, 28, 30},
            {0, 8, 13, 19, 21, 25, 28},
            {3, 12, 18, 21, 27, 32, 32}
        };

        int[] result = testObj.mergeArrays(input);
        for (int i = 0; i < result.length - 1; i++) {
            assertTrue (result[i] <= result[i+1]);
        }
    }

    @Test
    public void testTopK1() {
        int[] input = {1, 5, 4, 4, 2, 2};

        int[] result = testObj.topK(input, 2);
        int[] answer = {5, 4};

        Set<Integer> setResult = new HashSet<>();
        for (Integer n: result) {
            setResult.add(n);
        }
        Util.print(result);
        for (Integer n: answer) {
            if (setResult.contains(n)) {
                setResult.remove(n);
            }
        }
        assertTrue("set should be empty", setResult.isEmpty());
    }

    @Test
    public void testTopK2() {
      int[] input =  {4, 8, 9, 6, 6, 2, 10, 2, 8, 1, 2};
      int[] result = testObj.topK(input, 9);
      int[] answer = {1, 2, 4, 6, 8, 9, 10};
      Util.print(result);
        Set<Integer> setResult = new HashSet<>();
        for (Integer n: result) {
            setResult.add(n);
        }
        Util.print(result);
        for (Integer n: answer) {
            if (setResult.contains(n)) {
                setResult.remove(n);
            }
        }
        assertTrue("set should be empty", setResult.isEmpty());
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
}